package com.examples.empapp.dao;

import java.sql.PreparedStatement;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.examples.empapp.hibernateutil.HibernateUtil;
import com.examples.empapp.model.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	PreparedStatement ps = null;
	
	public int create(Employee employee) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
//		Connection con = JDBCUtil.getConnection();
//		String sql = "insert into employee values(?,?,?,?,?,?)";
//		int executeUpdate = 0;
//		try {
//			ps = con.prepareStatement(sql);
//			ps.setInt(1, employee.getEmpId());
//			ps.setString(2, employee.getName());
//			ps.setInt(3, employee.getAge());
//			ps.setString(4, employee.getDepartment());
//			ps.setString(5, employee.getDesignation());
//			ps.setString(6, employee.getCountry());
//			executeUpdate = ps.executeUpdate();
//			System.out.println(executeUpdate);
//		} catch (SQLException e) {
//			System.out.println("Error while statement execution :");
//			e.printStackTrace();
//		}
//		System.out.println((executeUpdate > 0) ? "success" : "fail");
//		return (executeUpdate > 0) ? true : false;	
//		Hihernate Implementation 
		Transaction tnx = null;
		Integer id = -1;

		try (Session session = factory.openSession()) {
			tnx = session.beginTransaction();

			// Insert data into table by supplying the persistent object
			id = (Integer) session.save(employee);

			System.out.println("\nEmployee inserted successfully with ID - " + id);

			tnx.commit();
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		}

		return id;
	}

	@Override
	public boolean delete(int empId) {
		return false;
	}

}
