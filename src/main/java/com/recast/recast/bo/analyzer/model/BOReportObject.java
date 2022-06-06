package com.recast.recast.bo.analyzer.model;

public class BOReportObject {

	private String dataProviderObjectId;
	
	private String dataProviderObjectName;
	
	private String dataSourceObjectId;
	
	private String formulaLanguageId;

	
	public String getDataProviderObjectId() {
		return dataProviderObjectId;
	}

	public void setDataProviderObjectId(String dataProviderObjectId) {
		this.dataProviderObjectId = dataProviderObjectId;
	}

	public String getDataProviderObjectName() {
		return dataProviderObjectName;
	}

	public void setDataProviderObjectName(String dataProviderObjectName) {
		this.dataProviderObjectName = dataProviderObjectName;
	}

	public String getDataSourceObjectId() {
		return dataSourceObjectId;
	}

	public void setDataSourceObjectId(String dataSourceObjectId) {
		this.dataSourceObjectId = dataSourceObjectId;
	}

	public String getFormulaLanguageId() {
		return formulaLanguageId;
	}

	public void setFormulaLanguageId(String formulaLanguageId) {
		this.formulaLanguageId = formulaLanguageId;
	}

	@Override
	public String toString() {
		return "BOReportObject [dataProviderObjectId=" + dataProviderObjectId + ", dataProviderObjectName="
				+ dataProviderObjectName + ", dataSourceObjectId=" + dataSourceObjectId + ", formulaLanguageId="
				+ formulaLanguageId + "]";
	}

}
