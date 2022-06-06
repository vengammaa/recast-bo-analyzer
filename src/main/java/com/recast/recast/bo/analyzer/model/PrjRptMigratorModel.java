package com.recast.recast.bo.analyzer.model;

//Model class for Migrator

public class PrjRptMigratorModel implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer projectId;
	private String sourceReportTypeCode;
	private Integer sourceReportConId;
	private String targetReportTypeCode;
	private Integer targetReportConId;
	private String taskName;
	private TaskStatusModel taskStatus;
	private String comment;
	private String sourceTaskName;
	private String sourceUniverseName;
	private String sourceUniverseDesc;
	
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
	public String getSourceReportTypeCode() {
		return sourceReportTypeCode;
	}
	public void setSourceReportTypeCode(String sourceReportTypeCode) {
		this.sourceReportTypeCode = sourceReportTypeCode;
	}
	public Integer getSourceReportConId() {
		return sourceReportConId;
	}
	public void setSourceReportConId(Integer sourceReportConId) {
		this.sourceReportConId = sourceReportConId;
	}
	public String getTargetReportTypeCode() {
		return targetReportTypeCode;
	}
	public void setTargetReportTypeCode(String targetReportTypeCode) {
		this.targetReportTypeCode = targetReportTypeCode;
	}
	public Integer getTargetReportConId() {
		return targetReportConId;
	}
	public void setTargetReportConId(Integer targetReportConId) {
		this.targetReportConId = targetReportConId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public TaskStatusModel getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(TaskStatusModel taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSourceTaskName() {
		return sourceTaskName;
	}
	public void setSourceTaskName(String sourceTaskName) {
		this.sourceTaskName = sourceTaskName;
	}
	public String getSourceUniverseName() {
		return sourceUniverseName;
	}
	public void setSourceUniverseName(String sourceUniverseName) {
		this.sourceUniverseName = sourceUniverseName;
	}
	public String getSourceUniverseDesc() {
		return sourceUniverseDesc;
	}
	public void setSourceUniverseDesc(String sourceUniverseDesc) {
		this.sourceUniverseDesc = sourceUniverseDesc;
	}
	@Override
	public String toString() {
		return "PrjRptMigratorModel [id=" + id + ", projectId=" + projectId + ", sourceReportTypeCode="
				+ sourceReportTypeCode + ", sourceReportConId=" + sourceReportConId + ", targetReportTypeCode="
				+ targetReportTypeCode + ", targetReportConId=" + targetReportConId + ", taskName=" + taskName
				+ ", taskStatus=" + taskStatus + ", comment=" + comment + ", sourceTaskName=" + sourceTaskName
				+ ", sourceUniverseName=" + sourceUniverseName + ", sourceUniverseDesc=" + sourceUniverseDesc + "]";
	}
	
	
	

	

	
}
