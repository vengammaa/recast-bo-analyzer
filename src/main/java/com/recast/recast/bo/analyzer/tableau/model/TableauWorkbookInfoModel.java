package com.recast.recast.bo.analyzer.tableau.model;

public class TableauWorkbookInfoModel {
	
	private String workbookID;
	private String workbookName;
	private String webPageUrl;
	private String createdDate;
	private String updatedDate;
	private String projectID;
	private String projectName;
	public String getWorkbookID() {
		return workbookID;
	}
	public void setWorkbookID(String workbookID) {
		this.workbookID = workbookID;
	}
	public String getWorkbookName() {
		return workbookName;
	}
	public void setWorkbookName(String workbookName) {
		this.workbookName = workbookName;
	}
	public String getWebPageUrl() {
		return webPageUrl;
	}
	public void setWebPageUrl(String webPageUrl) {
		this.webPageUrl = webPageUrl;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@Override
	public String toString() {
		return "TableauWorkbookInfoModel [workbookID=" + workbookID + ", workbookName=" + workbookName + ", webPageUrl="
				+ webPageUrl + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", projectID="
				+ projectID + ", projectName=" + projectName + "]";
	}
	
}
