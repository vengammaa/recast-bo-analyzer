package com.recast.recast.bo.analyzer.model;

import java.time.LocalDate;

public class AnalysisReportModel {
	private int id;
	private Integer prjRptAnalysisId;

	private String columnNames;
	private String reportType;
//	private int reportId;
	private String reportId;
	
	private String reportName;
	private String reportUpdatedDate;
	private LocalDate reportCreationDate;
	private String folderPath;
	private int numberOfInstances;
	private int numberOfRecurringInstances;
	private int averageRunTime;
	private int totalSize;
	private int totalUniverseCount;
	private int numberOfBlocks;
	private int numberOfFormulas;
	private int numberOfTabs;
	private int numberOfFilters;
	private int numberOfColumns;
	private int numberOfQuery;
	private String universeName;
	private String universePath;
	private boolean isReportScheduled; // Changed from String to boolean
	private int universeId;
	private int numberOfRows;
	private boolean isActivelyUsed; // Changed from activelyUsed(String) to isActivelyUsed(boolean)
	private int numberOfFailures; // Changed from String to integer
	private String reportUser;
	private boolean isReportPublished;  // Changed from String to boolean
	private int commonalityFactor;
	private String tableColumnMap;
	private String queryList;
	private String tableSet;
	private String chartSet;
	private int numberOfReportPages;
	private int numberOfVariables;
	private int numberOfConditionalBlocks;
	private String pivotTableSet;
	private Double complexity;
	private String tabList;
	private int numberOfImages;
	private int numberOfEmbeddedElements;
	private String exceptionReport;
	private String variableList;
	private String inputControl;
	private String alerters;
	private String dataFilters;
	private String queryFilters;
	private String pageContainer;
	private String containerCount;
	private String pageCount;
	private String promptPages;
	private int promptCount;
	private String conditionalBlocks;
	private String workbookName;
	
	
	public Double getComplexity() {
		return complexity;
	}
	public void setComplexity(Double complexity) {
		this.complexity = complexity;
	}
	public String getTabList() {
		return tabList;
	}
	public void setTabList(String tabList) {
		this.tabList = tabList;
	}
	public int getNumberOfImages() {
		return numberOfImages;
	}
	public void setNumberOfImages(int numberOfImages) {
		this.numberOfImages = numberOfImages;
	}
	public int getNumberOfEmbeddedElements() {
		return numberOfEmbeddedElements;
	}
	public void setNumberOfEmbeddedElements(int numberOfEmbeddedElements) {
		this.numberOfEmbeddedElements = numberOfEmbeddedElements;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	public Integer getPrjRptAnalysisId() {
		return prjRptAnalysisId;
	}
	public void setPrjRptAnalysisId(Integer prjRptAnalysisId) {
		this.prjRptAnalysisId = prjRptAnalysisId;
	}

	public String getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
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
	
	
	public String getReportUpdatedDate() {
		return reportUpdatedDate;
	}
	public void setReportUpdatedDate(String reportUpdatedDate) {
		this.reportUpdatedDate = reportUpdatedDate;
	}
	public LocalDate getReportCreationDate() {
		return reportCreationDate;
	}
	public void setReportCreationDate(LocalDate reportCreationDate) {
		this.reportCreationDate = reportCreationDate;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	public int getNumberOfInstances() {
		return numberOfInstances;
	}
	public void setNumberOfInstances(int numberOfInstances) {
		this.numberOfInstances = numberOfInstances;
	}
	public int getNumberOfRecurringInstances() {
		return numberOfRecurringInstances;
	}
	public void setNumberOfRecurringInstances(int numberOfRecurringInstances) {
		this.numberOfRecurringInstances = numberOfRecurringInstances;
	}
	public int getAverageRunTime() {
		return averageRunTime;
	}
	public void setAverageRunTime(int averageRunTime) {
		this.averageRunTime = averageRunTime;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getTotalUniverseCount() {
		return totalUniverseCount;
	}
	public void setTotalUniverseCount(int totalUniverseCount) {
		this.totalUniverseCount = totalUniverseCount;
	}
	public int getNumberOfBlocks() {
		return numberOfBlocks;
	}
	public void setNumberOfBlocks(int numberOfBlocks) {
		this.numberOfBlocks = numberOfBlocks;
	}
	public int getNumberOfFormulas() {
		return numberOfFormulas;
	}
	public void setNumberOfFormulas(int numberOfFormulas) {
		this.numberOfFormulas = numberOfFormulas;
	}
	public int getNumberOfTabs() {
		return numberOfTabs;
	}
	public void setNumberOfTabs(int numberOfTabs) {
		this.numberOfTabs = numberOfTabs;
	}
	public int getNumberOfFilters() {
		return numberOfFilters;
	}
	public void setNumberOfFilters(int numberOfFilters) {
		this.numberOfFilters = numberOfFilters;
	}
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	public int getNumberOfQuery() {
		return numberOfQuery;
	}
	public void setNumberOfQuery(int numberOfQuery) {
		this.numberOfQuery = numberOfQuery;
	}
	public String getUniverseName() {
		return universeName;
	}
	public void setUniverseName(String universeName) {
		this.universeName = universeName;
	}
	public String getUniversePath() {
		return universePath;
	}
	public void setUniversePath(String universePath) {
		this.universePath = universePath;
	}
	public boolean isReportScheduled() {
		return isReportScheduled;
	}
	public void setReportScheduled(boolean isReportScheduled) {
		this.isReportScheduled = isReportScheduled;
	}
	public int getUniverseId() {
		return universeId;
	}
	public void setUniverseId(int universeId) {
		this.universeId = universeId;
	}
	public int getNumberOfRows() {
		return numberOfRows;
	}
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	public boolean isActivelyUsed() {
		return isActivelyUsed;
	}
	public void setActivelyUsed(boolean isActivelyUsed) {
		this.isActivelyUsed = isActivelyUsed;
	}
	public int getNumberOfFailures() {
		return numberOfFailures;
	}
	public void setNumberOfFailures(int numberOfFailures) {
		this.numberOfFailures = numberOfFailures;
	}
	public String getReportUser() {
		return reportUser;
	}
	public void setReportUser(String reportUser) {
		this.reportUser = reportUser;
	}
	public boolean isReportPublished() {
		return isReportPublished;
	}
	public void setReportPublished(boolean isReportPublished) {
		this.isReportPublished = isReportPublished;
	}
	public int getCommonalityFactor() {
		return commonalityFactor;
	}
	public void setCommonalityFactor(int commonalityFactor) {
		this.commonalityFactor = commonalityFactor;
	}
	public String getTableColumnMap() {
		return tableColumnMap;
	}
	public void setTableColumnMap(String tableColumnMap) {
		this.tableColumnMap = tableColumnMap;
	}
	public String getQueryList() {
		return queryList;
	}
	public void setQueryList(String queryList) {
		this.queryList = queryList;
	}
	public String getTableSet() {
		return tableSet;
	}
	public void setTableSet(String tableSet) {
		this.tableSet = tableSet;
	}
	public String getChartSet() {
		return chartSet;
	}
	public void setChartSet(String chartSet) {
		this.chartSet = chartSet;
	}
	
	public String getPivotTableSet() {
		return pivotTableSet;
	}
	public void setPivotTableSet(String pivotTableSet) {
		this.pivotTableSet = pivotTableSet;
	}
	public int getNumberOfReportPages() {
		return numberOfReportPages;
	}
	public void setNumberOfReportPages(int numberOfReportPages) {
		this.numberOfReportPages = numberOfReportPages;
	}
	public int getNumberOfVariables() {
		return numberOfVariables;
	}
	public void setNumberOfVariables(int numberOfVariables) {
		this.numberOfVariables = numberOfVariables;
	}
	public int getNumberOfConditionalBlocks() {
		return numberOfConditionalBlocks;
	}
	public void setNumberOfConditionalBlocks(int numberOfConditionalBlocks) {
		this.numberOfConditionalBlocks = numberOfConditionalBlocks;
	}
	
	
	public String getExceptionReport() {
		return exceptionReport;
	}
	public void setExceptionReport(String exceptionReport) {
		this.exceptionReport = exceptionReport;
	}
	public String getVariableList() {
		return variableList;
	}
	public void setVariableList(String variableList) {
		this.variableList = variableList;
	}
	
	
	public String getInputControl() {
		return inputControl;
	}
	public void setInputControl(String inputControl) {
		this.inputControl = inputControl;
	}
	
	public String getAlerters() {
		return alerters;
	}
	public void setAlerters(String alerters) {
		this.alerters = alerters;
	}
	public String getDataFilters() {
		return dataFilters;
	}
	public void setDataFilters(String dataFilters) {
		this.dataFilters = dataFilters;
	}
	
	public String getQueryFilters() {
		return queryFilters;
	}
	public void setQueryFilters(String queryFilters) {
		this.queryFilters = queryFilters;
	}

	public String getPageContainer() {
		return pageContainer;
	}
	public void setPageContainer(String pageContainer) {
		this.pageContainer = pageContainer;
	}
	public String getContainerCount() {
		return containerCount;
	}
	public void setContainerCount(String containerCount) {
		this.containerCount = containerCount;
	}
	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getPromptPages() {
		return promptPages;
	}
	public void setPromptPages(String promptPages) {
		this.promptPages = promptPages;
	}
	
	
	
	public int getPromptCount() {
		return promptCount;
	}
	public void setPromptCount(int promptCount) {
		this.promptCount = promptCount;
	}

	public String getConditionalBlocks() {
		return conditionalBlocks;
	}
	public void setConditionalBlocks(String conditionalBlocks) {
		this.conditionalBlocks = conditionalBlocks;
	}
	
	public String getWorkbookName() {
		return workbookName;
	}
	public void setWorkbookName(String workbookName) {
		this.workbookName = workbookName;
	}
	@Override
	public String toString() {
		return "AnalysisReportModel [id=" + id + ", prjRptAnalysisId=" + prjRptAnalysisId + ", columnNames="
				+ columnNames + ", reportType=" + reportType + ", reportId=" + reportId + ", reportName=" + reportName
				+ ", reportUpdatedDate=" + reportUpdatedDate + ", reportCreationDate=" + reportCreationDate
				+ ", folderPath=" + folderPath + ", numberOfInstances=" + numberOfInstances
				+ ", numberOfRecurringInstances=" + numberOfRecurringInstances + ", averageRunTime=" + averageRunTime
				+ ", totalSize=" + totalSize + ", totalUniverseCount=" + totalUniverseCount + ", numberOfBlocks="
				+ numberOfBlocks + ", numberOfFormulas=" + numberOfFormulas + ", numberOfTabs=" + numberOfTabs
				+ ", numberOfFilters=" + numberOfFilters + ", numberOfColumns=" + numberOfColumns + ", numberOfQuery="
				+ numberOfQuery + ", universeName=" + universeName + ", universePath=" + universePath
				+ ", isReportScheduled=" + isReportScheduled + ", universeId=" + universeId + ", numberOfRows="
				+ numberOfRows + ", isActivelyUsed=" + isActivelyUsed + ", numberOfFailures=" + numberOfFailures
				+ ", reportUser=" + reportUser + ", isReportPublished=" + isReportPublished + ", commonalityFactor="
				+ commonalityFactor + ", tableColumnMap=" + tableColumnMap + ", queryList=" + queryList + ", tableSet="
				+ tableSet + ", chartSet=" + chartSet + ", numberOfReportPages=" + numberOfReportPages
				+ ", numberOfVariables=" + numberOfVariables + ", numberOfConditionalBlocks="
				+ numberOfConditionalBlocks + ", pivotTableSet=" + pivotTableSet + ", complexity=" + complexity
				+ ", tabList=" + tabList + ", numberOfImages=" + numberOfImages + ", numberOfEmbeddedElements="
				+ numberOfEmbeddedElements + ", exceptionReport=" + exceptionReport + ", variableList=" + variableList
				+ ", inputControl=" + inputControl + ", alerters=" + alerters + ", dataFilters=" + dataFilters
				+ ", queryFilters=" + queryFilters + ", pageContainer=" + pageContainer + ", containerCount="
				+ containerCount + ", pageCount=" + pageCount + ", promptPages=" + promptPages + ", promptCount="
				+ promptCount + ", conditionalBlocks=" + conditionalBlocks + ", workbookName=" + workbookName + "]";
	}
	
	
	
	
}
