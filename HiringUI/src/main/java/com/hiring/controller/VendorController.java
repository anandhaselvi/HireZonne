package com.hiring.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
public class VendorController {

	private static Logger logger = Logger.getLogger(VendorController.class);

	@RequestMapping(value = "/vendor",method=RequestMethod.GET)
	public String vendor() {
		return "vendor";
	}

	@RequestMapping(value = "/vendorinsert.htm",method=RequestMethod.POST)
	@ResponseBody
	public String invendor(@RequestParam("companydetails") String companydetails,
			@RequestParam("taxid") String taxid,@RequestParam("address") String address,
			@RequestParam("contact") String contact,@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,@RequestParam("email") String email,
			@RequestParam("password") String password,@RequestParam("phone") String phone,
			@RequestParam("insurance") String insurance,@RequestParam("certification") String certification,
			@RequestParam("partnership") String partnership,@RequestParam("category") String category,
			@RequestParam("customerId") String customerId,@RequestParam("vendorId") String vendorId,
			HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();								
		HttpHeaders header = new HttpHeaders();
		JSONObject vendorJson = new JSONObject();
		try {
		vendorJson.put("customerId", customerId);
		vendorJson.put("reportingto", vendorId);
		vendorJson.put("isprimary", vendorId.equalsIgnoreCase("0") ? "1":"0");
		vendorJson.put("companydetails",companydetails);
		vendorJson.put("taxid",taxid);
		vendorJson.put("address",address);
		vendorJson.put("contact",contact);
		vendorJson.put("firstname",firstname);
		vendorJson.put("lastname",lastname);
		vendorJson.put("email",email);
		vendorJson.put("password",password);
		vendorJson.put("phone",phone);
		vendorJson.put("insurance",insurance);
		vendorJson.put("certification",certification);
		if(customerId.equalsIgnoreCase("0")) {
			vendorJson.put("status","unapproved");
		}else {
			vendorJson.put("status","approved");
		}
		vendorJson.put("partnership",partnership);
		vendorJson.put("role", "vendor");
		vendorJson.put("username", email);
		vendorJson.put("category", category);
		System.out.println("vendor"+vendorJson);
		HttpEntity<String> requestEntity = new HttpEntity<String>(vendorJson.toString(), header);
		ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/vendorinsert",
				HttpMethod.POST, requestEntity, String.class);
		String jsonResonse = response.getBody();
		json = new JSONObject(jsonResonse);
		}catch(Exception e){
			logger.error(e);
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/getcustomer",method=RequestMethod.POST)
	@ResponseBody
	public String getcust(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getcustomer",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		}  catch(Exception e){
			logger.error(e);
			}
		return json.toString();

	}
	
	
	@RequestMapping(value = "/getreporting",method=RequestMethod.POST)
	@ResponseBody
	public String getrep(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = Utilities.userAuthentication(request);
		HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getreporting",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		}  catch(Exception e){
			logger.error(e);
			}
		return json.toString();
	}
	
	
	@RequestMapping(value = "/assignvendor",method=RequestMethod.GET)
	public ModelAndView vendorList(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = Utilities.userAuthentication(request);
		String userId = request.getSession().getAttribute("userId") != null
						? request.getSession().getAttribute("userId").toString(): "";
		String role = request.getSession().getAttribute("role") != null
								? request.getSession().getAttribute("role").toString(): "";
		HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/assignvendor/"+userId+"/"+role+"", HttpMethod.GET,
					requestEntity, String.class);
			String vendorList = response.getBody();
			JSONObject json = new JSONObject(vendorList);
			JSONArray jsonArr = json.getJSONArray("vendorList");
			List<VendorModel> vens = new ArrayList<VendorModel>();
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				VendorModel venModel = new VendorModel();
				venModel.setVendorId(jsonObj.getInt("vendorId"));
				venModel.setVendorname(jsonObj.optString("vendorname"));
				venModel.setDescription(jsonObj.optString("description"));
			    venModel.setCustomer(jsonObj.optString("customer"));
				venModel.setReportingto(jsonObj.optString("reportingto"));
				venModel.setEmailId(jsonObj.optString("emailId"));
				vens.add(venModel);
			}
			return new ModelAndView("assignvendor", "ven", vens);
		}catch (HttpServerErrorException ex) {
				if (ex.getStatusCode().value() == 401) {
					return new ModelAndView("login");
				}	
			}
		return new ModelAndView();

	}

	
	@RequestMapping(value = "/vendorupdate",method=RequestMethod.POST)
	@ResponseBody
	public String updatevendor(@RequestParam("vendorId") String vendorId,HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = Utilities.userAuthentication(request);
		JSONObject updatejson = new JSONObject();
		updatejson.put("vendorId", vendorId);
		HttpEntity<String> requestEntity = new HttpEntity<String>(updatejson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/vendorupdate", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch(Exception e){
				logger.error(e);
				}
			return json.toString();
		}

	

	@RequestMapping(value = "/fetchlist",method=RequestMethod.POST)
	@ResponseBody
	public String fetchvendor(@RequestParam("vendorId") String vendorId, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = Utilities.userAuthentication(request);
		JSONObject Json = new JSONObject();
		Json.put("vendorId", vendorId);
		HttpEntity<String> requestEntity = new HttpEntity<String>(Json.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/fetchlist", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (Exception e){
			logger.error(e);
			
		}
		return json.toString();
	}

	@RequestMapping(value = "/sendmail",method=RequestMethod.POST)
	@ResponseBody
	public String send(@RequestParam("emailid") String emailid, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = Utilities.userAuthentication(request);
		String userId = request.getSession().getAttribute("userId") != null
						? request.getSession().getAttribute("userId").toString(): "";
		String role = request.getSession().getAttribute("role") != null
								? request.getSession().getAttribute("role").toString(): "";
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("email", emailid);
		jsonObj.put("userId", userId);
		jsonObj.put("role", role);
		System.out.println("vendor"+jsonObj);
		HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObj.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/sendmail", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			JSONObject json = new JSONObject(jsonResonse);
			return json.toString();
		}  catch (HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return "";
	}
	
	@RequestMapping(value = "/send",method=RequestMethod.POST)
    @ResponseBody
    public String sendmail(@RequestParam("vendorId") int vendorId,@RequestParam("submitType") String submitType, HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = Utilities.userAuthentication(request);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("vendorId",vendorId);
        jsonObj.put("submitType", submitType);
         HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObj.toString(), header);
        try {
            ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/send", HttpMethod.POST,
                    requestEntity, String.class);
            String jsonResonse = response.getBody();
            JSONObject json = new JSONObject(jsonResonse);
            return json.toString();
        }  catch (HttpServerErrorException ex) {
        	logger.error(ex);
        }
        return "";
    }

}

