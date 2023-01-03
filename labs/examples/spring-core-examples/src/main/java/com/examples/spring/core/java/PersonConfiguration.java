package com.examples.spring.core.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfiguration {
	
	@Bean
	public Person person() {
		Person person=new Person(students());
		return person;
		
	}
	
	@Bean
	public @Autowired Students students() {
		return new Students(1, "Pratik", address());
	}
	
	@Bean
	public Address address() {
		Address add=new Address();
		add.setLocation("Bangaluru");
		add.setPinCode(562456);
		return add;
	}

}
