package com.toab.app.signup.model;

public class SuccessResponse {

	private String field;
	private String value;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public SuccessResponse(String field, String value) {
		super();
		this.field = field;
		this.value = value;
	}
	public SuccessResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
