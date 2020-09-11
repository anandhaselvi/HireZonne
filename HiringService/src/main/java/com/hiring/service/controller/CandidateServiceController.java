package com.hiring.service.controller;

import org.json.JSONArray;
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

import com.hiring.dao.CandidateDao;
import com.hiring.dao.UserDaoImpl;


@RestController
public class CandidateServiceController {

	@RequestMapping(value = "/candidate", method=RequestMethod.GET)
	public ResponseEntity<String> getcandidate(@RequestHeader HttpHeaders headers) {
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/candidatelisting", method=RequestMethod.GET)
	public ResponseEntity<String> jobposting(@RequestHeader HttpHeaders headers) {
		CandidateDao canDao = new CandidateDao();
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			JSONObject jsonObject = canDao.candidateList();
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	

	@RequestMapping(value = "/addcandidate", method=RequestMethod.POST)
	public ResponseEntity<String> candidatein(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		CandidateDao can = new CandidateDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		JSONArray jsonArr =json.getJSONArray("candidateList");
		for(int i=0;i<jsonArr.length();i++) {
			JSONObject candidateJson = jsonArr.getJSONObject(i);
			JSONObject userJson= user.insertUser(candidateJson);
			candidateJson.put("userId",userJson.optString("userId"));
			jsonObject = can.insertApplicant(candidateJson);
		}
		if (jsonObject.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/candidatebulkupload",method=RequestMethod.POST)
	public ResponseEntity<String> candidatebulkupload(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		CandidateDao candidatedao = new CandidateDao();
		UserDaoImpl userdao = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject(request);
		System.out.println("json"+jsonObject);
		JSONObject jsonObj = new JSONObject();
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			JSONArray jsonArr =jsonObject.getJSONArray("candidateList");
			for(int i=0;i<jsonArr.length();i++) {
				JSONObject candidateJson = jsonArr.getJSONObject(i);
				JSONObject userJson= userdao.insertUser(candidateJson);
				candidateJson.put("userId",userJson.optString("userId"));
				jsonObj = candidatedao.insertApplicant(candidateJson);
			}
			if (jsonObj.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
		}
		return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.OK);
	}

	
	@RequestMapping(value = "/reviewcandidate/{userId}",method=RequestMethod.GET)
	public ResponseEntity<String> candidatemapList(@RequestHeader HttpHeaders headers,@PathVariable String userId) {
		CandidateDao can = new CandidateDao();
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			JSONObject jsonObject = can.candidateMap(userId);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/candidateProfileSubmit",method=RequestMethod.POST)
	public ResponseEntity<String> candidateProfileSubmit(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		CandidateDao candidatedao = new CandidateDao();
		JSONObject json = new JSONObject(request);
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			JSONObject jsonObject = candidatedao.candidateProfileSubmit(json);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/profilecheck",method=RequestMethod.POST)
	public ResponseEntity<String> getProfileList(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		CandidateDao canDao = new CandidateDao();
		JSONObject json = new JSONObject(request);
		if(!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			JSONObject jsonObject = canDao.profileList(json.getString("profile"));
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
}
