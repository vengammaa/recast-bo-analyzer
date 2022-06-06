package com.recast.recast.bo.analyzer.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String roleName;
	private String description;

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
