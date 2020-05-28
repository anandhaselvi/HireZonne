package com.hiring.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
@Controller
public class JobController {

	@RequestMapping(value = "/assignvendor", method = RequestMethod.GET)
	public String assignvendors() {
		return "assignvendor";
	}

	@RequestMapping(value = "/createjobpost", method = RequestMethod.GET)
	public String createjobpost() {
		return "createjobpost";
	}

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public String customer() {
		return "customer";
	}

	@RequestMapping(value = "/findjob", method = RequestMethod.GET)
	public String findjob() {
		return "findjob";
	}

	@RequestMapping(value = "/jobposting", method = RequestMethod.GET)
	public String jobposting() {
		return "jobposting";
	}

	@RequestMapping(value = "/reviewcandidate", method = RequestMethod.GET)
	public String reviewcandidate() {
		return "reviewcandidate";
	}

	@RequestMapping(value = "/vendor", method = RequestMethod.GET)
	public String vendor() {
		return "vendor";
	}
	@RequestMapping(value = "/candidate", method = RequestMethod.GET)
	public String candidate() {
		return "candidate";
	}
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginvendor(@RequestParam("id") String id) {
		return "login";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}

	@RequestMapping(value = "/insertjob.htm", method = RequestMethod.POST)
	@ResponseBody
	public String inJob(@RequestParam("title") String title, @RequestParam("location") String location,
			@RequestParam("jobtype") String jobtype, @RequestParam("experiencerequired") String experiencerequired,
			@RequestParam("keyskillsrequired")String keyskillsrequired,@RequestParam("jobdescription") String jobdescription,
			HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();								
		HttpHeaders header = new HttpHeaders();
		JSONObject jobJson = new JSONObject();
		jobJson.put("title", title);
		jobJson.put("location", location);
		jobJson.put("jobtype", jobtype);
		jobJson.put("experiencerequired",experiencerequired);
		jobJson.put("keyskillsrequired",keyskillsrequired);
		jobJson.put("jobdescription", jobdescription);
		
		HttpEntity<String> requestEntity = new HttpEntity<>(jobJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange
					(Utilities.readProperties() + "/jobinsert",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return json.toString();
	}
}
