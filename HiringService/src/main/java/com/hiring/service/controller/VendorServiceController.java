package com.hiring.service.controller;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hiring.dao.Authorization;
import com.hiring.dao.SendMail;
import com.hiring.dao.VendorDao;

@RestController
public class VendorServiceController {

	@RequestMapping(value = "/vendor", method = RequestMethod.GET)

	public ResponseEntity<String> vendor(@RequestHeader HttpHeaders headers) {
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if (!Authorization.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/vendorinsert", method = RequestMethod.POST)
	public ResponseEntity<String> insertvendor(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		VendorDao vendor = new VendorDao();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if (!Authorization.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = vendor.insertvendor(json);
			System.out.println("counter" + jsonObject);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/vendorupdate", method = RequestMethod.POST)
	public ResponseEntity<String> vendorupdate(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		VendorDao vendor = new VendorDao();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if (!Authorization.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = vendor.vendorupdate(json);
			System.out.println("counter" + jsonObject);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/getcustomer", method = RequestMethod.POST)
	public ResponseEntity<String> getcustomer(@RequestHeader HttpHeaders headers) {
		VendorDao ven = new VendorDao();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if (!Authorization.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = ven.getCustomer();
			System.out.println("counter" + jsonObject);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getreporting", method = RequestMethod.POST)
	public ResponseEntity<String> getrep(@RequestHeader HttpHeaders headers) {
		VendorDao ven = new VendorDao();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if (!Authorization.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = ven.getReporting();
			System.out.println("counter" + jsonObject);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/assignvendor/{userId}/{role}", method = RequestMethod.GET)
	public ResponseEntity<String> listtime(@RequestHeader HttpHeaders headers,@PathVariable String userId,@PathVariable String role) {
		VendorDao ven = new VendorDao();
		JSONObject json = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if (!Authorization.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			json = ven.vendorList(userId,role);
			System.out.println("counter" + json);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/sendmail", method = RequestMethod.POST)
	public ResponseEntity<String> mail(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		SendMail mail = new SendMail();
		VendorDao vendordao = new VendorDao();
		JSONObject json = new JSONObject();
		JSONObject jsonObj = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if (!Authorization.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			if (jsonObj.optString("role").equalsIgnoreCase("vendor")) {
				JSONObject venJson = vendordao.getCustomerId(jsonObj.optString("userId"));
				System.out.println("vendorJson"+venJson);
				jsonObj.put("customerId", venJson.optInt("customer"));
		}
		json = mail.sendmail(jsonObj);
		if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}
	

}

