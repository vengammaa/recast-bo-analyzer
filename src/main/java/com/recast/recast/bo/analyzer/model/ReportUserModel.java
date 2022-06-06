package com.recast.recast.bo.analyzer.model;

public class ReportUserModel implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String reportUser;
	
	private int count;

	public String getReportUser() {
		return reportUser;
	}

	public void setReportUser(String reportUser) {
		this.reportUser = reportUser;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ReportUserModel [reportUser=" + reportUser + ", count=" + count + "]";
	}

	
	
	
	
}
