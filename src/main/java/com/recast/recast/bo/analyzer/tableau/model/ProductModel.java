package com.recast.recast.bo.analyzer.tableau.model;

import java.util.Date;

public class ProductModel implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int productId;
	
	private String productName;
	
	private String segment;
	
	private String fpc;
	
	private String country;
	
	private String price;
	
	private String manufacturer;
	
	private String newSegment;
	
	private Date modifiedDate;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getFpc() {
		return fpc;
	}

	public void setFpc(String fpc) {
		this.fpc = fpc;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getNewSegment() {
		return newSegment;
	}

	public void setNewSegment(String newSegment) {
		this.newSegment = newSegment;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "ProductModel [productId=" + productId + ", productName=" + productName + ", segment=" + segment
				+ ", fpc=" + fpc + ", country=" + country + ", price=" + price + ", manufacturer=" + manufacturer
				+ ", newSegment=" + newSegment + ", modifiedDate=" + modifiedDate + "]";
	}


}
