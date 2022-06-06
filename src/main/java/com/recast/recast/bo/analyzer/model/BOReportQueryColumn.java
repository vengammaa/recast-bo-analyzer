package com.recast.recast.bo.analyzer.model;

public class BOReportQueryColumn {
	private String columnId;
	private String columnName;
	private String columnExpression;
	private String columnDescription;
	private String aggregateFunction;
	private String columnDataType;
	private String columnQualification;
	
	public String getColumnDataType() {
		return columnDataType;
	}
	public void setColumnDataType(String columnDataType) {
		this.columnDataType = columnDataType;
	}
	public String getColumnQualification() {
		return columnQualification;
	}
	public void setColumnQualification(String columnQualification) {
		this.columnQualification = columnQualification;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnExpression() {
		return columnExpression;
	}
	public void setColumnExpression(String columnExpression) {
		this.columnExpression = columnExpression;
	}
	public String getColumnDescription() {
		return columnDescription;
	}
	public void setColumnDescription(String columnDescription) {
		this.columnDescription = columnDescription;
	}
	public String getAggregateFunction() {
		return aggregateFunction;
	}
	public void setAggregateFunction(String aggregateFunction) {
		this.aggregateFunction = aggregateFunction;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((aggregateFunction == null) ? 0 : aggregateFunction
						.hashCode());
		result = prime * result
				+ ((columnDataType == null) ? 0 : columnDataType.hashCode());
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime
				* result
				+ ((columnQualification == null) ? 0 : columnQualification
						.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BOReportQueryColumn other = (BOReportQueryColumn) obj;
		if (aggregateFunction == null) {
			if (other.aggregateFunction != null)
				return false;
		} else if (!aggregateFunction.equals(other.aggregateFunction))
			return false;
		if (columnDataType == null) {
			if (other.columnDataType != null)
				return false;
		} else if (!columnDataType.equals(other.columnDataType))
			return false;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (columnQualification == null) {
			if (other.columnQualification != null)
				return false;
		} else if (!columnQualification.equals(other.columnQualification))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BOReportQueryColumn [columnId=" + columnId + ", columnName=" + columnName + ", columnExpression="
				+ columnExpression + ", columnDescription=" + columnDescription + ", aggregateFunction="
				+ aggregateFunction + ", columnDataType=" + columnDataType + ", columnQualification="
				+ columnQualification + "]";
	}
	
	
}
