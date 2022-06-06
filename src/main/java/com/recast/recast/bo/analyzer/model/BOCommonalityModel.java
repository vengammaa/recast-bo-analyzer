package com.recast.recast.bo.analyzer.model;

public class BOCommonalityModel {
	private BOAnalyzerModel boAnalyzerModel1;
	private BOAnalyzerModel boAnalyzerModel2;
	private Integer commonality;
	private Boolean identical;
	
	public BOAnalyzerModel getBoAnalyzerModel1() {
		return boAnalyzerModel1;
	}
	public void setBoAnalyzerModel1(BOAnalyzerModel boAnalyzerModel1) {
		this.boAnalyzerModel1 = boAnalyzerModel1;
	}
	public BOAnalyzerModel getBoAnalyzerModer2() {
		return boAnalyzerModel2;
	}
	public void setBoAnalyzerModel2(BOAnalyzerModel boAnalyzerModer2) {
		this.boAnalyzerModel2 = boAnalyzerModer2;
	}
	public Integer getCommonality() {
		return commonality;
	}
	public void setCommonality(Integer commonality) {
		this.commonality = commonality;
	}
	public Boolean getIdentical() {
		return identical;
	}
	public void setIdentical(Boolean identical) {
		this.identical = identical;
	}
	public BOCommonalityModel(BOAnalyzerModel boAnalyzerModel1, BOAnalyzerModel boAnalyzerModel2, Integer commonality,
			Boolean identical) {
		super();
		this.boAnalyzerModel1 = boAnalyzerModel1;
		this.boAnalyzerModel2 = boAnalyzerModel2;
		this.commonality = commonality;
		this.identical = identical;
	}
	@Override
	public String toString() {
		return "BOCommonalityModel [boAnalyzerModel1=" + boAnalyzerModel1 + ", boAnalyzerModel2=" + boAnalyzerModel2
				+ ", commonality=" + commonality + ", identical=" + identical + "]";
	}
	
	
}
