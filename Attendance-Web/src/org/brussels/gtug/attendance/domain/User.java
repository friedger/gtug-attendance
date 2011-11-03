package org.brussels.gtug.attendance.domain;

import java.io.Serializable;

public class User implements Serializable {

	private String id;

	private String name;

	private String email;

	private Boolean isAdmin;

	private Boolean isFlagged;

	public User() {
		isFlagged = Boolean.FALSE;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public Boolean getIsFlagged() {
		return isFlagged;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setIsFlagged(Boolean isFlagged) {
		this.isFlagged = isFlagged;
	}
}
