package com.recast.recast.bo.analyzer.tableau.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.recast.recast.bo.analyzer.model.ProjectReportConModel;
import com.recast.recast.bo.analyzer.model.UserModel;

public class ProjectModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private StatusModel status;
	private String name;
	private Date startDate;
	private Date endDate;

	private Set<ProjectReportConModel> projectReportCons = new HashSet<ProjectReportConModel>(0);

	private List<UserModel> users = new ArrayList<UserModel>(0);

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StatusModel getStatus() {
		return this.status;
	}

	public void setStatus(StatusModel status) {
		this.status = status;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Set<ProjectReportConModel> getProjectReportCons() {
		return this.projectReportCons;
	}

	public void setProjectReportCons(Set<ProjectReportConModel> projectReportCons) {
		this.projectReportCons = projectReportCons;
	}

	public List<UserModel> getUsers() {
		return this.users;
	}

	public void setUsers(List<UserModel> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "ProjectModel [id=" + id + ", status=" + status + ", name=" + name + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", projectReportCons=" + projectReportCons + ", users=" + users + "]";
	}

}
