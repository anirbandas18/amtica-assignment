package com.toab.app.signup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserLoginForm {
	
	@JsonIgnore
	private String email;
	@JsonIgnore
	private char[] password;
	private char[] credentials;
	private String ip;
	private String agent;
	public char[] getCredentials() {
		return credentials;
	}
	public void setCredentials(char[] credentials) {
		this.credentials = credentials;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public char[] getPassword() {
		return password;
	}
	public void setPassword(char[] password) {
		this.password = password;
	}
	
}