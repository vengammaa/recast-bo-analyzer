package com.recast.recast.bo.analyzer.tableau.model;

import java.util.List;

public class Axes {

	private String role;
	
	private String name;
	
	private List<AxisFormula> formulaList;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AxisFormula> getFormulaList() {
		return formulaList;
	}

	public void setFormulaList(List<AxisFormula> formulaList) {
		this.formulaList = formulaList;
	}
	
	

}
