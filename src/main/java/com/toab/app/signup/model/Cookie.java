package com.toab.app.signup.model;

public class Cookie {

	private String sessionId;
	private String accessId;
	private String userName;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getAccessId() {
		return accessId;
	}
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessId == null) ? 0 : accessId.hashCode());
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cookie other = (Cookie) obj;
		if (accessId == null) {
			if (other.accessId != null)
				return false;
		} else if (!accessId.equals(other.accessId))
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Cookie(String sessionId, String accessId, String userName) {
		super();
		this.sessionId = sessionId;
		this.accessId = accessId;
		this.userName = userName;
	}
	public Cookie() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return sessionId + ", " + accessId + ", " + userName;
	}
	
	
}
