package com.recast.recast.bo.analyzer.model;

public class BOQueryCommonalityVO implements  Comparable<BOQueryCommonalityVO>  {
	private BOReportQuery sourceQuery;
	private BOReportQuery destinationQuery;
	private double commonality;
	
	public BOQueryCommonalityVO(BOReportQuery sourceQuery, BOReportQuery destinationQuery, double commonality) {
		super();
		this.sourceQuery = sourceQuery;
		this.destinationQuery = destinationQuery;
		this.commonality = commonality;
	}
	
	public BOReportQuery getSourceQuery() {
		return sourceQuery;
	}
	
	public BOReportQuery getDestinationQuery() {
		return destinationQuery;
	}
	
	public double getCommonality() {
		return commonality;
	}
	
	public int compareTo(BOQueryCommonalityVO arg0) {
		int compareCommonality = (int)((BOQueryCommonalityVO) arg0).getCommonality();
		return (compareCommonality - (int)this.commonality);
		
	}

	@Override
	public String toString() {
		return "BOQueryCommonalityVO [sourceQuery=" + sourceQuery + ", destinationQuery=" + destinationQuery
				+ ", commonality=" + commonality + "]";
	}

}
