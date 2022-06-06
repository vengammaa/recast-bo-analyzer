package com.recast.recast.bo.analyzer.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CommonalityReport implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer prjRptAnalysisId;
	
	@ManyToOne()
	private AnalysisReport analysisReport1;
	
	@ManyToOne()
	private AnalysisReport analysisReport2;
	
	private Integer commonality;
	
	private Boolean identical;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrjRptAnalysisId() {
		return prjRptAnalysisId;
	}

	public void setPrjRptAnalysisId(Integer prjRptAnalysisId) {
		this.prjRptAnalysisId = prjRptAnalysisId;
	}

	public AnalysisReport getAnalysisReport1() {
		return analysisReport1;
	}

	public void setAnalysisReport1(AnalysisReport analysisReport1) {
		this.analysisReport1 = analysisReport1;
	}

	public AnalysisReport getAnalysisReport2() {
		return analysisReport2;
	}

	public void setAnalysisReport2(AnalysisReport analysisReport2) {
		this.analysisReport2 = analysisReport2;
	}

	public Integer getCommonality() {
		return commonality;
	}

	public void setCommonality(Integer commonality) {
		this.commonality = commonality;
	}

	public boolean isIdentical() {
		return identical;
	}

	public void setIdentical(boolean identical) {
		this.identical = identical;
	}

	@Override
	public String toString() {
		return "CommonalityReport [id=" + id + ", prjRptAnalysisId=" + prjRptAnalysisId + ", analysisReport1="
				+ analysisReport1.getId() + ", analysisReport2=" + analysisReport2.getId() + ", commonality=" + commonality
				+ ", identical=" + identical + "]";
	}
	
	
	
	
	
}
