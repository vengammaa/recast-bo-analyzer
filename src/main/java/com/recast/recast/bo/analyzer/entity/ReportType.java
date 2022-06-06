package com.recast.recast.bo.analyzer.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ReportType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	@ManyToOne
	private Status status;
	
	private String name;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
