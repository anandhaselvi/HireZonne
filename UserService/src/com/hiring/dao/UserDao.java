package com.hiring.dao;

import org.json.JSONObject;


public interface UserDao {

	public int isexistuser(JSONObject json);
	
	public JSONObject insertUser(JSONObject json);
	
	public JSONObject userList();
	
	public int login(JSONObject json);
	
	public JSONObject CheckRole(JSONObject jsonObj);
	
	public Boolean authorizeToken(String username, String token);
	
	public void validateToken(String username);
	
	public JSONObject passwordchange(JSONObject json);

}