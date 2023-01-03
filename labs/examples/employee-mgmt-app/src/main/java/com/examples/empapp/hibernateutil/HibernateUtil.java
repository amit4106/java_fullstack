package com.examples.empapp.hibernateutil;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.examples.empapp.model.Employee;



public class HibernateUtil {

	private static final SessionFactory SESSION_FACTORY = builSessionFactory();

	private HibernateUtil() {}

	private static SessionFactory builSessionFactory() {
		SessionFactory sessionFactory = null;
		try {
			sessionFactory = new Configuration().addAnnotatedClass(Employee.class).configure().buildSessionFactory();
		} catch (Throwable e) {
			System.out.println("Intial SessionFactory creation failed :" + e.getMessage());
		}
		return sessionFactory;
	}

	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}

	public static void closeSession() {
		getSessionFactory().close();
	}
}
