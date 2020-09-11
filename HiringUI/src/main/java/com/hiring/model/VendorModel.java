package com.hiring.model;

public class VendorModel {
	private int vendorId;
	private String vendorname;
	private String description;
	private String isprimary;
	private String customer;
	private String reportingto;
	private String emailId;
	private String location;
	private String phonenumber;
	private String status;
	private String companydetails;

	
	public String getCompanydetails() {
		return companydetails;
	}
	public void setCompanydetails(String companydetails) {
		this.companydetails = companydetails;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsprimary() {
		return isprimary;
	}
	public void setIsprimary(String isprimary) {
		this.isprimary = isprimary;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getReportingto() {
		return reportingto;
	}
	public void setReportingto( String reportingto) {
		this.reportingto = reportingto;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
