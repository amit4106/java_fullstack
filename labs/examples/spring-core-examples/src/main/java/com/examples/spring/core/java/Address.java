package com.examples.spring.core.java;

public class Address {

	private String location;
	private int pinCode;
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Address(String location, int pinCode) {
		super();
		this.location = location;
		this.pinCode = pinCode;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getPinCode() {
		return pinCode;
	}
	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}
	@Override
	public String toString() {
		return "Address [location=" + location + ", pinCode=" + pinCode + "]";
	}
	
}
