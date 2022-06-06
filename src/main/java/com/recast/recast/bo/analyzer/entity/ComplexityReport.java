package com.recast.recast.bo.analyzer.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
public class ComplexityReport implements java.io.Serializable {
	
    private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer complexityId;
	
	private Integer taskId;
	
	private String reportId;
	
	private String reportName;
	
	private String complexityStatus;
	
	private String complexityParameter;
	
	
	public Integer getComplexityId() {
		return complexityId;
	}

	public void setComplexityId(Integer complexityId) {
		this.complexityId = complexityId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
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

	public String getComplexityStatus() {
		return complexityStatus;
	}

	public void setComplexityStatus(String complexityStatus) {
		this.complexityStatus = complexityStatus;
	}

	public String getComplexityParameter() {
		return complexityParameter;
	}

	public void setComplexityParameter(String complexityParameter) {
		this.complexityParameter = complexityParameter;
	}

	@Override
	public String toString() {
		return "ComplexityReport [complexityId=" + complexityId + ", taskId=" + taskId + ", reportId=" + reportId
				+ ", reportName=" + reportName + ", complexityStatus=" + complexityStatus + ", complexityParameter="
				+ complexityParameter + "]";
	}

	
	
	
	
}
