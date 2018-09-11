package com.toab.app.signup.model;

public class UserVO extends UserRegistrationForm {
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", details=" + super.toString() + "]";
	}
	
	

}
