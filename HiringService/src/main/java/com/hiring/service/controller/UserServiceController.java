package com.hiring.service.controller;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hiring.dao.UserDaoImpl;

@RestController
public class UserServiceController {
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody String request) {
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		int counter = userDao.login(json);
		if (counter == 1) {
			userDao.validateToken(json.getString("username"));
			jsonObject = userDao.CheckRole(json);
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
		else if(counter == 2) {
			jsonObject.put("msg","Incorrect username and password");
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
			}
		else {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> addUser(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject json = new JSONObject();
		JSONObject jsonObject = new JSONObject(request);
		json = userDao.insertUser(jsonObject);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		
	}
}
