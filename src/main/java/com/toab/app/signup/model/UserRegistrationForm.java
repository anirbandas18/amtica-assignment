package com.toab.app.signup.model;

public class UserRegistrationForm {
	
	private String firstName;
	private String lastName;
	private PhoneNumber phone;
	private EmailID email;
	//@JsonIgnore
	private String password;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public PhoneNumber getPhone() {
		return phone;
	}
	public void setPhone(PhoneNumber phone) {
		this.phone = phone;
	}
	public EmailID getEmail() {
		return email;
	}
	public void setEmail(EmailID email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserRegistrationForm [firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone
				+ ", email=" + email + "]";
	}
	
	

}
