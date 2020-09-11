package com.hiring.test;

import org.json.JSONObject;
import org.junit.Test;

import com.hiring.dao.UserDaoImpl;

public class validateToken {

	@Test
	public void test() {
		UserDaoImpl dao = new UserDaoImpl();
		JSONObject json = new JSONObject();
		json.put("username", "anandhaselvi0@gmail.com");
		json.put("password", "123456");
		System.out.println(dao.login(json));
		dao.validateToken("supervisor");
		System.out.println(dao.checkRole(json));
	}

}
