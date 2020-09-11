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

import com.hiring.dao.CustomerDao;

@RestController
public class CustomerServiceContorller {
	@RequestMapping(value = "/customer",method=RequestMethod.GET)
	public ResponseEntity<String> customer(@RequestHeader HttpHeaders headers) {
		if(!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/custinsert",method = RequestMethod.POST)
	public ResponseEntity<String> addCustomer(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		CustomerDao cus = new CustomerDao();
		JSONObject json = new JSONObject(request);
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			JSONObject jsonObject = cus.insertCustomer(json);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
}