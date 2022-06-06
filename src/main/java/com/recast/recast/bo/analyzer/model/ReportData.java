package com.recast.recast.bo.analyzer.model;

import java.util.Set;

public class ReportData {

	//private String reportPath;
	
	private String reportId;
	private String reportName;
	private String size;
	private String date;
	private Set<String> universes;
	
	
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
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Set<String> getUniverses() {
		return universes;
	}
	public void setUniverses(Set<String> universes) {
		this.universes = universes;
	}
	@Override
	public String toString() {
		return "ReportData [reportId=" + reportId + ", reportName=" + reportName + ", size=" + size + ", date=" + date
				+ ", universes=" + universes + "]";
	}
	
}
