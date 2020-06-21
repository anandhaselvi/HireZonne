package com.hiring.dao;

import org.json.JSONObject;

public interface Candidate {

	public JSONObject insertapplicant(JSONObject json);
	
	public JSONObject updatecandidate(JSONObject jsonObject);
	public JSONObject NameById(String candidateid);
	
	public JSONObject candidatelist();

	JSONObject candidatemap(String userId);
}
