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

import com.hiring.dao.Authorization;
import com.hiring.dao.CustomerDao;
@RestController
public class CustomerServiceContorller {
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public ResponseEntity<String> Addcustomer(@RequestHeader HttpHeaders headers) {
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!Authorization.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/custinsert", method = RequestMethod.POST)
	public ResponseEntity<String> Addcustomer(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		CustomerDao cus = new CustomerDao();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		JSONObject json = new JSONObject(request);
		if (!Authorization.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = cus.insertCustomer(json);
			System.out.println("counter" + jsonObject);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
}

	/*@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<String> updateCustomer(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject json = new JSONObject();
		JSONObject jsonObject = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!userDao.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = userDao.updateUser(jsonObject);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/getCustomer", method = RequestMethod.POST)
	public ResponseEntity<String> getCustomer(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		
		UserDaoImpl userdao = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!userdao.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			String customerid = json.getString("customerid");
			jsonObject = userdao.custById(customerid);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	*/




