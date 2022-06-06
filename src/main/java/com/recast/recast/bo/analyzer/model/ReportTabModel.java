package com.recast.recast.bo.analyzer.model;

public class ReportTabModel {
	private static final long serialVersionUID = 1L;

	private Integer reportId;
	private String reportTabName;
	private String elementName;
	private String elementType;
	private String dataAxisInfo;
	private String layoutDetails;
	private String parentElementName;
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public String getReportTabName() {
		return reportTabName;
	}
	public void setReportTabName(String reportTabName) {
		this.reportTabName = reportTabName;
	}
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	public String getElementType() {
		return elementType;
	}
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}
	public String getDataAxisInfo() {
		return dataAxisInfo;
	}
	public void setDataAxisInfo(String dataAxisInfo) {
		this.dataAxisInfo = dataAxisInfo;
	}
	public String getLayoutDetails() {
		return layoutDetails;
	}
	public void setLayoutDetails(String layoutDetails) {
		this.layoutDetails = layoutDetails;
	}
	public String getParentElementName() {
		return parentElementName;
	}
	public void setParentElementName(String parentElementName) {
		this.parentElementName = parentElementName;
	}
	@Override
	public String toString() {
		return "ReportTabModel [reportId=" + reportId + ", reportTabName=" + reportTabName + ", elementName="
				+ elementName + ", elementType=" + elementType + ", dataAxisInfo=" + dataAxisInfo + ", layoutDetails="
				+ layoutDetails + ", parentElementName=" + parentElementName + "]";
	}
	
	

}
