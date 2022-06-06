package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class BOReportVisualizationTab {

	private String reportTabName;
	
	private String reportTabId;
	
	private List<BOVisualElements> boVisualElements;

//	private List<BOPageZone> pageZoneElements;
	
//	private List<BOCell> cellElements;
	

	public String getReportTabName() {
		return reportTabName;
	}

	public void setReportTabName(String reportTabName) {
		this.reportTabName = reportTabName;
	}

	public String getReportTabId() {
		return reportTabId;
	}

	public void setReportTabId(String reportTabId) {
		this.reportTabId = reportTabId;
	}

	public List<BOVisualElements> getBoVisualElements() {
		return boVisualElements;
	}

	public void setBoVisualElements(List<BOVisualElements> boVisualElements) {
		this.boVisualElements = boVisualElements;
	}

	@Override
	public String toString() {
		return "BOReportVisualizationTab [reportTabName=" + reportTabName + ", reportTabId=" + reportTabId
				+ ", boVisualElements=" + boVisualElements + "]";
	}

	
	//private BOVisualizationColumn visualizationColumn;

	
}
