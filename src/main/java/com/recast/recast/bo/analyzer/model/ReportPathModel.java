package com.recast.recast.bo.analyzer.model;

import java.util.List;
public class ReportPathModel implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String path;
	
	private List<ReportPathDataModel> reports;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<ReportPathDataModel> getReports() {
		return reports;
	}

	public void setReports(List<ReportPathDataModel> reports) {
		this.reports = reports;
	}

	@Override
	public String toString() {
		return "ReportPathModel [path=" + path + ", reports=" + reports + "]";
	}
	
}
