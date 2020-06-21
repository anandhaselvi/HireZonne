package com.hiring.controller;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {
	
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public String customer(HttpServletRequest request) {
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
		restTemplate.exchange(Utilities.readProperties() + "/customer", HttpMethod.GET,
					requestEntity, String.class);
			return "customer";
		}
		catch (HttpClientErrorException | HttpServerErrorException ex) {
			 	//logger.error(ex.getResponseBodyAsString());
			if (ex.getStatusCode().value() == 401) {
				return "redirect:/login";
			}
		}
		return "";
	}
	
	
	@RequestMapping(value = "/custinsert.htm", method = RequestMethod.POST)
	@ResponseBody
	public String customerin(@RequestParam("companyname") String companyname, @RequestParam("description") String description,
		HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();								
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString(): "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject customerJson = new JSONObject();
		customerJson.put("companyname", companyname);
		customerJson.put("description", description);
		
		HttpEntity<String> requestEntity = new HttpEntity<>(customerJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/custinsert",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			System.out.println("counter"+json);
		}  catch (Exception ex) {
			ex.printStackTrace();
			
		}
		return json.toString();
	}
}


