package com.revature.models;

import java.util.Objects;

public class User {
	// Initial attribute creation; Leaving everything public as a start
	private int userId;
	private String userName;
	private String passwordHash;
	private int userRoleId;
	private String firstName;
	private String lastName;
	private String email;
	// TODO: Implement a picture class to work with profile pictures (optional functionality)
	// Picture profilePicture;
	
	// No args constructor
	public User() {
		super();
	}
	
	// All args constructor (pictures of users will be added individually)
	public User(int userId, 
			String userName, 
			String passwordHash, 
			int userRoleId, 
			String firstName, 
			String lastName, 
			String email) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.passwordHash = passwordHash;
		this.userRoleId = userRoleId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, passwordHash, userId, userName, userRoleId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(passwordHash, other.passwordHash)
				&& userId == other.userId && Objects.equals(userName, other.userName) && userRoleId == other.userRoleId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", passwordHash=" + passwordHash + ", userRoleId="
				+ userRoleId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
	
	
}
