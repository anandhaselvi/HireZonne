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

import com.hiring.dao.SendMail;
import com.hiring.dao.UserDaoImpl;
import com.hiring.dao.VendorDao;

@RestController
public class VendorServiceController {

	@RequestMapping(value = "/vendor",method=RequestMethod.GET)
	public ResponseEntity<String> vendor(@RequestHeader HttpHeaders headers) {
		JSONObject jsonObject = new JSONObject();
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/vendorinsert",method=RequestMethod.POST)
	public ResponseEntity<String> insertvendor(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		VendorDao vendor = new VendorDao();
		UserDaoImpl userDao = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		System.out.println("request"+json);
		jsonObject = userDao.insertUser(json);
		json.put("userId", jsonObject.optString("userId"));
     	jsonObject = vendor.insertVendor(json);
		if (jsonObject.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/vendorupdate",method=RequestMethod.POST)
	public ResponseEntity<String> vendorupdate(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		JSONObject json = new JSONObject(request);
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			VendorDao vendor = new VendorDao();
			JSONObject jsonObject = vendor.vendorupdate(json);
			System.out.println("counter" + jsonObject);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/getcustomer", method=RequestMethod.GET)
	public ResponseEntity<String> getcustomer(@RequestHeader HttpHeaders headers) {
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			VendorDao ven = new VendorDao();
			JSONObject jsonObject = ven.getCustomer();
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getreporting",method=RequestMethod.GET)
	public ResponseEntity<String> getrep(@RequestHeader HttpHeaders headers) {
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			VendorDao ven = new VendorDao();
			JSONObject jsonObject = ven.getReporting();
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/assignvendor/{userId}/{role}",method=RequestMethod.GET)
	public ResponseEntity<String> listtime(@RequestHeader HttpHeaders headers,@PathVariable String userId,@PathVariable String role) {
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			VendorDao ven = new VendorDao();
			 JSONObject json = ven.vendorList(userId,role);
			if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/sendmail",method=RequestMethod.POST)
	public ResponseEntity<String> mail(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		SendMail mail = new SendMail();
		JSONObject jsonObj = new JSONObject(request);
		System.out.println(jsonObj);
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			VendorDao vendordao = new VendorDao();
			if (jsonObj.optString("role").equalsIgnoreCase("vendor")) {
				JSONObject venJson = vendordao.getCustomerId(jsonObj.optString("userId"));
				System.out.println("vendor"+venJson);
				jsonObj.put("customerId", venJson.optInt("customer"));
			}
		JSONObject json = mail.sendmail(jsonObj);
		if (json.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}
	
    @RequestMapping(value = "/send",method=RequestMethod.POST)
    public ResponseEntity<String> mailsend(@RequestHeader HttpHeaders headers, @RequestBody String request) {
        SendMail mail = new SendMail();
        VendorDao vendordao = new VendorDao();
        JSONObject json = new JSONObject();
        JSONObject jsonObj = new JSONObject(request);
        if (!Authorization.authorizeToken(headers)) {
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        } else {
                JSONObject vJson = vendordao.getVendor(jsonObj.optInt("vendorId"));
                jsonObj.put("email",vJson.getString( "email"));
                json= mail.sendmail(jsonObj);
                jsonObj.put("status",vJson.getString("submitType"));
                json = vendordao.vendorupdate(jsonObj);
        }
              if (json.isEmpty()) {
                return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
        }
}

