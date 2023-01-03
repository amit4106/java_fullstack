package com.examples.spring.core.java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.examples.spring.core.config.GreetingsConfig;

public class Main {

	public static void main(String[] args) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(Person.class);
		Person obj =(Person) context.getBean("person");
		System.out.println(obj);
		
	}
}
