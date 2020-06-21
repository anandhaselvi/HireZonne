package com.hiring.dao;

import org.json.JSONObject;

public interface Vendor {

	
	public JSONObject insertvendor(JSONObject json);
	public JSONObject vendorupdate(JSONObject jsonObject);
	public JSONObject vendorList(String userId,String role);
	public JSONObject fetchvendor(String id);
	int nameExists(String vendorname);
}
