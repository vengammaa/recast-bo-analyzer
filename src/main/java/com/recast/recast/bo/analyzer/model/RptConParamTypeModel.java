package com.recast.recast.bo.analyzer.model;


public class RptConParamTypeModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String code;

	private ReportTypeModel reportType;

	private String name;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ReportTypeModel getReportType() {
		return this.reportType;
	}

	public void setReportType(ReportTypeModel reportType) {
		this.reportType = reportType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RptConParamTypeModel [code=" + code + ", reportType=" + reportType + ", name=" + name + "]";
	}

}
