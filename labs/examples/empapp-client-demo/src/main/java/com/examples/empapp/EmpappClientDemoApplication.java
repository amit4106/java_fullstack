package com.examples.empapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EmpappClientDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EmpappClientDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Logic to consume empapp service

        // STEP 1: Create Rest Template
        RestTemplate restClient = new RestTemplate();

        String baseUrl = "http://localhost:8080/employees";


        // STEP 2: Call Rest Service
        //Create employee
        Employee emp = new Employee();
        emp.setName("John");
        emp.setAge(30);
        emp.setDepartment("IT");
        emp.setDesignation("Developer");
        emp.setCountry("India");

        Object res1 = restClient.postForObject(baseUrl, emp, Object.class);
        System.out.println(res1);

        Object res2 = restClient.getForObject(baseUrl, Object.class);

//        Object response1 = restClient.exchange(baseUrl, HttpMethod.GET, null, Object.class);

        // STEP 3: Print the details
        System.out.println(res2);
    }
}
