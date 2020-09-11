package com.hiring.controller;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {
	
	private static Logger logger= Logger.getLogger(CustomerController.class);

	
	@RequestMapping(value = "/customer",method=RequestMethod.GET)
	public String customer(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = Utilities.userAuthentication(request);
		HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
		try {
		restTemplate.exchange(Utilities.readProperties() + "/customer", HttpMethod.GET,
					requestEntity, String.class);
			return "customer";
		}
		catch (HttpServerErrorException ex) {
			if (ex.getStatusCode().value() == 401) {
				return "redirect:/login";
			}
		}
		return "";
	}
	
	
	@RequestMapping(value = "/custinsert.htm",method=RequestMethod.POST)
	@ResponseBody
	public String customerin(@RequestParam("companyname") String companyname, @RequestParam("description") String description,
		HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();								
		HttpHeaders header = Utilities.userAuthentication(request);
		String userId = request.getSession().getAttribute("userId") != null
						? request.getSession().getAttribute("userId").toString(): "";

		JSONObject customerJson = new JSONObject();
		customerJson.put("companyname", companyname);
		customerJson.put("description", description);
		customerJson.put("createdby", userId);

		HttpEntity<String> requestEntity = new HttpEntity<String>(customerJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/custinsert",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		}  catch (Exception ex) {
			logger.error(ex);
			
		}
		return json.toString();
	}
}


