package com.hiring.model;

public class CandidateModel {
	private String candidateid;
	private String name;
	private String emailid;
	private String dob;
	private String profile;
	private String vendorname;
	private String submittedto;
	private String jobPostingId;
	private String resume;
	private String submittedType;
	private String candidateJobPostId;
	private String createdon;
		
	public String getSubmittedType() {
		return submittedType;
	}
	public void setSubmittedType(String submittedType) {
		this.submittedType = submittedType;
	}
	public String getJobPostingId() {
		return jobPostingId;
	}
	public void setJobPostingId(String jobPostingId) {
		this.jobPostingId = jobPostingId;
	}
	public String getSubmittedto() {
		return submittedto;
	}
	public void setSubmittedto(String submittedto) {
		this.submittedto = submittedto;
	}
	public String getCandidateid() {
		return candidateid;
	}
	public void setCandidateid(String candidateid) {
		this.candidateid = candidateid;
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
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public void setCandidateJobPostId(String candidateJobPostId) {
		this.candidateJobPostId = candidateJobPostId;
	}
	public String getCandidateJobPostId() {
		return candidateJobPostId;
	}
	public String getCreatedon() {
		return createdon;
	}
	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}
}
