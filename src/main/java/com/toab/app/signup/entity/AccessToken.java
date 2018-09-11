package com.toab.app.signup.entity;

public class AccessToken {

	private String clientAgent;
	private String ip;
	private Long userId;
	private Long createdOn;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Long createdOn) {
		this.createdOn = createdOn;
	}
	public AccessToken() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getClientAgent() {
		return clientAgent;
	}
	public void setClientAgent(String clientAgent) {
		this.clientAgent = clientAgent;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
