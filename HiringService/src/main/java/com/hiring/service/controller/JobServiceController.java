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
import com.hiring.dao.UserDaoImpl;
@RestController
public class JobServiceController {
	@RequestMapping(value="/findjob", method=RequestMethod.GET)
	public ResponseEntity<String> findjob(@RequestHeader HttpHeaders headers) {
		//UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		/*String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {*/
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	
	
	@RequestMapping(value="/jobposting", method=RequestMethod.GET)
	public ResponseEntity<String> jobposting(@RequestHeader HttpHeaders headers) {
		UserDaoImpl user = new UserDaoImpl();
		JobDao jobDao = new JobDao();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if (!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = jobDao.jobList();
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/jobList",method=RequestMethod.POST)
	public ResponseEntity<String> job(@RequestHeader HttpHeaders headers) {
		JobDao job = new JobDao();
		JSONObject jsonObject = new JSONObject();
		jsonObject = job.jobList();
		System.out.println("counter" + jsonObject);
		if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/createjobpost", method=RequestMethod.GET)
	public ResponseEntity<String> addJob(@RequestHeader HttpHeaders headers) {
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	@RequestMapping(value="/insertjob",method=RequestMethod.POST)
	public ResponseEntity<String> addJob(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		JobDao job = new JobDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else {
			jsonObject = job.jobInsert(json);
			System.out.println("counter" + jsonObject);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
			}
	}
	

	@RequestMapping(value="/getstatus",method=RequestMethod.POST)
	public ResponseEntity<String> getstatus(@RequestHeader HttpHeaders headers,@RequestBody String request){
		JobDao job = new JobDao();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		jsonObject= job.candidatemapping(json);
		if (jsonObject.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
	


	@RequestMapping(value="/searchjobList/{userId}",method=RequestMethod.POST)
	public ResponseEntity<String> searchjobList(@RequestHeader HttpHeaders headers,@PathVariable String userId) {
	    JobDao job = new JobDao();
		JSONObject jsonObject = new JSONObject();
		UserDaoImpl user = new UserDaoImpl();
		String token = headers.getFirst("Authorization");
		String username = headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}else {
			jsonObject = job.jobRefNoListing(userId);
			System.out.println("counter" + jsonObject);
			if (jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
		}
			
	}


}
		/*@RequestMapping(value="/fetchjob",method=RequestMethod.POST)
		public ResponseEntity<String> fetchJob(@RequestHeader HttpHeaders headers,@RequestBody String request) {
			JobDao job= new JobDao();
			UserDaoImpl user = new UserDaoImpl();
			JSONObject jsonObject = new JSONObject();
			JSONObject json = new JSONObject(request);
			String token = headers.getFirst("Authorization");
			String username= headers.getFirst("username");
			if(!user.authorizeToken(username, token)) {
				return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
				} else {
					String jobpostingId =json.getString("jobpostingId");
					jsonObject=job.fetchJob(jobpostingId);
					if(jsonObject.isEmpty()) {
						return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
					}
					return new ResponseEntity<String>(jsonObject.toString(),HttpStatus.OK);
				}
			}

	@RequestMapping(value="/jobupdate",method =RequestMethod.POST)
	public ResponseEntity<String> updateJob(@RequestHeader HttpHeaders headers,@RequestBody String request) {
		JobDao jobdao= new JobDao();
		UserDaoImpl user = new UserDaoImpl();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject(request);
		String token = headers.getFirst("Authorization");
		String username= headers.getFirst("username");
		if(!user.authorizeToken(username, token)) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
			} else {
			jsonObject=jobdao.jobUpdate(json);
			if(jsonObject.isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(jsonObject.toString(),HttpStatus.OK);
		}
}*/








