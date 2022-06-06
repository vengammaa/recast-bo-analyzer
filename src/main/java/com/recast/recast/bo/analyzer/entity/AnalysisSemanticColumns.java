package com.recast.recast.bo.analyzer.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AnalysisSemanticColumns implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer taskId;
	
	private Integer semanticId;
	
	private String semanticName;
	
	private String columnQualification;
	
	private String columnNames;
	
	private String dataType;
	
	private String functions;
	
	private String objectIdentifier;
	
	

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

	public Integer getSemanticId() {
		return semanticId;
	}

	public void setSemanticId(Integer semanticId) {
		this.semanticId = semanticId;
	}

	public String getSemanticName() {
		return semanticName;
	}

	public void setSemanticName(String semanticName) {
		this.semanticName = semanticName;
	}

	public String getColumnQualification() {
		return columnQualification;
	}

	public void setColumnQualification(String columnQualification) {
		this.columnQualification = columnQualification;
	}

	public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	

	public String getFunctions() {
		return functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	public String getObjectIdentifier() {
		return objectIdentifier;
	}

	public void setObjectIdentifier(String objectIdentifier) {
		this.objectIdentifier = objectIdentifier;
	}

	@Override
	public String toString() {
		return "AnalysisSemanticColumns [id=" + id + ", taskId=" + taskId + ", semanticId=" + semanticId
				+ ", semanticName=" + semanticName + ", columnQualification=" + columnQualification + ", columnNames="
				+ columnNames + ", dataType=" + dataType + ", functions=" + functions + ", objectIdentifier="
				+ objectIdentifier + "]";
	}

	
	
}
