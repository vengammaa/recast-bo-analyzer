package com.recast.recast.bo.analyzer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AnalysisReportsTable implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer taskId;
	
//	private Integer reportId;
	
	private String reportId;
	
	private String reportName;
	
	private String dataSource;
	
	private String queryName;
	
	private String tableNames;
	
	private String tableAliasNames;
	
	private String columnList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getTableNames() {
		return tableNames;
	}

	public void setTableNames(String tableNames) {
		this.tableNames = tableNames;
	}

	public String getTableAliasNames() {
		return tableAliasNames;
	}

	public void setTableAliasNames(String tableAliasNames) {
		this.tableAliasNames = tableAliasNames;
	}

	public String getColumnList() {
		return columnList;
	}

	public void setColumnList(String columnList) {
		this.columnList = columnList;
	}

	@Override
	public String toString() {
		return "AnalysisReportsTable [id=" + id + ", taskId=" + taskId + ", reportId=" + reportId + ", reportName="
				+ reportName + ", dataSource=" + dataSource + ", queryName=" + queryName + ", tableNames=" + tableNames
				+ ", tableAliasNames=" + tableAliasNames + ", columnList=" + columnList + "]";
	}

	

	
	
	
}
