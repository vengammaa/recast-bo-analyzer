package com.recast.recast.bo.analyzer.entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="prj_rpt_migrator")
public class PrjRptMigrator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@Basic(optional = true)
	private Project project;
	
	private String sourceReportTypeCode;
	private String targetReportTypeCode;
	private Integer projectReportSourceConId;
	private Integer projectReportTargetConId;
	private String taskName;	
	private String comment;
	
	private String sourceTaskName;
	private String sourceUniverseName;
	private String sourceUniverseDesc;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@Basic(optional = true)
	private TaskStatus taskStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getSourceReportTypeCode() {
		return sourceReportTypeCode;
	}

	public void setSourceReportTypeCode(String sourceReportTypeCode) {
		this.sourceReportTypeCode = sourceReportTypeCode;
	}

	public String getTargetReportTypeCode() {
		return targetReportTypeCode;
	}

	public void setTargetReportTypeCode(String targetReportTypeCode) {
		this.targetReportTypeCode = targetReportTypeCode;
	}

	public Integer getProjectReportSourceConId() {
		return projectReportSourceConId;
	}

	public void setProjectReportSourceConId(Integer projectReportSourceConId) {
		this.projectReportSourceConId = projectReportSourceConId;
	}

	public Integer getProjectReportTargetConId() {
		return projectReportTargetConId;
	}

	public void setProjectReportTargetConId(Integer projectReportTargetConId) {
		this.projectReportTargetConId = projectReportTargetConId;
	}

	public String getTaskName() {
		return taskName;
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

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
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
		return "PrjRptMigrator [id=" + id + ", project=" + project + ", sourceReportTypeCode=" + sourceReportTypeCode
				+ ", targetReportTypeCode=" + targetReportTypeCode + ", projectReportSourceConId="
				+ projectReportSourceConId + ", projectReportTargetConId=" + projectReportTargetConId + ", taskName="
				+ taskName + ", comment=" + comment + ", sourceTaskName=" + sourceTaskName + ", sourceUniverseName="
				+ sourceUniverseName + ", sourceUniverseDesc=" + sourceUniverseDesc + ", taskStatus=" + taskStatus
				+ "]";
	}

	
}
