package com.recast.recast.bo.analyzer.model;

public class PrjRptConParamsModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String projectReportCon;

	private RptConParamTypeModel rptConParamType;
	private String rptConParamValue;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectReportCon() {
		return this.projectReportCon;
	}

	public void setProjectReportCon(String projectReportCon) {
		this.projectReportCon = projectReportCon;
	}

	public RptConParamTypeModel getRptConParamType() {
		return this.rptConParamType;
	}

	public void setRptConParamType(RptConParamTypeModel rptConParamType) {
		this.rptConParamType = rptConParamType;
	}

	public String getRptConParamValue() {
		return this.rptConParamValue;
	}

	public void setRptConParamValue(String rptConParamValue) {
		this.rptConParamValue = rptConParamValue;
	}

	@Override
	public String toString() {
		return "PrjRptConParamsModel [id=" + id + ", projectReportCon=" + projectReportCon + ", rptConParamType="
				+ rptConParamType + ", rptConParamValue=" + rptConParamValue + "]";
	}

}
