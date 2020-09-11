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

import com.hiring.dao.JobDao;

@RestController
public class JobServiceController {
	@RequestMapping(value="/findjob",method=RequestMethod.GET)
	public ResponseEntity<String> findjob(@RequestHeader HttpHeaders headers) {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
	
	
	@RequestMapping(value="/jobposting",method=RequestMethod.GET)
	public ResponseEntity<String> jobposting(@RequestHeader HttpHeaders headers) {
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			JobDao jobDao = new JobDao();
			JSONObject jsonObject = jobDao.jobList();
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/jobList",method=RequestMethod.POST)
	public ResponseEntity<String> job(@RequestHeader HttpHeaders headers) {
		if (!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			JobDao job = new JobDao();
			JSONObject jsonObject = job.jobList();
		if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/createjobpost/{userId}",method=RequestMethod.GET)
	public ResponseEntity<String> addJob(@RequestHeader HttpHeaders headers, @PathVariable String userId) {
		if(!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			JobDao dao = new JobDao();
			JSONObject json = dao.vendorCategory(userId);
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value="/insertjob", method=RequestMethod.POST)
	public ResponseEntity<String> insertjob(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		JSONObject json = new JSONObject(request);
		if(!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			JobDao job = new JobDao();
			JSONObject jsonObject = job.jobInsert(json);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
			}
	}
	

	@RequestMapping(value="/getstatus", method=RequestMethod.POST)
	public ResponseEntity<String> getstatus(@RequestHeader HttpHeaders headers,@RequestBody String request){
		JSONObject json = new JSONObject(request);
		if(!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			JobDao job = new JobDao();
			JSONObject jsonObject= job.candidateMapping(json);	
		if (jsonObject.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	


	@RequestMapping(value="/searchjobList/{userId}",method=RequestMethod.POST)
	public ResponseEntity<String> searchjobList(@RequestHeader HttpHeaders headers,@PathVariable String userId) {
		if(!Authorization.authorizeToken(headers)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}else {
		    JobDao job = new JobDao();
			JSONObject jsonObject = job.jobRefNoListing(userId);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
			
	}
	
	 @RequestMapping(value = "/jobbulkupload",method = RequestMethod.POST)
	    public ResponseEntity<String> jobbulkupload(@RequestHeader HttpHeaders headers,@RequestBody String request) {
	        JSONObject jsonObject = new JSONObject(request);
	        if (!Authorization.authorizeToken(headers)) {
	            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	        } else {
			 	JobDao jobdao = new JobDao();
			 	JSONObject json= jobdao.jobInsert(jsonObject);
	            if (json.isEmpty()) {
	            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	            }
	        }
	        return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
	    }
}
	
