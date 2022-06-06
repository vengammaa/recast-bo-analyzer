package com.recast.recast.bo.analyzer.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class User implements java.io.Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	private String userName;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private UserProfile userProfile;
	private String password;
	private int numberAttemps;
	private boolean accountLocked;
	private boolean accountEnabled;

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role_xref", joinColumns = @JoinColumn(name = "user_name", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "role_name", nullable = false, updatable = false))
	private Set<Role> roles = new HashSet<Role>(0);

	@ManyToMany(cascade = CascadeType.DETACH)
	@JoinTable(name = "project_user_xref", joinColumns = @JoinColumn(name = "user_name", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "project_id", nullable = false, updatable = false))
	private Set<Project> projects = new HashSet<Project>(0);
	
	
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserProfile getUserProfile() {
		return this.userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getNumberAttemps() {
		return this.numberAttemps;
	}

	public void setNumberAttemps(int numberAttemps) {
		this.numberAttemps = numberAttemps;
	}

	public boolean isAccountLocked() {
		return this.accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public boolean isAccountEnabled() {
		return this.accountEnabled;
	}

	public void setAccountEnabled(boolean accountEnabled) {
		this.accountEnabled = accountEnabled;
	}


	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Project> getProjects() {
		return projects;
	}
	
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	
	@Override
	public String toString() {
		return "User [userName=" + userName + ", userProfile=" + userProfile + ", password=" + password
				+ ", numberAttemps=" + numberAttemps + ", accountLocked=" + accountLocked + ", accountEnabled="
				+ accountEnabled + ", roles=" + roles + ", projects =" + projects + " ]";
	}
}
