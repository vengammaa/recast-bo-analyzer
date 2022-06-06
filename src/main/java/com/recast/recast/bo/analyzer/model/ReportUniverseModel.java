package com.recast.recast.bo.analyzer.model;

import java.util.List;

public class ReportUniverseModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String universeName;
	
	private int count;
	

	private List<ReportTypeUniverseModel> reportTypeModel;
	
	public String getUniverseName() {
		return universeName;
	}

	public void setUniverseName(String universeName) {
		this.universeName = universeName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<ReportTypeUniverseModel> getReportTypeModel() {
		return reportTypeModel;
	}

	public void setReportTypeModel(List<ReportTypeUniverseModel> reportTypeModel) {
		this.reportTypeModel = reportTypeModel;
	}

	@Override
	public String toString() {
		return "ReportUniverseModel [universeName=" + universeName + ", count=" + count + ", reportTypeModel="
				+ reportTypeModel + "]";
	}

	
		
	

	
	
	
	
	
}
