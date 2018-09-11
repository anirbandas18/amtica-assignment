package com.toab.app.signup.entity;

public class WebSession {

	private String id;
	private AccessToken token;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public AccessToken getToken() {
		return token;
	}
	public void setToken(AccessToken token) {
		this.token = token;
	}
	public WebSession(String id, AccessToken token) {
		super();
		this.id = id;
		this.token = token;
	}
	public WebSession() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
