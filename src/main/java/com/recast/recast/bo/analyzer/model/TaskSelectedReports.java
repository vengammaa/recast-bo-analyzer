package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class TaskSelectedReports {

	
	private String reportid;
	
	private String reportname;
	
	private String path;
	
	private List<String> universes;



	public String getReportid() {
		return reportid;
	}

	public void setReportid(String reportid) {
		this.reportid = reportid;
	}

	public String getReportname() {
		return reportname;
	}

	public void setReportname(String reportname) {
		this.reportname = reportname;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getUniverses() {
		return universes;
	}

	public void setUniverses(List<String> universes) {
		this.universes = universes;
	}

	@Override
	public String toString() {
		return "TaskSelectedReports [reportid=" + reportid + ", reportname=" + reportname + ", path=" + path
				+ ", universes=" + universes + "]";
	}

	

}
