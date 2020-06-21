package com.hiring.model;

public class VendorModel {
	public int vendorId;
	public String vendorname;
	public String description;
	public String isprimary;
	public String customer;
	public String reportingto;
	public String emailId;

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
	

}
