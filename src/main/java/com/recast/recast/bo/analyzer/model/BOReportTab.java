package com.recast.recast.bo.analyzer.model;

import java.util.ArrayList;
import java.util.List;

public class BOReportTab {
	public static enum ReportElementType{VTable, Section, Visualization, XTable, HTable}
	
	private int reportTabId;
	private String reportTabName;
	private List<QueryFilter> queryFilters = new ArrayList<QueryFilter>();
	private List<BOTableElement> boTableElements = new ArrayList<BOTableElement>();
	private List<BOChartElement> boChartElements = new ArrayList<BOChartElement>();
	
	public int getReportTabId() {
		return reportTabId;
	}
	public void setReportTabId(int reportTabId) {
		this.reportTabId = reportTabId;
	}
	public String getReportTabName() {
		return reportTabName;
	}
	public void setReportTabName(String reportTabName) {
		this.reportTabName = reportTabName;
	}
	public List<QueryFilter> getQueryFilters() {
		return queryFilters;
	}
	public void setQueryFilters(List<QueryFilter> queryFilters) {
		this.queryFilters = queryFilters;
	}
	public List<BOTableElement> getBoTableElements() {
		return boTableElements;
	}
	public void setBoTableElements(List<BOTableElement> boTableElements) {
		this.boTableElements = boTableElements;
	}
	public List<BOChartElement> getBoChartElements() {
		return boChartElements;
	}
	public void setBoChartElements(List<BOChartElement> boChartElements) {
		this.boChartElements = boChartElements;
	}
	@Override
	public String toString() {
		return "BOReportTab [reportTabId=" + reportTabId + ", reportTabName=" + reportTabName + ", queryFilters="
				+ queryFilters + ", boTableElements=" + boTableElements + ", boChartElements=" + boChartElements + "]";
	}
	
}
