package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class PrjRptAnalysisModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;
	private Integer projectId;
	private String reportTypeCode;
	private Integer projectReportConId;
	private String taskName;
	private TaskStatusModel taskStatus;
	private String comment;
	private List<TaskSelectedReports> selectedReportsList;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getReportTypeCode() {
		return reportTypeCode;
	}
	public void setReportTypeCode(String reportTypeCode) {
		this.reportTypeCode = reportTypeCode;
	}
	
	public Integer getProjectReportConId() {
		return projectReportConId;
	}
	public void setProjectReportConId(Integer projectReportConId) {
		this.projectReportConId = projectReportConId;
	}
	public String getTaskName() {
		return taskName;
	}
	public TaskStatusModel getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(TaskStatusModel taskStatus) {
		this.taskStatus = taskStatus;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public List<TaskSelectedReports> getSelectedReportsList() {
		return selectedReportsList;
	}
	public void setSelectedReportsList(List<TaskSelectedReports> selectedReportsList) {
		this.selectedReportsList = selectedReportsList;
	}
	
	@Override
	public String toString() {
		return "PrjRptAnalysisModel [id=" + id + ", projectId=" + projectId + ", reportTypeCode=" + reportTypeCode
				+ ", projectReportConId=" + projectReportConId + ", taskName=" + taskName + ", taskStatus=" + taskStatus
				+ ", comment=" + comment + ", selectedReportsList=" + selectedReportsList + "]";
	}
	
}
