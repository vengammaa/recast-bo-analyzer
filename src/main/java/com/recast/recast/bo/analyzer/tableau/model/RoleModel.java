package com.recast.recast.bo.analyzer.tableau.model;

public class RoleModel {
	private String roleName;
	private String description;

	public RoleModel() {

	}

	public RoleModel(String roleName, String description) {
		super();
		this.roleName = roleName;
		this.description = description;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
