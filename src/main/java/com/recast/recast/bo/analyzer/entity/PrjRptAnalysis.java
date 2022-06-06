package com.recast.recast.bo.analyzer.entity;



import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="prj_rpt_analysis")
public class PrjRptAnalysis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@Basic(optional = true)
	private Project project;
	
	private String reportTypeCode;
	private Integer projectReportConId;
	private String taskName;	
	private String comment;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@Basic(optional = true)
	private TaskStatus taskStatus;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prjFolderAnalysisId", orphanRemoval = true)
	private List<FolderTask> taskFolderdetails;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prjRptAnalysisId", orphanRemoval = true)
	private List<AnalysisReport> analysisReports;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prjRptAnalysisId", orphanRemoval = true)
	private List<CommonalityReport> commonalityReports;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prjRptAnalysisId", orphanRemoval = true)
	private List<UniverseReport> universeReports;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId", orphanRemoval = true)
	private List<AnalysisReportsTable> analysisReportTableList; 
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId", orphanRemoval = true)
	private List<VisualDetails> visualDetailsList; 
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId", orphanRemoval = true)
	private List<AnalysisSemanticColumns> AnalysisSemanticColumnsList; 
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId", orphanRemoval = true)
	private List<CommonalityParams> CommonalityParamsList; 
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId", orphanRemoval = true)
	private List<ComplexityReport> complexityList; 
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	public List<AnalysisReport> getAnalysisReports() {
		return analysisReports;
	}
	public void setAnalysisReports(List<AnalysisReport> analysisReports) {
		this.analysisReports = analysisReports;
	}
	public List<CommonalityReport> getCommonalityReports() {
		return commonalityReports;
	}
	public void setCommonalityReports(List<CommonalityReport> commonalityReports) {
		this.commonalityReports = commonalityReports;
	}
	
	public List<UniverseReport> getUniverseReports() {
		return universeReports;
	}
	public void setUniverseReports(List<UniverseReport> universeReports) {
		this.universeReports = universeReports;
	}
	public List<FolderTask> getTaskFolderdetails() {
		return taskFolderdetails;
	}
	public void setTaskFolderdetails(List<FolderTask> taskFolderdetails) {
		this.taskFolderdetails = taskFolderdetails;
	}
	
	public List<AnalysisReportsTable> getAnalysisReportTableList() {
		return analysisReportTableList;
	}
	public void setAnalysisReportTableList(List<AnalysisReportsTable> analysisReportTableList) {
		this.analysisReportTableList = analysisReportTableList;
	}
	
	public List<VisualDetails> getVisualDetailsList() {
		return visualDetailsList;
	}
	public void setVisualDetailsList(List<VisualDetails> visualDetailsList) {
		this.visualDetailsList = visualDetailsList;
	}
	
	
	public List<AnalysisSemanticColumns> getAnalysisSemanticColumnsList() {
		return AnalysisSemanticColumnsList;
	}
	public void setAnalysisSemanticColumnsList(List<AnalysisSemanticColumns> analysisSemanticColumnsList) {
		AnalysisSemanticColumnsList = analysisSemanticColumnsList;
	}
	
	
	
	
	
	public List<CommonalityParams> getCommonalityParamsList() {
		return CommonalityParamsList;
	}
	public void setCommonalityParamsList(List<CommonalityParams> commonalityParamsList) {
		CommonalityParamsList = commonalityParamsList;
	}
	@Override
	public String toString() {
		return "PrjRptAnalysis [id=" + id + ", project=" + project + ", reportTypeCode=" + reportTypeCode
				+ ", projectReportConId=" + projectReportConId + ", taskName=" + taskName + ", comment=" + comment
				+ ", taskStatus=" + taskStatus + ", taskFolderdetails=" + taskFolderdetails + ", analysisReports="
				+ analysisReports + ", commonalityReports=" + commonalityReports + ", universeReports="
				+ universeReports + ", analysisReportTableList=" + analysisReportTableList + ", visualDetailsList="
				+ visualDetailsList + ", AnalysisSemanticColumnsList=" + AnalysisSemanticColumnsList
				+ ", CommonalityParamsList=" + CommonalityParamsList + "]";
	}
	
	
	

}
