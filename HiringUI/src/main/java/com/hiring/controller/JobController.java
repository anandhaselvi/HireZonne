package com.hiring.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.hiring.model.JobModel;

@Controller
public class JobController {

	
	@RequestMapping(value = "/createjobpost", method = RequestMethod.GET)
	public String createjobpost(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString(): "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			restTemplate.exchange(Utilities.readProperties() + "/createjobpost", HttpMethod.GET,
						requestEntity, String.class);
				return "createjobpost";
		}
		catch (HttpClientErrorException | HttpServerErrorException ex) {
			 	//logger.error(ex.getResponseBodyAsString());
				if (ex.getStatusCode().value() == 401) {
					return "redirect:/login";
				}
			}
		return "";
	}

	//@RequestMapping(value = "/customer", method = RequestMethod.GET)
	//public String customer() {
		//return "customer";
	//}

	@RequestMapping(value = "/findjob", method = RequestMethod.GET)
	public String findjob() {
		return "findjob";
	}

	@RequestMapping(value = "/jobposting", method = RequestMethod.GET)
	public ModelAndView jobposting(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString(): "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
				ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/jobposting",
						HttpMethod.GET, requestEntity, String.class);
				String jobList = response.getBody();
				JSONObject json = new JSONObject(jobList);
				JSONArray jsonArr = json.getJSONArray("jobList");
				List<JobModel> job = new ArrayList<JobModel>();
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj = jsonArr.getJSONObject(i);
					JobModel  jobModel = new JobModel();
					jobModel.setTitle(jsonObj.getString("title"));
					jobModel.setLocation(jsonObj.getString("location"));
					jobModel.setExperiencerequired(jsonObj.getString("experiencerequired"));
					jobModel.setJobbdescription(jsonObj.getString("jobdescription"));
					jobModel.setKeyskillsrequired(jsonObj.getString("keyskillsrequired"));
					jobModel.setJobtype(jsonObj.getString("jobtype"));
					job.add(jobModel);
				}
				return new ModelAndView("jobposting", "jobListing", job);
			} catch (HttpClientErrorException | HttpServerErrorException ex) {
			 	//logger.error(ex.getResponseBodyAsString());
				if (ex.getStatusCode().value() == 401) {
					return  new ModelAndView("login");
				}
			}
			return new ModelAndView();
		}

//	@RequestMapping(value = "/vendor", method = RequestMethod.GET)
//	public String vendor() {
//		return "vendor";
//	}
	
	@RequestMapping(value = "/candidate", method = RequestMethod.GET)
	public String candidate() {
		return "candidate";
	}
	


	@RequestMapping(value="/jobList.htm", method=RequestMethod.POST)
	@ResponseBody
	public String getjobList(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();								
		HttpHeaders header = new HttpHeaders();
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/jobList",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		} catch (Exception ex) {
				ex.printStackTrace();
		}
		return json.toString();
    }

	@RequestMapping(value = "/insertjob.htm", method = RequestMethod.POST)
	@ResponseBody
	public String inJob(@RequestParam("title") String title, @RequestParam("location") String location,
			@RequestParam("jobtype") String jobtype,@RequestParam("experiencerequired") String experiencerequired,
			@RequestParam("workauthorization")String workauthorization,@RequestParam("jobdescription") String jobdescription,
			@RequestParam("jobId") String jobId,
			HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();								
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString(): "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		String role = request.getSession().getAttribute("role") != null
						? request.getSession().getAttribute("role").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject jobJson = new JSONObject();
		jobJson.put("title", title);
		jobJson.put("location", location);
		jobJson.put("jobtype", jobtype);
		jobJson.put("experiencerequired",experiencerequired);
		jobJson.put("workauthorization", workauthorization);
		jobJson.put("jobdescription", jobdescription);
		jobJson.put("updatedby", request.getSession().getAttribute("userId").toString()); 
		jobJson.put("role", role);
		jobJson.put("jobId", jobId);
		System.out.println(jobJson);
		HttpEntity<String> requestEntity = new HttpEntity<>(jobJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/insertjob",
			HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			System.out.println("counter" + json);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return json.toString();
	}
	
	@RequestMapping(value="/getstatus.htm", method=RequestMethod.POST)
	@ResponseBody
	public String status(@RequestParam("vendorId") String vendorId,@RequestParam("jobpostingId") String jobpostingId,
			@RequestParam("submitType") String submitType,@RequestParam("customerId") String customerId,HttpServletRequest request) {
	RestTemplate restTemplate = new RestTemplate();
	JSONObject json = new JSONObject();
	HttpHeaders header = new HttpHeaders();
	String token = request.getSession().getAttribute("token") != null
			? request.getSession().getAttribute("token").toString(): "";
	String username = request.getSession().getAttribute("username") != null
			? request.getSession().getAttribute("username").toString(): "";
	String userId = request.getSession().getAttribute("userId") != null
					? request.getSession().getAttribute("userId").toString(): "";
	header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	header.set("Authorization", token);
	header.set("username",username);
	JSONObject jsonobj = new JSONObject();
	jsonobj.put("jobpostingId", jobpostingId);
	jsonobj.put("candidateId", userId);
	if(submitType.equalsIgnoreCase("customer")) {
	jsonobj.put("submittedto", customerId);
	}else {
		jsonobj.put("submittedto", vendorId);
	}
	jsonobj.put("vendorId", vendorId);
	jsonobj.put("customerId", customerId);
	jsonobj.put("submittedType", submitType);
	jsonobj.put("createdby", userId);
	System.out.println("vendorsubmission"+jsonobj);
	HttpEntity<String> requestEntity = new HttpEntity<>(jsonobj.toString(), header);
	try {
		ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getstatus",
		HttpMethod.POST, requestEntity, String.class);
		String jsonResonse = response.getBody();
		json = new JSONObject(jsonResonse);
		System.out.println("counter" + json);
	} catch (Exception ex) {
		ex.printStackTrace();
	}
	return json.toString();
}
	@RequestMapping(value="/searchjobList.htm", method=RequestMethod.POST)
	@ResponseBody
	public String searchjobList(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();								
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString(): "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		String userId = request.getSession().getAttribute("userId") != null
						? request.getSession().getAttribute("userId").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/searchjobList/"+userId+"",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		} catch (Exception ex) {
				ex.printStackTrace();
		}
		return json.toString();
    }

}