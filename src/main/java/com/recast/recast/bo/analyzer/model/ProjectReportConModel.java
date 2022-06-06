package com.recast.recast.bo.analyzer.model;

import java.util.HashSet;
import java.util.Set;

public class ProjectReportConModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String project;

	private ReportTypeModel reportType;

	private String name;
	
	private String status;

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	private Set<PrjRptConParamsModel> prjRptConParams = new HashSet<PrjRptConParamsModel>(0);

	public ProjectReportConModel() {
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public ReportTypeModel getReportType() {
		return this.reportType;
	}

	public void setReportType(ReportTypeModel reportType) {
		this.reportType = reportType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<PrjRptConParamsModel> getPrjRptConParams() {
		return this.prjRptConParams;
	}

	public void setPrjRptConParams(Set<PrjRptConParamsModel> prjRptConParams) {
		this.prjRptConParams = prjRptConParams;
	}


	@Override
	public String toString() {
		return "ProjectReportConModel [id=" + id + ", project=" + project + ", reportType=" + reportType + ", name="
				+ name + ", status=" + status + ", prjRptConParams=" + prjRptConParams + "]";
	}

	

}
