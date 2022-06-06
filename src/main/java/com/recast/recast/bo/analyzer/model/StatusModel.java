package com.recast.recast.bo.analyzer.model;

public class StatusModel {
	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public String toString() {
		return ("[Code: " + this.getCode() + ", Name: " + this.getName() + "]");
	}

}
