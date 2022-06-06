package com.recast.recast.bo.analyzer.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ReportPath implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer pathId;
	private String reportId;
	private String reportName;
	private String reportSize;
	private String reportDate;
	private String universes;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPathId() {
		return pathId;
	}
	public void setPathId(Integer pathId) {
		this.pathId = pathId;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportSize() {
		return reportSize;
	}
	public void setReportSize(String reportSize) {
		this.reportSize = reportSize;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getUniverses() {
		return universes;
	}
	public void setUniverses(String universes) {
		this.universes = universes;
	}
	
	

}
