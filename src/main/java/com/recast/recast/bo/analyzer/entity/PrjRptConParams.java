package com.recast.recast.bo.analyzer.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PrjRptConParams implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private ProjectReportCon projectReportCon;

	@ManyToOne(cascade = CascadeType.DETACH)
	private RptConParamType rptConParamType;
	private String rptConParamValue;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProjectReportCon getProjectReportCon() {
		return this.projectReportCon;
	}

	public void setProjectReportCon(ProjectReportCon projectReportCon) {
		this.projectReportCon = projectReportCon;
	}

	public RptConParamType getRptConParamType() {
		return this.rptConParamType;
	}

	public void setRptConParamType(RptConParamType rptConParamType) {
		this.rptConParamType = rptConParamType;
	}

	public String getRptConParamValue() {
		return this.rptConParamValue;
	}

	public void setRptConParamValue(String rptConParamValue) {
		this.rptConParamValue = rptConParamValue;
	}

}
