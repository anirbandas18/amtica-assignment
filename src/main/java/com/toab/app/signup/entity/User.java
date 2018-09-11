package com.toab.app.signup.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email_id")
	private String emailId;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "status")
	private Boolean status;
	@Column(name = "secret_hash")
	private String secretHash;
	/*@Column(name = "permanent_id")
	private String permanentId;*/
	@Column(name = "registered_on")
	private Date registeredOn;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getSecretHash() {
		return secretHash;
	}
	public void setSecretHash(String secretHash) {
		this.secretHash = secretHash;
	}
	public Date getRegisteredOn() {
		return registeredOn;
	}
	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}
	public User() {
		this.status = true;
		this.registeredOn = new Date(System.currentTimeMillis());
	}
	/*public String getPermanentId() {
		return permanentId;
	}
	public void setPermanentId(String permanentId) {
		this.permanentId = permanentId;
	}*/
	
	
}
