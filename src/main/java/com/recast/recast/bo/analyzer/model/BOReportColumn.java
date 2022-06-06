package com.recast.recast.bo.analyzer.model;

public class BOReportColumn {
	
	private String columnExpression;
	private String columnDataType;
	
	public String getColumnExpression() {
		return columnExpression;
	}
	public void setColumnExpression(String columnExpression) {
		this.columnExpression = columnExpression;
	}
	public String getColumnDataType() {
		return columnDataType;
	}
	public void setColumnDataType(String columnDataType) {
		this.columnDataType = columnDataType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnDataType == null) ? 0 : columnDataType.hashCode());
		result = prime
				* result
				+ ((columnExpression == null) ? 0 : columnExpression.hashCode());
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
		BOReportColumn other = (BOReportColumn) obj;
		if (columnDataType == null) {
			if (other.columnDataType != null)
				return false;
		} else if (!columnDataType.equals(other.columnDataType))
			return false;
		if (columnExpression == null) {
			if (other.columnExpression != null)
				return false;
		} else if (!columnExpression.equals(other.columnExpression))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BOReportColumn [columnExpression=" + columnExpression + ", columnDataType=" + columnDataType + "]";
	}
	
	
}
