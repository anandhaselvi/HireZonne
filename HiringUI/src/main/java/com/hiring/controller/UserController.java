package com.hiring.controller;

import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

@Controller
public class UserController {
	RestTemplate restTemplate = new RestTemplate();
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginvendor() {
		return "login";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView login(HttpServletRequest request) throws SQLException {
		ModelAndView mv = new ModelAndView();
		JSONObject userJson = new JSONObject();
		userJson.put("username", request.getParameter("username"));
		userJson.put("password", request.getParameter("password"));	
		HttpHeaders header = new HttpHeaders();
		HttpEntity<String> requestEntity = new HttpEntity<>(userJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/login", HttpMethod.POST,
					requestEntity, String.class);
			String userList = response.getBody();
			JSONObject json = new JSONObject(userList);
			HttpSession session=request.getSession(true);
			session.setAttribute("userId", json.optString("userId"));
			session.setAttribute("role", json.optString("role"));
			session.setAttribute("username",json.optString("username"));
			session.setAttribute("token", json.optString("token"));
			session.setMaxInactiveInterval(900);
			if (json.optString("msg").equalsIgnoreCase("Incorrect username and password")){
				mv.addObject("msg", json.optString("msg"));
				mv.setViewName("login");
			}
			else if (json.optString("role").equalsIgnoreCase("Vendor")) {
				mv.setViewName("redirect:/assignvendor");
			} else if (json.optString("role").equalsIgnoreCase("Customer")) {
				mv.setViewName("redirect:/assignvendor");
			}
			else if (json.optString("role").equalsIgnoreCase("Candidate")) {
				mv.setViewName("redirect:/findjob");
			}
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			//logger.error(ex.getResponseBodyAsString());
			if (ex.getStatusCode().value() == 401) {
				mv.setViewName("login");
			}else if(ex.getStatusCode().value() == 500) {
				mv.setViewName("login");
			}
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView addUser(HttpServletRequest request) {
		HttpHeaders header = new HttpHeaders();
		ModelAndView mv = new ModelAndView();
		JSONObject json = new JSONObject();
		RestTemplate restTemplate = new RestTemplate();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		JSONObject userJson = new JSONObject();
		userJson.put("username", request.getParameter("username"));
		System.out.println(request.getParameter("customerId"));
		if(!request.getParameter("customerId").isEmpty()) {
			userJson.put("customerId", request.getParameter("customerId"));
			userJson.put("role", "vendor");
			System.out.println("vendorId"+request.getParameter("vendorId"));
			userJson.put("reportingto", !request.getParameter("vendorId").isEmpty() ? request.getParameter("vendorId"):"0");
			userJson.put("isprimary", !request.getParameter("vendorId").isEmpty() ? "0":"1");
		}else {
			userJson.put("role", "customer");
		}
		userJson.put("password", request.getParameter("password"));
		userJson.put("firstname", request.getParameter("firstname"));
		userJson.put("lastname", request.getParameter("lastname"));
		userJson.put("createdby", request.getParameter("customerId"));
		System.out.println("user"+userJson);
		HttpEntity<String> requestEntity = new HttpEntity<>(userJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/register", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			if(json.optString("msg").contains("successfully")) {
				mv.setViewName("redirect:/login");
			}
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
				
		}
		return mv;
	}
	
	@RequestMapping(value="/candidatelogin.htm", method=RequestMethod.POST)
	@ResponseBody
	public String status(@RequestParam("username") String username,@RequestParam("password") String password,HttpServletRequest request) {
	RestTemplate restTemplate = new RestTemplate();
	JSONObject userJson = new JSONObject();
	JSONObject json = new JSONObject();
	userJson.put("username", username);
	userJson.put("password", password);	
	HttpHeaders header = new HttpHeaders();
	HttpEntity<String> requestEntity = new HttpEntity<>(userJson.toString(), header);
	try {
		ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/login", HttpMethod.POST,
				requestEntity, String.class);
		String userList = response.getBody();
		json = new JSONObject(userList);
		HttpSession session=request.getSession(true);
		session.setAttribute("userId", json.optString("userId"));
		session.setAttribute("role", json.optString("role"));
		session.setAttribute("username",json.optString("username"));
		session.setAttribute("token", json.optString("token"));
		session.setMaxInactiveInterval(900);
	}catch(Exception e) {
		e.printStackTrace();
	}
	return json.toString();
}
}
