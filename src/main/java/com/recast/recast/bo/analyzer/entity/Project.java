package com.recast.recast.bo.analyzer.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Project implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne()
	private Status status;
	private String name;
	private Date startDate;
	private Date endDate;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "project_id")
	@Basic(optional = true)
	private Set<ProjectReportCon> projectReportCons = new HashSet<ProjectReportCon>(0);

	@ManyToMany(cascade = CascadeType.DETACH)
	@Basic(optional = true)
	@JoinTable(name = "project_user_xref", joinColumns = @JoinColumn(name = "project_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "user_name", nullable = false, updatable = false))
	private Set<User> users = new HashSet<User>(0);

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
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

	public Set<ProjectReportCon> getProjectReportCons() {
		return this.projectReportCons;
	}

	public void setProjectReportCons(Set<ProjectReportCon> projectReportCons) {
		this.projectReportCons = projectReportCons;
	}
	
	public void addProjectReportCon(ProjectReportCon p) {
		this.projectReportCons.add(p);
	}

	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	
}
