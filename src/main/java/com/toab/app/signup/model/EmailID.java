package com.toab.app.signup.model;

public class EmailID {

	private String name;
	private String domain;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + "@" + domain;
	}
	public EmailID() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmailID(String name, String domain) {
		super();
		this.name = name;
		this.domain = domain;
	}
	
	
}
