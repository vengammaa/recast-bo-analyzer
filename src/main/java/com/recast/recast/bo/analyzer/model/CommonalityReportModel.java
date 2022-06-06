package com.recast.recast.bo.analyzer.model;

public class CommonalityReportModel {
	private Integer id;
	private Integer prjRptAnalysisId;
	private AnalysisReportModel analysisReport1;	
	private AnalysisReportModel analysisReport2;	
	private Integer commonality;	
	private Boolean identical;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPrjRptAnalysisId() {
		return prjRptAnalysisId;
	}
	public void setPrjRptAnalysisId(Integer prjRptAnalysisId) {
		this.prjRptAnalysisId = prjRptAnalysisId;
	}
	public AnalysisReportModel getAnalysisReport1() {
		return analysisReport1;
	}
	public void setAnalysisReport1(AnalysisReportModel analysisReport1) {
		this.analysisReport1 = analysisReport1;
	}
	public AnalysisReportModel getAnalysisReport2() {
		return analysisReport2;
	}
	public void setAnalysisReport2(AnalysisReportModel analysisReport2) {
		this.analysisReport2 = analysisReport2;
	}
	public Integer getCommonality() {
		return commonality;
	}
	public void setCommonality(Integer commonality) {
		this.commonality = commonality;
	}
	public boolean isIdentical() {
		return identical;
	}
	public void setIdentical(boolean identical) {
		this.identical = identical;
	}
	
	

}
