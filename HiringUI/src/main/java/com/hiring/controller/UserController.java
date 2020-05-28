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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView login(HttpServletRequest request) throws SQLException {
		ModelAndView mv = new ModelAndView();
		JSONObject userJson = new JSONObject();
		userJson.put("username", request.getParameter("username"));
		userJson.put("password", request.getParameter("password"));
		System.out.println(request.getParameter("username"));
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
			if (json.has("msg")) {
				mv.addObject("msg", json.optString("msg"));
				mv.setViewName("login");
			}
			else if (json.optString("role").equalsIgnoreCase("Vendor")) {
				mv.setViewName("redirect:/vendor");
			} else if (json.optString("role").equalsIgnoreCase("Customer")) {
				mv.setViewName("redirect:/vendor");
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
		System.out.println(request.getParameter("username"));
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		JSONObject userJson = new JSONObject();
		userJson.put("username", request.getParameter("username"));
		userJson.put("role", request.getParameter("role"));
		userJson.put("password", request.getParameter("password"));
		userJson.put("firstname",  request.getParameter("firstname"));
		userJson.put("lastname",  request.getParameter("lastname"));
		HttpEntity<String> requestEntity = new HttpEntity<>(userJson.toString(), header);
		try {
			System.out.println(Utilities.readProperties());
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/register", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			if(json.optString("msg").equalsIgnoreCase("user Created successfully")) {
				mv.setViewName("redirect:/login?id="+request.getParameter("role")+"");
			}
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			if (ex.getStatusCode().value() == 401) {
				mv.setViewName("register");
			}else if(ex.getStatusCode().value() == 500) {
				mv.setViewName("register");
			}		
		}
		return mv;
	}

}
