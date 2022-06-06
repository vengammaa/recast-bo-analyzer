package com.recast.recast.bo.analyzer.dto;

import java.util.List;

public class BoReportConfigDTO extends ReportConfigDTO {
	private String hostname;
	private String port;
	private String username;
	private String password;
	//private String path;
	private List<String> reports;
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<String> getReports() {
		return reports;
	}
	public void setReports(List<String> reports) {
		this.reports = reports;
	}
	@Override
	public String toString() {
		return "BoReportConfigDTO [hostname=" + hostname + ", port=" + port + ", username=" + username + ", password="
				+ password + ", reports=" + reports + "]";
	}
	
	
	
}
