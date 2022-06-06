package com.recast.recast.bo.analyzer.model;

public class BOCell {

	
	private String cellElementName;

	private String alwaysFlag;
	
	private String minimalWidth;
	
	private String minimalHeight;
	
	private String positionX;
	
	private String positionY;
	
	private String backgroundColor;
	
	private String border;
	
	private String font;
	
	private String allignment;
	
	private String formula;

	public String getCellElementName() {
		return cellElementName;
	}

	public void setCellElementName(String cellElementName) {
		this.cellElementName = cellElementName;
	}

	public String getAlwaysFlag() {
		return alwaysFlag;
	}

	public void setAlwaysFlag(String alwaysFlag) {
		this.alwaysFlag = alwaysFlag;
	}

	public String getMinimalWidth() {
		return minimalWidth;
	}

	public void setMinimalWidth(String minimalWidth) {
		this.minimalWidth = minimalWidth;
	}

	public String getMinimalHeight() {
		return minimalHeight;
	}

	public void setMinimalHeight(String minimalHeight) {
		this.minimalHeight = minimalHeight;
	}

	public String getPositionX() {
		return positionX;
	}

	public void setPositionX(String positionX) {
		this.positionX = positionX;
	}

	public String getPositionY() {
		return positionY;
	}

	public void setPositionY(String positionY) {
		this.positionY = positionY;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getAllignment() {
		return allignment;
	}

	public void setAllignment(String allignment) {
		this.allignment = allignment;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	@Override
	public String toString() {
		return "BOCell [cellElementName=" + cellElementName + ", alwaysFlag=" + alwaysFlag + ", minimalWidth="
				+ minimalWidth + ", minimalHeight=" + minimalHeight + ", positionX=" + positionX + ", positionY="
				+ positionY + ", backgroundColor=" + backgroundColor + ", border=" + border + ", font=" + font
				+ ", allignment=" + allignment + ", formula=" + formula + "]";
	}
	
	
}
