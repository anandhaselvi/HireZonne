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

import com.hiring.dao.CandidateDao;
import com.hiring.dao.UserDaoImpl;

@RestController
public class CandidateServiceController {

	@RequestMapping(value = "/candidate", method = RequestMethod.GET)
	public ResponseEntity<String> getcandidate(@RequestHeader HttpHeaders headers) {
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if (!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	

	@RequestMapping(value = "/addcandidate", method = RequestMethod.POST)
	public ResponseEntity<String> candidatein(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		CandidateDao can = new CandidateDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		System.out.println("candidateJson"+json);
		JSONObject userJson= user.insertUser(json);
		json.put("userId",userJson.optString("userId"));
		jsonObject = can.insertapplicant(json);
		System.out.println("counter" + jsonObject);
		if (jsonObject.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
	}

	
	@RequestMapping(value = "/reviewcandidate/{userId}", method = RequestMethod.GET)
	public ResponseEntity<String> candidatemapList(@RequestHeader HttpHeaders headers,@PathVariable String userId) {
		CandidateDao can = new CandidateDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if (!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = can.candidatemap(userId);
			System.out.println("counter" + jsonObject);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/candidateProfileSubmit", method = RequestMethod.POST)
	public ResponseEntity<String> candidateProfileSubmit(@RequestHeader HttpHeaders headers, @RequestBody String request) {
		CandidateDao candidatedao = new CandidateDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if (!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = candidatedao.candidateProfileSubmit(json);
			System.out.println("counter" + jsonObject);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
}
