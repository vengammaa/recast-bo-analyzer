package com.recast.recast.bo.analyzer.tableau.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TableauVisualizationModel {

	private String reportName;
	private String reportId;
	private List<TableauStyleRule> styleRuleList = new ArrayList<TableauStyleRule>();
	private List<TableauVisualElements> visualElementList = new ArrayList<TableauVisualElements>();
	
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public List<TableauStyleRule> getStyleRuleList() {
		return styleRuleList;
	}
	public void setStyleRuleList(List<TableauStyleRule> styleRuleList) {
		this.styleRuleList = styleRuleList;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public List<TableauVisualElements> getVisualElementList() {
		return visualElementList;
	}
	public void setVisualElementList(List<TableauVisualElements> visualElementList) {
		this.visualElementList = visualElementList;
	}
	@Override
	public String toString() {
		return "TableauVisualizationModel [reportName=" + reportName + ", reportId=" + reportId + ", visualElementList="
				+ visualElementList + ", styleRuleList=" + styleRuleList + "]";
	}
	
	public String getStyleRuleJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getStyleRuleList());
		}
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	
	
}
