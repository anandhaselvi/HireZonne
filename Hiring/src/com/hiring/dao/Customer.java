package com.hiring.dao;

import org.json.JSONObject;

public interface Customer {

	public JSONObject insertCustomer(JSONObject json) ;
	public JSONObject customerList();
	public JSONObject updatecust(JSONObject json);
	public JSONObject custById(String customerid);

}
