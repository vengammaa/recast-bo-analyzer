package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class BOVisualizationModel {

	private String reportName;
	
	private int reportId;
	
	private List<BOReportVisualizationTab> boVisualizationTab;

	
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public List<BOReportVisualizationTab> getBoVisualizationTab() {
		return boVisualizationTab;
	}

	public void setBoVisualizationTab(List<BOReportVisualizationTab> boVisualizationTab) {
		this.boVisualizationTab = boVisualizationTab;
	}

	@Override
	public String toString() {
		return "BOVisualizationModel [reportName=" + reportName + ", reportId=" + reportId + ", boVisualizationTab="
				+ boVisualizationTab + "]";
	}

	
	
}
