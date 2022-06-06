package com.recast.recast.bo.analyzer.model;

public class UserProfileModel {
	private Integer id;
	private String name;
	private String emailid;
	private String mobileNo;

	public UserProfileModel() {

	}

	public UserProfileModel(Integer id, String name, String emailid, String mobileNo) {
		super();
		this.id = id;
		this.name = name;
		this.emailid = emailid;
		this.mobileNo = mobileNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

}
