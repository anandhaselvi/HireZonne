package com.hiring.dao;

import org.json.JSONObject;

public interface Job {
	
	
	public JSONObject jobInsert(JSONObject json);
	
	public JSONObject jobUpdate(JSONObject jsonObject);
	
	public JSONObject fetchJob(String jobpostingId);
	
	public JSONObject jobList();

	JSONObject candidatemapping(JSONObject jsonobj);
	
	

}
