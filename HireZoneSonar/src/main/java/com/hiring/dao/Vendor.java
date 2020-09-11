package com.hiring.dao;

import org.json.JSONObject;

public interface Vendor {

	
	public JSONObject vendorUpdate(JSONObject jsonObject);
	public JSONObject vendorList(String userId,String role);
	public JSONObject fetchVendor(String id);
	JSONObject insertVendor(JSONObject json);
}
