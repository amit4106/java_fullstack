package com.examples.empapp;

import com.examples.empapp.model.Employee;
import com.examples.empapp.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootApplication
//@ComponentScan
//@Configuration
//@EnableAutoConfiguration
public class EmployeeMgmtSpringBootApplication implements CommandLineRunner {

	// Create logger instance
	private static Logger log = LoggerFactory.getLogger(EmployeeMgmtSpringBootApplication.class);

	private Scanner in;

	@Autowired
	private EmployeeService empService;

	@Autowired
	private ApplicationContext ctx;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeMgmtSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		in = new Scanner(System.in);
//		ctx = new AnnotationConfigApplicationContext(EmployeeConfig.class);

//		System.out.println("No of beans: " + ctx.getBeanDefinitionCount());
//		for(String beanName: ctx.getBeanDefinitionNames()) {
//			System.out.println(beanName);
//		}

//		empService = new EmployeeServiceImpl();
//		empService = ctx.getBean("empService", EmployeeService.class);



		ExecutorService executor = Executors.newCachedThreadPool();

//		//System.out.print("Welcome to Employee Management App!");
//		log.debug("DEBUG: Welcome to Employee Management App!");
		log.debug("DEBUG: Welcome to Employee Management App!");
		log.info("INFO: Welcome to Employee Management App!");
		log.warn("WARN: Welcome to Employee Management App!");
		log.error("ERROR: Welcome to Employee Management App!");

		while (true) {

			System.out.println("\n");
			System.out.println("1. Add Employee");
			System.out.println("2. View Employee");
			System.out.println("3. Update Employee");
			System.out.println("4. Delete Employee");
			System.out.println("5. View All Employees");
			System.out.println("6. Print Statistics");
			System.out.println("7. Import");
			System.out.println("8. Export");
			System.out.println("9. Exit");

			System.out.print("Enter the option: ");
			int option = 0;
			// Get option from user
			try {
				option = Integer.parseInt(in.next());
			} catch (NumberFormatException e) {
				log.error("Invalid option. Please enter valid option.");
				continue;
			}
			int empId;
			try {
				switch (option) {
					case 1:

						addEmployee();
						log.info("Employee has been added successfully!");
						break;
					case 2:
						System.out.print("Please enter employee id: ");
						empId = in.nextInt();
						Employee emp = empService.get(empId);
						printHeader();
						printDetail(emp);
						break;
					case 3:
						System.out.print("Please enter employee id: ");
						empId = in.nextInt();
						Employee empForUpdate = empService.get(empId);
						captureEmpDetail(empForUpdate);
						empService.update(empForUpdate);
						log.info("Employee has been updated successfully!");
						break;
					case 4:
						System.out.print("Please enter employee id: ");
						empId = in.nextInt();
						empService.delete(empId);
						log.info("Employee has been deleted successfully!");
						break;
					case 5:
						List<Employee> employees = empService.getAll();
						printHeader();
						for (Employee employee : employees) {
							printDetail(employee);
						}
						break;
					case 6:
						printStatistics();
						break;
					case 7:
						Callable<Boolean> importThread = new Callable<Boolean>() {
							@Override
							public Boolean call() throws Exception {
								System.out.println(Thread.currentThread() + " Waiting for 5s to start import process.");
								Thread.sleep(5000);
								empService.bulkImport();
								return true;
							}
						};

						Future<Boolean> importFuture = executor.submit(importThread);
						System.out.println(Thread.currentThread().getName() + " Import process triggered");

						break;
					case 8:
						Callable<Boolean> exportThread = new Callable<Boolean>() {
							@Override
							public Boolean call() throws Exception {
								System.out.println(Thread.currentThread() + " Waiting for 5s to start export process.");
								Thread.sleep(5000);
								empService.bulkExport();
								return true;
							}
						};

						Future<Boolean> exportFuture = executor.submit(exportThread);
						System.out.println(Thread.currentThread().getName() + " Export process triggered");
						break;
					case 9:
						System.out.println("Thank you!!!");
						executor.shutdown();
						in.close();
						System.exit(0);
						break;

					default:
						break;
				}
			} catch (NumberFormatException e) {
				log.error("Invalid input. Please enter valid input.");
			}
		}
	}

	private void printStatistics() {

		System.out.println("No of employees older than thirty years: "
				+ empService.getEmployeeCountAgeGreaterThan(e -> e.getAge() > 30));
		System.out.println("List employee IDs older than thirty years: " + empService.getEmployeeIdsAgeGreaterThan(30));
		System.out.println("Employee count by Department: " + empService.getEmployeeCountByDepartment());
		System.out.println("Employee count by Department ordered: " + empService.getEmployeeCountByDepartmentOdered());
		System.out.println("Average Employee Age by Department: " + empService.getAvgEmployeeAgeByDept());
		System.out.println(
				"List Departments have more than 3 employees: " + empService.getDepartmentsHaveEmployeesMoreThan(3));
		System.out.println("List Employees starts with 'S':" + empService.getEmployeeNamesStartsWith("S"));

	}

	private void printHeader() {
		System.out.format("\n%5s %15s %5s %15s %15s %15s", "EmpID", "Name", "Age", "Designation", "Department",
				"Country");
	}

	private void printDetail(Employee emp) {
		if (emp == null) {
			return;
		}

		System.out.format("\n%5d %15s %5d %15s %15s %15s", emp.getEmpId(), emp.getName(), emp.getAge(),
				emp.getDesignation(), emp.getDepartment(), emp.getCountry());
	}

	private void addEmployee() throws NumberFormatException {
		Employee employee = new Employee();

		captureEmpDetail(employee);

		empService.create(employee);
	}

	private void captureEmpDetail(Employee employee) throws NumberFormatException {
		System.out.print("Enter employee Name: ");
		employee.setName(in.next());

		try {
			boolean val = true;
			do {
				System.out.print("Enter employee Age: ");
				String errorMsg = "Invalid Age. Age should be between 18 to 60.";
				employee.setAge(Integer.parseInt(in.next()));
				val = empService.validate(employee, errorMsg, e -> e.getAge() >= 18 && e.getAge() <= 60, m -> {
					System.out.println(m);
					return false;
				});
			} while (!val);
		} catch (NumberFormatException e) {
			throw e;
		}

		System.out.print("Enter employee Designation: ");
		employee.setDesignation(in.next());

		System.out.print("Enter employee Department: ");
		employee.setDepartment(in.next());

		System.out.print("Enter employee Country: ");
		employee.setCountry(in.next());
	}
}
