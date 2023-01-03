package com.examples.empapp.dao;

import com.examples.empapp.model.Employee;

public interface EmployeeDao {
	
	public int create(Employee employee);
	
	public boolean delete(int empId);
}
