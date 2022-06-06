package com.recast.recast.bo.analyzer.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BOAnalyzerModel {
	private Integer reportId;
	private String reportName;
	private String reportType;
	private String reportFolderPath;
	private String reportUpdatedDate;
	private Boolean isReportScheduled;
	private Double reportSize;
	private String reportUser;
	private Integer numberOfBlocks;
	private Integer numberOfFormulas;
	private Integer numberOfTabs;
	private Integer numberOfFilters;
	private Integer numberOfColumns;
	private Integer numberOfQuery;
	private Integer totalUniverseCount = 0;
	private Boolean activelyUsed;
	private Double reportComplexity;
	private Integer numberOfInstances;
	private Integer numberOfRecurringInstances;
	private Integer numberOfFailures;
	private Boolean isReportFailed;
	private List<String> columnNameList = new ArrayList<String>();
	private List<BOReportTab> boReportTabList = new ArrayList<BOReportTab>();
	private List<BOReportQuery> boReportQueries = new ArrayList<BOReportQuery>();
	private List<BOVariable> boVariableList = new ArrayList<BOVariable>();
	private List<BOQueryDetailsModel> boQueryDetailsList = new ArrayList<BOQueryDetailsModel>();
	private Integer commonalityFactor;
	private Integer numberOfImages;
	private Integer numberOfEmbeddedElements;
	private Set<String> universes = new HashSet<String>();
	private String exceptionReport = "";
	private List<InputControl> inputControlList;
	private List<Alerters> alertersList;
	private List<DataFilter> dataFiltersList;
	private List<BOQueryFilter> boQueryFilterList;
	private List<BOComplexityParameters> complexityList = new ArrayList<BOComplexityParameters>();
	

	public Set<String> getUniverses() {
		return universes;
	}
	public void setUniverses(Set<String> universes) {
		this.universes = universes;
	}
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
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
	public List<BOReportTab> getBoReportTabList() {
		return boReportTabList;
	}
	public void setBoReportTabList(List<BOReportTab> boReportTabList) {
		this.boReportTabList = boReportTabList;
	}
	public List<BOReportQuery> getBoReportQueries() {
		return boReportQueries;
	}
	public void setBoReportQueries(List<BOReportQuery> boReportQueries) {
		this.boReportQueries = boReportQueries;
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
	
	

	public List<BOVariable> getBoVariableList() {
		return boVariableList;
	}
	public void setBoVariableList(List<BOVariable> boVariableList) {
		this.boVariableList = boVariableList;
	}
	
	
	public String getExceptionReport() {
		return exceptionReport;
	}
	public void setExceptionReport(String exceptionReport) {
		this.exceptionReport = exceptionReport;
	}
	
	public List<BOQueryDetailsModel> getBoQueryDetailsList() {
		return boQueryDetailsList;
	}
	public void setBoQueryDetailsList(List<BOQueryDetailsModel> boQueryDetailsList) {
		this.boQueryDetailsList = boQueryDetailsList;
	}
	
	public List<InputControl> getInputControlList() {
		return inputControlList;
	}
	
	public void setInputControlList(List<InputControl> inputControlList) {
		this.inputControlList = inputControlList;
	}
	
	public List<Alerters> getAlertersList() {
		return alertersList;
	}
	public void setAlertersList(List<Alerters> alertersList) {
		this.alertersList = alertersList;
	}
	
	public List<DataFilter> getDataFiltersList() {
		return dataFiltersList;
	}
	public void setDataFiltersList(List<DataFilter> dataFiltersList) {
		this.dataFiltersList = dataFiltersList;
	}
	
	public List<BOQueryFilter> getBoQueryFilterList() {
		return boQueryFilterList;
	}
	public void setBoQueryFilterList(List<BOQueryFilter> boQueryFilterList) {
		this.boQueryFilterList = boQueryFilterList;
	}
	
	public List<BOComplexityParameters> getComplexityList() {
		return complexityList;
	}
	public void setComplexityList(List<BOComplexityParameters> complexityList) {
		this.complexityList = complexityList;
	}
	public String getBoReportQueriesJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getBoReportQueries());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	public String getBOReportTabListJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getBoReportTabList());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;
		
	}
	
	public String getBOReportVariableListJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getBoVariableList());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;
		
	}
	
	public String getInputControlListJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getInputControlList());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;		
	}
	
	public String getAlertersListJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getAlertersList());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;		
	}
	
	public String getDataFiltersListJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getDataFiltersList());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;		
	}
	
	public String getBoQueryFilterListJSON() {
		String jsonStr;
		ObjectMapper obj = new ObjectMapper();
		try {
			jsonStr = obj.writeValueAsString(this.getBoQueryFilterList());
		} 
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;		
	}
	
	public String getBOComplexityParameterJSON() {
		String jsonStr = "Empty";
		ObjectMapper obj = new ObjectMapper();
		try {
			if(this.getComplexityList() != null) 
				jsonStr = obj.writeValueAsString(this.getComplexityList());
		}
		catch (JsonProcessingException e) {
			jsonStr = "Error";
			e.printStackTrace();
		}
		return jsonStr;	
	}
	@Override
	public String toString() {
		return "BOAnalyzerModel [reportId=" + reportId + ", reportName=" + reportName + ", reportType=" + reportType
				+ ", reportFolderPath=" + reportFolderPath + ", reportUpdatedDate=" + reportUpdatedDate
				+ ", isReportScheduled=" + isReportScheduled + ", reportSize=" + reportSize + ", reportUser="
				+ reportUser + ", numberOfBlocks=" + numberOfBlocks + ", numberOfFormulas=" + numberOfFormulas
				+ ", numberOfTabs=" + numberOfTabs + ", numberOfFilters=" + numberOfFilters + ", numberOfColumns="
				+ numberOfColumns + ", numberOfQuery=" + numberOfQuery + ", totalUniverseCount=" + totalUniverseCount
				+ ", activelyUsed=" + activelyUsed + ", reportComplexity=" + reportComplexity + ", numberOfInstances="
				+ numberOfInstances + ", numberOfRecurringInstances=" + numberOfRecurringInstances
				+ ", numberOfFailures=" + numberOfFailures + ", isReportFailed=" + isReportFailed + ", columnNameList="
				+ columnNameList + ", boReportTabList=" + boReportTabList + ", boReportQueries=" + boReportQueries
				+ ", boVariableList=" + boVariableList + ", boQueryDetailsList=" + boQueryDetailsList
				+ ", commonalityFactor=" + commonalityFactor + ", numberOfImages=" + numberOfImages
				+ ", numberOfEmbeddedElements=" + numberOfEmbeddedElements + ", universes=" + universes
				+ ", exceptionReport=" + exceptionReport + ", inputControlList=" + inputControlList + ", alertersList="
				+ alertersList + ", dataFiltersList=" + dataFiltersList + ", boQueryFilterList=" + boQueryFilterList
				+ ", complexityList=" + complexityList + "]";
	}
	

}
