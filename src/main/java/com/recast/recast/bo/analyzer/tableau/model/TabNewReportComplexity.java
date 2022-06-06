package com.recast.recast.bo.analyzer.tableau.model;

public class TabNewReportComplexity {
	private Integer numberOfDataSources;
	private Integer numberOfQueries;
	private Integer numberOfVariables;
	private Integer numberOfReportTabs;
	private Integer numberOfReportElements;
	private Integer numberOfObjects;
	private Integer numberOfFilters;
	private Integer numberOfComplexCalculations;
	

	public Integer getNumberOfFilters() {
		return numberOfFilters;
	}
	public void setNumberOfFilters(Integer numberOfFilters) {
		this.numberOfFilters = numberOfFilters;
	}
	public Integer getNumberOfQueries() {
		return numberOfQueries;
	}
	public void setNumberOfQueries(Integer numberOfQueries) {
		this.numberOfQueries = numberOfQueries;
	}
	public Integer getNumberOfReportTabs() {
		return numberOfReportTabs;
	}
	public void setNumberOfReportTabs(Integer numberOfReportTabs) {
		this.numberOfReportTabs = numberOfReportTabs;
	}
	public Integer getNumberOfReportElements() {
		return numberOfReportElements;
	}
	public void setNumberOfReportElements(Integer numberOfReportElements) {
		this.numberOfReportElements = numberOfReportElements;
	}
	public Integer getNumberOfDataSources() {
		return numberOfDataSources;
	}
	public void setNumberOfDataSources(Integer numberOfDataSources) {
		this.numberOfDataSources = numberOfDataSources;
	}
	public Integer getNumberOfVariables() {
		return numberOfVariables;
	}
	public void setNumberOfVariables(Integer numberOfVariables) {
		this.numberOfVariables = numberOfVariables;
	}
	public Integer getNumberOfObjects() {
		return numberOfObjects;
	}
	public void setNumberOfObjects(Integer numberOfObjects) {
		this.numberOfObjects = numberOfObjects;
	}
	public Integer getNumberOfComplexCalculations() {
		return numberOfComplexCalculations;
	}
	public void setNumberOfComplexCalculations(Integer numberOfComplexCalculations) {
		this.numberOfComplexCalculations = numberOfComplexCalculations;
	}
	@Override
	public String toString() {
		return "TabNewReportComplexity [numberOfDataSources=" + numberOfDataSources + ", numberOfQueries="
				+ numberOfQueries + ", numberOfVariables=" + numberOfVariables + ", numberOfReportTabs="
				+ numberOfReportTabs + ", numberOfReportElements=" + numberOfReportElements + ", numberOfObjects="
				+ numberOfObjects + ", numberOfFilters=" + numberOfFilters + ", numberOfComplexCalculations="
				+ numberOfComplexCalculations + "]";
	}
	
	
}
