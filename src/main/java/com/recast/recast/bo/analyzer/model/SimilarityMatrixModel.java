package com.recast.recast.bo.analyzer.model;

import javax.persistence.*;

public class SimilarityMatrixModel {

	private String id;
	private int taskId;
	private int reportId1;
	private int reportId2;
	private String Step1;
	private String Step2;
	private String Step3;
	private String Step4;
	private String Step5;
	private String Step6;
	private int commonalityPercentage;
	private String clusterColumn;
	private String step1Description;
	private String step2Description;
	private String step3Description;
	private String step4Description;
	private String step5Description;
	private String step6Description;
	
	

	public SimilarityMatrixModel() {
		super();
	}
	
	public SimilarityMatrixModel(String id, int taskId, int reportId1, int reportId2, int commonalityPercentage, String step1, String step2,
			String step3, String step4, String step5, String step6, String clusterColumn,
			String step1Description, String step2Description, String step3Description, String step4Description,
			String step5Description, String step6Description) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.reportId1 = reportId1;
		this.reportId2 = reportId2;
		Step1 = step1;
		Step2 = step2;
		Step3 = step3;
		Step4 = step4;
		Step5 = step5;
		Step6 = step6;
		this.commonalityPercentage = commonalityPercentage;
		this.clusterColumn = clusterColumn;
		this.step1Description = step1Description;
		this.step2Description = step2Description;
		this.step3Description = step3Description;
		this.step4Description = step4Description;
		this.step5Description = step5Description;
		this.step6Description = step6Description;
	}











	public SimilarityMatrixModel(String id,int taskId, int reportId1, int reportId2, int commonalityPercentage, String step1, String step2, String step3, String step4,
			String step5, String step6, String step1Description,String clusterColumn) {
		super();
		this.id=id;
		this.taskId = taskId;
		this.reportId1 = reportId1;
		this.reportId2 = reportId2;
		this.commonalityPercentage = commonalityPercentage;
		Step1 = step1;
		Step2 = step2;
		Step3 = step3;
		Step4 = step4;
		Step5 = step5;
		Step6 = step6;
		this.step1Description = step1Description;
		this.clusterColumn = clusterColumn;
		
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getReportId1() {
		return reportId1;
	}
	public void setReportId1(int reportId1) {
		this.reportId1 = reportId1;
	}
	public int getReportId2() {
		return reportId2;
	}
	public void setReportId2(int reportId2) {
		this.reportId2 = reportId2;
	}
	public String getStep1() {
		return Step1;
	}
	public void setStep1(String step1) {
		Step1 = step1;
	}
	public String getStep2() {
		return Step2;
	}
	public void setStep2(String step2) {
		Step2 = step2;
	}
	public String getStep3() {
		return Step3;
	}
	public void setStep3(String step3) {
		Step3 = step3;
	}
	public String getStep4() {
		return Step4;
	}
	public void setStep4(String step4) {
		Step4 = step4;
	}
	public String getStep5() {
		return Step5;
	}
	public void setStep5(String step5) {
		Step5 = step5;
	}
	public String getStep6() {
		return Step6;
	}
	public void setStep6(String step6) {
		Step6 = step6;
	}
	
	public int getCommonalityPercentage() {
		return commonalityPercentage;
	}
	public void setCommonalityPercentage(int total_score) {
		this.commonalityPercentage = total_score;
	}
	
	public String getClusterColumn() {
		return clusterColumn;
	}

	public void setClusterColumn(String clusterColumn) {
		this.clusterColumn = clusterColumn;
	}

	public String getStep1Description() {
		return step1Description;
	}

	public void setStep1Description(String step1Description) {
		this.step1Description = step1Description;
	}

	

	public String getStep2Description() {
		return step2Description;
	}

	public void setStep2Description(String step2Description) {
		this.step2Description = step2Description;
	}

	public String getStep3Description() {
		return step3Description;
	}

	public void setStep3Description(String step3Description) {
		this.step3Description = step3Description;
	}

	public String getStep4Description() {
		return step4Description;
	}

	public void setStep4Description(String step4Description) {
		this.step4Description = step4Description;
	}

	public String getStep5Description() {
		return step5Description;
	}

	public void setStep5Description(String step5Description) {
		this.step5Description = step5Description;
	}

	public String getStep6Description() {
		return step6Description;
	}

	public void setStep6Description(String step6Description) {
		this.step6Description = step6Description;
	}

	@Override
	public String toString() {
		return "SimilarityMatrixModel [id=" + id + ", taskId=" + taskId + ", reportId1=" + reportId1 + ", reportId2="
				+ reportId2 + ", Step1=" + Step1 + ", Step2=" + Step2 + ", Step3=" + Step3 + ", Step4=" + Step4
				+ ", Step5=" + Step5 + ", Step6=" + Step6 + ", commonalityPercentage=" + commonalityPercentage
				+ ", clusterColumn=" + clusterColumn + ", step1Description=" + step1Description + ", step2Description="
				+ step2Description + ", step3Description=" + step3Description + ", step4Description=" + step4Description
				+ ", step5Description=" + step5Description + ", step6Description=" + step6Description + "]";
	}


	
	
	
}