package com.recast.recast.bo.analyzer.model;

import java.io.Serializable;

public class QueryFilter implements Serializable{

	private static final long serialVersionUID = 1L;
	private String filterExpression;
	private String filterName;
	private String filterValue;
	private String filterOperator;
	
	public String getFilterExpression() {
		return filterExpression;
	}
	public void setFilterExpression(String filterExpression) {
		this.filterExpression = filterExpression;
	}
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public String getFilterValue() {
		return filterValue;
	}
	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}
	public String getFilterOperator() {
		return filterOperator;
	}
	public void setFilterOperator(String filterOperator) {
		this.filterOperator = filterOperator;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((filterName == null) ? 0 : filterName.hashCode());
		result = prime * result
				+ ((filterOperator == null) ? 0 : filterOperator.hashCode());
		result = prime * result
				+ ((filterValue == null) ? 0 : filterValue.hashCode());
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
		QueryFilter other = (QueryFilter) obj;
		if (filterName == null) {
			if (other.filterName != null)
				return false;
		} else if (!filterName.equals(other.filterName))
			return false;
		if (filterOperator == null) {
			if (other.filterOperator != null)
				return false;
		} else if (!filterOperator.equals(other.filterOperator))
			return false;
		if (filterValue == null) {
			if (other.filterValue != null)
				return false;
		} else if (!filterValue.equals(other.filterValue))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Filter [filterExpression=" + filterExpression + ", filterName="
				+ filterName + ", filterValue=" + filterValue
				+ ", filterOperator=" + filterOperator + "]";
	}
	
	
}