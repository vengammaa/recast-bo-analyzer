package com.recast.recast.bo.analyzer.model;

public class ReportTypeUniverseModel implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String reportType;
	
	private int count;

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ReportTypeUniverseModel [reportType=" + reportType + ", count=" + count + "]";
	}
	
	
}
