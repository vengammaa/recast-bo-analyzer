package com.recast.recast.bo.analyzer.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "project_report_con")
public class ProjectReportCon implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne()
	private Project project;

	@ManyToOne(cascade = CascadeType.DETACH)
	private ReportType reportType;

	private String name;
	
	private String status;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "projectReportCon")
	private Set<PrjRptConParams> prjRptConParams = new HashSet<PrjRptConParams>(0);
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "connectionId", orphanRemoval = true)
	private List<ConnectionPath> connectionPathList;
	
	
	public ProjectReportCon() {
	}

	public ProjectReportCon(Project project, ReportType reportType, String name) {
		this.project = project;
		this.reportType = reportType;
		this.name = name;
	}

	public ProjectReportCon(Project project, ReportType reportType, String name, Set<PrjRptConParams> prjRptConParams) {
		this.project = project;
		this.reportType = reportType;
		this.name = name;
		this.prjRptConParams = prjRptConParams;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ReportType getReportType() {
		return this.reportType;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<PrjRptConParams> getPrjRptConParams() {
		return this.prjRptConParams;
	}

	public void setPrjRptConParams(Set<PrjRptConParams> prjRptConParams) {
		this.prjRptConParams = prjRptConParams;
	}

}
