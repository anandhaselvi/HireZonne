package com.hiring.dao;

import org.json.JSONObject;

public interface Vendor {

	
	public JSONObject vendorupdate(JSONObject jsonObject);
	public JSONObject vendorList(String userId,String role);
	public JSONObject fetchvendor(String id);
	JSONObject insertVendor(JSONObject json);
}
