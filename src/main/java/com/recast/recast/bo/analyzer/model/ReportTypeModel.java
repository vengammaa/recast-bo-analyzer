package com.recast.recast.bo.analyzer.model;

public class ReportTypeModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private StatusModel status;
	private String name;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public StatusModel getStatus() {
		return this.status;
	}

	public void setStatus(StatusModel status) {
		this.status = status;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ReportTypeModel [code=" + code + ", status=" + status + ", name=" + name + "]";
	}

}
