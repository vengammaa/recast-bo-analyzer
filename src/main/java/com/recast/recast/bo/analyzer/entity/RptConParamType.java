package com.recast.recast.bo.analyzer.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class RptConParamType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	@ManyToOne(cascade = CascadeType.DETACH)
	private ReportType reportType;

	private String name;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ReportType getReportType() {
		return this.reportType;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
