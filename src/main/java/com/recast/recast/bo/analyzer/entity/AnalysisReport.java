package com.recast.recast.bo.analyzer.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class AnalysisReport implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer prjRptAnalysisId;
	private String columnNames;
	private String reportType;
//	private Integer reportId;
	private String reportId;	
	
	private String reportName;
	private String reportUpdatedDate;
	private LocalDate reportCreationDate = LocalDate.parse("1970-01-01");
	private String folderPath;
	private Integer numberOfInstances =1;
	private Integer numberOfRecurringInstances =1;
	private Integer averageRunTime = -1;
	private Integer totalSize;
	private Integer totalUniverseCount;
	private Integer numberOfBlocks;
	private Integer numberOfFormulas;
	private Integer numberOfTabs;
	private Integer numberOfFilters;
	private Integer numberOfColumns;
	private Integer numberOfQuery;
	private String universeName = "NOT AVAILABLE";
	private String universePath = "NOT AVAILABLE";
	private Boolean isReportScheduled; // Changed from String to Boolean
	private Integer universeId = -1;
	private Integer numberOfRows = -1;
	private Boolean isActivelyUsed; // Changed from activelyUsed(String) to isActivelyUsed(Boolean)
	private Integer numberOfFailures; // Changed from String to Integereger
	private String reportUser;
	private Boolean isReportPublished = false;  // Changed from String to Boolean
	private Integer commonalityFactor;
	private String tableColumnMap = "NOT AVAILABLE";
	private String queryList;
	private String tableSet = "NOT AVAILABLE";
	private String chartSet = "NOT AVAILABLE";
	private Integer numberOfReportPages = -1;
	private Integer numberOfVariables = -1;
	private Integer numberOfConditionalBlocks = -1;
	private String pivotTableSet = "NOT AVAILABLE";
	private Double complexity = -1.0;
	private String tabList = "NOT AVAILABLE";
	private Integer numberOfImages = -1;
	private Integer numberOfEmbeddedElements = -1;
	private String variableList = "NOT AVAILABLE";
	private String exceptionReport;
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
	
	
	public Integer getId() {
		return id;
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
	public Integer getAverageRunTime() {
		return averageRunTime;
	}
	public void setAverageRunTime(Integer averageRunTime) {
		this.averageRunTime = averageRunTime;
	}
	public Integer getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}
	public Integer getTotalUniverseCount() {
		return totalUniverseCount;
	}
	public void setTotalUniverseCount(Integer totalUniverseCount) {
		this.totalUniverseCount = totalUniverseCount;
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
	public Boolean isReportScheduled() {
		return isReportScheduled;
	}
	public void setReportScheduled(Boolean isReportScheduled) {
		this.isReportScheduled = isReportScheduled;
	}
	public Integer getUniverseId() {
		return universeId;
	}
	public void setUniverseId(Integer universeId) {
		this.universeId = universeId;
	}
	public Integer getNumberOfRows() {
		return numberOfRows;
	}
	public void setNumberOfRows(Integer numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	public Boolean isActivelyUsed() {
		return isActivelyUsed;
	}
	public void setActivelyUsed(Boolean isActivelyUsed) {
		this.isActivelyUsed = isActivelyUsed;
	}
	public Integer getNumberOfFailures() {
		return numberOfFailures;
	}
	public void setNumberOfFailures(Integer numberOfFailures) {
		this.numberOfFailures = numberOfFailures;
	}
	public String getReportUser() {
		return reportUser;
	}
	public void setReportUser(String reportUser) {
		this.reportUser = reportUser;
	}
	public Boolean isReportPublished() {
		return isReportPublished;
	}
	public void setReportPublished(Boolean isReportPublished) {
		this.isReportPublished = isReportPublished;
	}
	public Integer getCommonalityFactor() {
		return commonalityFactor;
	}
	public void setCommonalityFactor(Integer commonalityFactor) {
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
	public Integer getNumberOfReportPages() {
		return numberOfReportPages;
	}
	public void setNumberOfReportPages(Integer numberOfReportPages) {
		this.numberOfReportPages = numberOfReportPages;
	}
	public Integer getNumberOfVariables() {
		return numberOfVariables;
	}
	public void setNumberOfVariables(Integer numberOfVariables) {
		this.numberOfVariables = numberOfVariables;
	}
	public Integer getNumberOfConditionalBlocks() {
		return numberOfConditionalBlocks;
	}
	public void setNumberOfConditionalBlocks(Integer numberOfConditionalBlocks) {
		this.numberOfConditionalBlocks = numberOfConditionalBlocks;
	}
	



	public Integer getPrjRptAnalysisId() {
		return prjRptAnalysisId;
	}


	public void setPrjRptAnalysisId(Integer prjRptAnalysisId) {
		this.prjRptAnalysisId = prjRptAnalysisId;
	}


	public Boolean getIsReportScheduled() {
		return isReportScheduled;
	}


	public void setIsReportScheduled(Boolean isReportScheduled) {
		this.isReportScheduled = isReportScheduled;
	}


	public Boolean getIsActivelyUsed() {
		return isActivelyUsed;
	}


	public void setIsActivelyUsed(Boolean isActivelyUsed) {
		this.isActivelyUsed = isActivelyUsed;
	}


	public Boolean getIsReportPublished() {
		return isReportPublished;
	}


	public void setIsReportPublished(Boolean isReportPublished) {
		this.isReportPublished = isReportPublished;
	}


	public void setId(Integer id) {
		this.id = id;
	}


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
		return "AnalysisReport [id=" + id + ", prjRptAnalysisId=" + prjRptAnalysisId + ", columnNames=" + columnNames
				+ ", reportType=" + reportType + ", reportId=" + reportId + ", reportName=" + reportName
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
				+ numberOfEmbeddedElements + ", variableList=" + variableList + ", exceptionReport=" + exceptionReport
				+ ", inputControl=" + inputControl + ", alerters=" + alerters + ", dataFilters=" + dataFilters
				+ ", queryFilters=" + queryFilters + ", pageContainer=" + pageContainer + ", containerCount="
				+ containerCount + ", pageCount=" + pageCount + ", promptPages=" + promptPages + ", promptCount="
				+ promptCount + ", conditionalBlocks=" + conditionalBlocks + ", workbookName=" + workbookName + "]";
	}

}
