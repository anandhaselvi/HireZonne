package com.hiring.model;

public class JobModel {
	private String jobpostingId;
	private String title;
	private String location;
	private String jobtype;
	private String  experiencerequired;
	private String keyskillsrequired;
	private String jobbdescription;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getJobtype() {
		return jobtype;
	}
	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}
	public String getExperiencerequired() {
		return experiencerequired;
	}
	public void setExperiencerequired(String experiencerequired) {
		this.experiencerequired = experiencerequired;
	}
	public String getKeyskillsrequired() {
		return keyskillsrequired;
	}
	public void setKeyskillsrequired(String keyskillsrequired) {
		this.keyskillsrequired = keyskillsrequired;
	}
	public String getJobbdescription() {
		return jobbdescription;
	}
	public void setJobbdescription(String jobbdescription) {
		this.jobbdescription = jobbdescription;
	}
	public String getJobpostingId() {
		return jobpostingId;
	}
	public void setJobpostingId(String jobpostingId) {
		this.jobpostingId = jobpostingId;
	}

}
