package com.recast.recast.bo.analyzer.tableau.model;

import java.util.Set;

public class ReportData {
	private String id;
	private String name;
	private String contentUrl;
	private String createdAt;
	private String updatedAt;
	private Set<String> universes;
	private String size = "";
	private String projectId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Set<String> getUniverses() {
		return universes;
	}
	public void setUniverses(Set<String> universes) {
		this.universes = universes;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Override
	public String toString() {
		return "ReportPathData [id=" + id + ", name=" + name + ", contentUrl=" + contentUrl + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", universes=" + universes + ", size=" + size + ", projectId="
				+ projectId + "]";
	}
	
}
