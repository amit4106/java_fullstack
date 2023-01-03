package com.examples.spring.core.java;

public class Person {

	private Students students;

	public Person() {}
	public Person(Students students) {
		super();
		this.students = students;
	}
	public Students getStudents() {
		return students;
	}
	public void setStudents(Students students) {
		this.students = students;
	}
	@Override
	public String toString() {
		return "Person [students=" + students + "]";
	}

	
	
	
}
