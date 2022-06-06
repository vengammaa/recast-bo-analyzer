package com.recast.recast.bo.analyzer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FolderTask implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer prjFolderAnalysisId;
	
	private String reportId;
	
	private String reportName;
	
	private String reportPath;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrjFolderAnalysisId() {
		return prjFolderAnalysisId;
	}

	public void setPrjFolderAnalysisId(Integer prjFolderAnalysisId) {
		this.prjFolderAnalysisId = prjFolderAnalysisId;
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

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	@Override
	public String toString() {
		return "FolderTask [id=" + id + ", prjFolderAnalysisId=" + prjFolderAnalysisId + ", reportId=" + reportId
				+ ", reportName=" + reportName + ", reportPath=" + reportPath + "]";
	}
	
	
	
	
}
