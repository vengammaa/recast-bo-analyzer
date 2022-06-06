package com.recast.recast.bo.analyzer.model;

public class BOComplexityParameters {
	
	private int numberOfDataSources = 0;
	private int numberOfQueries = 0;
	private int numberOfVariables = 0;
	private int numberOfReportTabs = 0;
	private int numberOfReportElements = 0;
	private int numberOfObjects = 0;
	public int getNumberOfDataSources() {
		return numberOfDataSources;
	}
	public void setNumberOfDataSources(int numberOfDataSources) {
		this.numberOfDataSources = numberOfDataSources;
	}
	public int getNumberOfQueries() {
		return numberOfQueries;
	}
	public void setNumberOfQueries(int numberOfQueries) {
		this.numberOfQueries = numberOfQueries;
	}
	public int getNumberOfVariables() {
		return numberOfVariables;
	}
	public void setNumberOfVariables(int numberOfVariables) {
		this.numberOfVariables = numberOfVariables;
	}
	public int getNumberOfReportTabs() {
		return numberOfReportTabs;
	}
	public void setNumberOfReportTabs(int numberOfReportTabs) {
		this.numberOfReportTabs = numberOfReportTabs;
	}
	public int getNumberOfReportElements() {
		return numberOfReportElements;
	}
	public void setNumberOfReportElements(int numberOfReportElements) {
		this.numberOfReportElements = numberOfReportElements;
	}
	public int getNumberOfObjects() {
		return numberOfObjects;
	}
	public void setNumberOfObjects(int numberOfObjects) {
		this.numberOfObjects = numberOfObjects;
	}
	@Override
	public String toString() {
		return "BOComplexityParameters [numberOfDataSources=" + numberOfDataSources + ", numberOfQueries="
				+ numberOfQueries + ", numberOfVariables=" + numberOfVariables + ", numberOfReportTabs="
				+ numberOfReportTabs + ", numberOfReportElements=" + numberOfReportElements + ", numberOfObjects="
				+ numberOfObjects + "]";
	}
	
	
	
	
	
}
