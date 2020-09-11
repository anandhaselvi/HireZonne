package com.hiring.test;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.Test;

import com.hiring.dao.UserDaoImpl;

public class UserTest {

	@Test
	public void test() throws IOException {
	UserDaoImpl user=new UserDaoImpl();
	JSONObject users =new JSONObject();
	users.put("firstname","selvnvi");
	users.put("lastname","nataraaajan");
	users.put("username","selvi2454");
	users.put("password","selvi123");
	users.put("oldpassword","selvi123");
	users.put("role","vendor");
	users.put("updatedby","1");
	users.put("userid", "76");
	System.out.println(user.insertUser(users));
	System.out.println(user.userList());
	JSONObject json = new JSONObject();
	json.put("username", "anandhaselvi0@gmail.com");
	user.checkRole(json);
	user.validateToken("anandhaselvi0@gmail.com");
	user.passwordchange(users);
	user.siteList();
	}
} 
