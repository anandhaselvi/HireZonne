package com.hiring.controller;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.hiring.model.VendorModel;

@Controller
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);
	RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginvendor() {
		return "login";
	}
	
	@RequestMapping(value = "/register",method=RequestMethod.GET)
	public String register() {
		return "register";
	}
	
		@RequestMapping(value = "/adminApproval",method=RequestMethod.GET)
		public ModelAndView siteList(HttpServletRequest request) {
			HttpHeaders header = Utilities.userAuthentication(request);
			HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
			try {
				ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/adminApproval", HttpMethod.GET,
						requestEntity, String.class);
				String siteList = response.getBody();
				JSONObject json = new JSONObject(siteList);
				JSONArray jsonArr = json.getJSONArray("siteList");
				List<VendorModel> site = new ArrayList<VendorModel>();
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj = jsonArr.getJSONObject(i);
					VendorModel venModel = new VendorModel();
					venModel.setVendorId(jsonObj.getInt("vendorId"));
					venModel.setVendorname(jsonObj.optString("vendorname"));
					venModel.setLocation(jsonObj.optString("location"));
					venModel.setEmailId(jsonObj.optString("EmailId"));
				    venModel.setPhonenumber(jsonObj.optString("Phonenumber"));
					venModel.setCompanydetails(jsonObj.optString("companydetails"));
					venModel.setStatus(jsonObj.optString("status"));
					site.add(venModel);
				}
				return new ModelAndView("adminApproval", "ven", site);
			} catch(Exception e){
				logger.error(e);
			}
			return new ModelAndView();
		}
	
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView login(HttpServletRequest request) throws SQLException {
		ModelAndView mv = new ModelAndView();
		JSONObject userJson = new JSONObject();
		userJson.put("username", request.getParameter("username"));
		userJson.put("password", request.getParameter("password"));	
		HttpHeaders header = new HttpHeaders();
		HttpEntity<String> requestEntity = new HttpEntity<String>(userJson.toString(), header);
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
			session.setAttribute("name", json.optString("name"));
			session.setMaxInactiveInterval(900);
			if (json.optString("msg").equalsIgnoreCase("Incorrect username and password")){
				mv.addObject("msg", json.optString("msg"));
				mv.setViewName("login");
			}
			else if (json.optString("role").equalsIgnoreCase("Vendor")) {
				mv.setViewName("redirect:/assignvendor");
			}else if (json.optString("role").equalsIgnoreCase("Customer")) {
				mv.setViewName("redirect:/assignvendor");
			}
			else if (json.optString("role").equalsIgnoreCase("Candidate")) {
				mv.setViewName("redirect:/findjob");
			}
		} catch (HttpServerErrorException ex) {
			if (ex.getStatusCode().value() == 401) {
				mv.setViewName("login");
			}
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/register",method=RequestMethod.POST)
	public ModelAndView addUser(HttpServletRequest request) {
		HttpHeaders header = new HttpHeaders();
		ModelAndView mv = new ModelAndView();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		JSONObject userJson = new JSONObject();
		userJson.put("username", request.getParameter("username"));
		if(request.getParameter("customerId") != null) {
			userJson.put("customerId", request.getParameter("customerId"));
			userJson.put("role", "vendor");
			userJson.put("reportingto", !request.getParameter("vendorId").isEmpty() ? request.getParameter("vendorId"):"0");
			userJson.put("isprimary", !request.getParameter("vendorId").isEmpty() ? "0":"1");
		}else {
			userJson.put("role", "customer");
		}
		userJson.put("password", request.getParameter("password"));
		userJson.put("firstname", request.getParameter("firstname"));
		userJson.put("lastname", request.getParameter("lastname"));
		userJson.put("createdby", request.getParameter("customerId"));
		HttpEntity<String> requestEntity = new HttpEntity<String>(userJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/register", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			JSONObject json = new JSONObject(jsonResonse);
			if(json.optString("msg").contains("successfully")) {
				mv.setViewName("redirect:/login");
			}
		} catch (HttpServerErrorException ex) {
				logger.error(ex);
		}
		return mv;
	}
	
	@RequestMapping(value="/candidatelogin.htm",method=RequestMethod.POST)
	@ResponseBody
	public String status(@RequestParam("username") String username,@RequestParam("password") String password,HttpServletRequest request) {
	JSONObject userJson = new JSONObject();
	JSONObject json = new JSONObject();
	userJson.put("username", username);
	userJson.put("password", password);	
	HttpHeaders header = new HttpHeaders();
	HttpEntity<String> requestEntity = new HttpEntity<String>(userJson.toString(), header);
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
		logger.error(e);
	}
	return json.toString();
}
}
