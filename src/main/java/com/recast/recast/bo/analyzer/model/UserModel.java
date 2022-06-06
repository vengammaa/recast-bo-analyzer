package com.recast.recast.bo.analyzer.model;


import java.util.ArrayList;
import java.util.List;


public class UserModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	private int numberAttemps;
	private boolean accountLocked;
	private boolean accountEnabled;
	private List<RoleModel> roles = new ArrayList<RoleModel>(0);
	private List<String> projects = new ArrayList<String>(0);
	private UserProfileModel userProfile;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserProfileModel getUserProfile() {
		return this.userProfile;
	}

	public void setUserProfile(UserProfileModel userProfile) {
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

	public List<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleModel> roles) {
		this.roles = roles;
	}
	
	public List<String> getProjects() {
		return projects;
	}
	
	public void setProjects(List<String> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "UserDTO [userName=" + userName + ", password=" + password + ", numberAttemps=" + numberAttemps
				+ ", accountLocked=" + accountLocked + ", accountEnabled=" + accountEnabled + ", roles=" + roles
				+ ", userProfile=" + userProfile + "]";
	}

}
