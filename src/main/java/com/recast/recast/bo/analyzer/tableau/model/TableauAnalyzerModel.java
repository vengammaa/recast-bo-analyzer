package com.recast.recast.bo.analyzer.tableau.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TableauAnalyzerModel {

//	private Integer reportId;
	private String reportId;
	private String reportName;
	private String workbookName;
	private String reportType;
	private String reportFolderPath;
	private String reportUpdatedDate;
	private Boolean isReportScheduled=false;
	private Double reportSize=0.0;
	private String reportUser;
	private Integer numberOfBlocks=0;
	private Integer numberOfFormulas=0;
	private Integer numberOfTabs=0;
	private Integer numberOfFilters=0;
	private Integer numberOfColumns=0;
	private Integer numberOfQuery=0;
	private Integer totalUniverseCount = 0;
	private Boolean activelyUsed=true;
	private Double reportComplexity=0.0;
	private Integer numberOfInstances=1;
	private Integer numberOfRecurringInstances=0;
	private Integer numberOfFailures =0;
	private Boolean isReportFailed=false;
	private List<String> columnNameList = new ArrayList<String>();
	private List<TableauReportTab> tableauReportTabList = new ArrayList<TableauReportTab>();
	private List<TableauReportQuery> tableauReportQueries = new ArrayList<TableauReportQuery>();
	private List<TableauVariable> tableauVariableList = new ArrayList<TableauVariable>();
	private List<TabNewReportComplexity> complexityList = new ArrayList<TabNewReportComplexity>();
	private Integer commonalityFactor=1;
	private Integer numberOfImages=0;
	private Integer numberOfEmbeddedElements=0;
	private List<String> universes = new ArrayList<String>();
	private String exceptionReport = "";
	
	public List<TabNewReportComplexity> getComplexityList() {
		return complexityList;
	}
	public void setComplexityList(List<TabNewReportComplexity> complexityList) {
		this.complexityList = complexityList;
	}
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
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getReportFolderPath() {
		return reportFolderPath;
	}
	public void setReportFolderPath(String reportFolderPath) {
		this.reportFolderPath = reportFolderPath;
	}
	public String getReportUpdatedDate() {
		return reportUpdatedDate;
	}
	public void setReportUpdatedDate(String reportUpdatedDate) {
		this.reportUpdatedDate = reportUpdatedDate;
	}
	public Boolean getIsReportScheduled() {
		return isReportScheduled;
	}
	public void setIsReportScheduled(Boolean isReportScheduled) {
		this.isReportScheduled = isReportScheduled;
	}
	public Double getReportSize() {
		return reportSize;
	}
	public void setReportSize(Double reportSize) {
		this.reportSize = reportSize;
	}
	public String getReportUser() {
		return reportUser;
	}
	public void setReportUser(String reportUser) {
		this.reportUser = reportUser;
	}
	public Integer getNumberOfBlocks() {
		return numberOfBlocks;
	}
	public void setNumberOfBlocks(Integer numberOfBlocks) {
		this.numberOfBlocks = numberOfBlocks;
	}
	public Integer getNumberOfFormulas() {
		return numberOfFormulas;
	}
	public void setNumberOfFormulas(Integer numberOfFormulas) {
		this.numberOfFormulas = numberOfFormulas;
	}
	public Integer getNumberOfTabs() {
		return numberOfTabs;
	}
	public void setNumberOfTabs(Integer numberOfTabs) {
		this.numberOfTabs = numberOfTabs;
	}
	public Integer getNumberOfFilters() {
		return numberOfFilters;
	}
	public void setNumberOfFilters(Integer numberOfFilters) {
		this.numberOfFilters = numberOfFilters;
	}
	public Integer getNumberOfColumns() {
		return numberOfColumns;
	}
	public void setNumberOfColumns(Integer numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	public Integer getNumberOfQuery() {
		return numberOfQuery;
	}
	public void setNumberOfQuery(Integer numberOfQuery) {
		this.numberOfQuery = numberOfQuery;
	}
	public Integer getTotalUniverseCount() {
		return totalUniverseCount;
	}
	public void setTotalUniverseCount(Integer totalUniverseCount) {
		this.totalUniverseCount = totalUniverseCount;
	}
	public Boolean getActivelyUsed() {
		return activelyUsed;
	}
	public void setActivelyUsed(Boolean activelyUsed) {
		this.activelyUsed = activelyUsed;
	}
	public Double getReportComplexity() {
		return reportComplexity;
	}
	public void setReportComplexity(Double reportComplexity) {
		this.reportComplexity = reportComplexity;
	}
	public Integer getNumberOfInstances() {
		return numberOfInstances;
	}
	public void setNumberOfInstances(Integer numberOfInstances) {
		this.numberOfInstances = numberOfInstances;
	}
	public Integer getNumberOfRecurringInstances() {
		return numberOfRecurringInstances;
	}
	public void setNumberOfRecurringInstances(Integer numberOfRecurringInstances) {
		this.numberOfRecurringInstances = numberOfRecurringInstances;
	}
	public Integer getNumberOfFailures() {
		return numberOfFailures;
	}
	public void setNumberOfFailures(Integer numberOfFailures) {
		this.numberOfFailures = numberOfFailures;
	}
	public Boolean getIsReportFailed() {
		return isReportFailed;
	}
	public void setIsReportFailed(Boolean isReportFailed) {
		this.isReportFailed = isReportFailed;
	}
	public List<String> getColumnNameList() {
		return columnNameList;
	}
	public void setColumnNameList(List<String> columnNameList) {
		this.columnNameList = columnNameList;
	}


	public List<TableauReportTab> getTableauReportTabList() {
		return tableauReportTabList;
	}
	public void setTableauReportTabList(List<TableauReportTab> tableauReportTabList) {
		this.tableauReportTabList = tableauReportTabList;
	}
	public List<TableauReportQuery> getTableauReportQueries() {
		return tableauReportQueries;
	}
	public void setTableauReportQueries(List<TableauReportQuery> tableauReportQueries) {
		this.tableauReportQueries = tableauReportQueries;
	}
	public List<TableauVariable> getTableauVariableList() {
		return tableauVariableList;
	}
	public void setTableauVariableList(List<TableauVariable> tableauVariableList) {
		this.tableauVariableList = tableauVariableList;
	}
	public Integer getCommonalityFactor() {
		return commonalityFactor;
	}
	public void setCommonalityFactor(Integer commonalityFactor) {
		this.commonalityFactor = commonalityFactor;
	}
	public Integer getNumberOfImages() {
		return numberOfImages;
	}
	public void setNumberOfImages(Integer numberOfImages) {
		this.numberOfImages = numberOfImages;
	}
	public Integer getNumberOfEmbeddedElements() {
		return numberOfEmbeddedElements;
	}
	public void setNumberOfEmbeddedElements(Integer numberOfEmbeddedElements) {
		this.numberOfEmbeddedElements = numberOfEmbeddedElements;
	}
	public List<String> getUniverses() {
		return universes;
	}
	public void setUniverses(List<String> universes) {
		this.universes = universes;
	}
	public String getExceptionReport() {
		return exceptionReport;
	}
	public void setExceptionReport(String exceptionReport) {
		this.exceptionReport = exceptionReport;
	}
	
	
	
	public String getWorkbookName() {
		return workbookName;
	}
	public void setWorkbookName(String workbookName) {
		this.workbookName = workbookName;
	}
	public String getTableauReportQueriesJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getTableauReportQueries());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	public String getTableauReportTabListJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getTableauReportTabList());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;
		
	}
	
	public String getTableauComplexityParametersJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getComplexityList());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	public String getTableauReportVariableListJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getTableauVariableList());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;
		
	}
	@Override
	public String toString() {
		return "TableauAnalyzerModel [reportId=" + reportId + ", reportName=" + reportName + ", workbookName="
				+ workbookName + ", reportType=" + reportType + ", reportFolderPath=" + reportFolderPath
				+ ", reportUpdatedDate=" + reportUpdatedDate + ", isReportScheduled=" + isReportScheduled
				+ ", reportSize=" + reportSize + ", reportUser=" + reportUser + ", numberOfBlocks=" + numberOfBlocks
				+ ", numberOfFormulas=" + numberOfFormulas + ", numberOfTabs=" + numberOfTabs + ", numberOfFilters="
				+ numberOfFilters + ", numberOfColumns=" + numberOfColumns + ", numberOfQuery=" + numberOfQuery
				+ ", totalUniverseCount=" + totalUniverseCount + ", activelyUsed=" + activelyUsed
				+ ", reportComplexity=" + reportComplexity + ", numberOfInstances=" + numberOfInstances
				+ ", numberOfRecurringInstances=" + numberOfRecurringInstances + ", numberOfFailures="
				+ numberOfFailures + ", isReportFailed=" + isReportFailed + ", columnNameList=" + columnNameList
				+ ", tableauReportTabList=" + tableauReportTabList + ", tableauReportQueries=" + tableauReportQueries
				+ ", tableauVariableList=" + tableauVariableList + ", complexityList=" + complexityList
				+ ", commonalityFactor=" + commonalityFactor + ", numberOfImages=" + numberOfImages
				+ ", numberOfEmbeddedElements=" + numberOfEmbeddedElements + ", universes=" + universes
				+ ", exceptionReport=" + exceptionReport + "]";
	}
	

}
