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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.hiring.model.VendorModel;


@Controller
public class VendorController {

	private static Logger logger = Logger.getLogger(VendorController.class);

	@RequestMapping(value = "/vendor", method = RequestMethod.GET)
	public String vendor() {
		return "vendor";
	}

	@RequestMapping(value = "/vendorinsert", method = RequestMethod.POST)
	@ResponseBody
	public String invendor(@RequestParam("vendorname") String vendorname, @RequestParam("description") String description,
			@RequestParam("isprimary") String isprimary, @RequestParam("customer") String customer,
			@RequestParam("reportingto")String reportingto,
			HttpServletRequest request) {
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
		JSONObject vendorJson = new JSONObject();
		vendorJson.put("vendorname", vendorname);
		vendorJson.put("description", description);
		vendorJson.put("isprimary", isprimary);
		vendorJson.put("customer",customer);
		vendorJson.put("reportingto",reportingto);
		HttpEntity<String> requestEntity = new HttpEntity<>(vendorJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/vendorinsert",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			System.out.println("counter"+json);
		}  catch(Exception e){
			e.printStackTrace();
			}
		return json.toString();
	}
	
	@RequestMapping(value = "/getcustomer", method = RequestMethod.POST)
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
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getcustomer",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			System.out.println("counter"+json);
			return json.toString();
		}  catch(Exception e){
			e.printStackTrace();
			}
		return json.toString();

	}
	
	
	@RequestMapping(value = "/getreporting", method = RequestMethod.POST)
	@ResponseBody
	public String getrep(HttpServletRequest request) {
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
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getreporting",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			System.out.println("counter"+json);
			return json.toString();
		}  catch(Exception e){
			e.printStackTrace();
			}
		return json.toString();
	}
	
	
	@RequestMapping(value = "/assignvendor", method = RequestMethod.GET)
	public ModelAndView vendorList(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		String userId = request.getSession().getAttribute("userId") != null
						? request.getSession().getAttribute("userId").toString(): "";
		String role = request.getSession().getAttribute("role") != null
								? request.getSession().getAttribute("role").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
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
			System.out.println("counter"+json);
			return new ModelAndView("assignvendor", "ven", vens);
		}catch (HttpClientErrorException | HttpServerErrorException ex) {
				if (ex.getStatusCode().value() == 401) {
					return new ModelAndView("login");
				}else if(ex.getStatusCode().value() == 500) {
					return new ModelAndView("login");
				}		
			}
		return new ModelAndView();

	}

	
	@RequestMapping(value = "/vendorupdate", method = RequestMethod.POST)
	@ResponseBody
	public String updatevendor(@RequestParam("vendorId") String vendorId,HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString()
				: "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject updatejson = new JSONObject();
		updatejson.put("vendorId", vendorId);
		HttpEntity<String> requestEntity = new HttpEntity<>(updatejson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/vendorupdate", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			System.out.println("counter"+json);
		} catch(Exception e){
				e.printStackTrace(); 
				}
			return json.toString();
		}

	

	@RequestMapping(value = "/fetchlist", method = RequestMethod.POST)
	@ResponseBody
	public String fetchvendor(@RequestParam("vendorId") String vendorId, HttpServletRequest request) {
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
		JSONObject Json = new JSONObject();
		Json.put("vendorId", vendorId);
		HttpEntity<String> requestEntity = new HttpEntity<>(Json.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/fetchlist", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			System.out.println("counter"+json);
		} catch (Exception e){
			e.printStackTrace(); 
			
		}
		return json.toString();
	}

	@RequestMapping(value = "/sendmail",method = RequestMethod.POST)
	@ResponseBody
	public String send(@RequestParam("emailid") String emailid, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("emailid"+emailid);
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString(): "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString(): "";
		String userId = request.getSession().getAttribute("userId") != null
						? request.getSession().getAttribute("userId").toString(): "";
		String role = request.getSession().getAttribute("role") != null
								? request.getSession().getAttribute("role").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject Json = new JSONObject();
		Json.put("email", emailid);
		Json.put("userId", userId);
		Json.put("role", role);
	//	Json.put("ids", ids);
		//System.out.println("emailid"+Json.getString("emailid"));
		HttpEntity<String> requestEntity = new HttpEntity<>(Json.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/sendmail", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		}  catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return "";
	}


}

	
	/*@RequestMapping(value = "/fetchlist", method = RequestMethod.POST)
	@ResponseBody
	public String fetchvendor(@RequestParam("vendorId") String vendorId, HttpServletRequest request) {
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
		JSONObject Json = new JSONObject();
		Json.put("vendorId", vendorId);
		HttpEntity<String> requestEntity = new HttpEntity<>(Json.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/fetchlist", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}

	@RequestMapping(value = "/vendorupdate", method = RequestMethod.POST)
	@ResponseBody
	public String updatevendor(@RequestParam("vendorId") int vendorId, @RequestParam("vendorname") String vendorname,
			@RequestParam("description") String description , @RequestParam("isprimary") int isprimary,
		    		    @RequestParam("customer") int customer, @RequestParam("reportingto") int reportingto,HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString()
				: "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject updatejson = new JSONObject();
		updatejson.put("vendorId", Integer.toString(vendorId));
		updatejson.put("vendorname", vendorname);
		updatejson.put("description", description);
		updatejson.put("isprimary", isprimary);
		updatejson.put("customer", customer);
		updatejson.put("reportingto", reportingto);
		HttpEntity<String> requestEntity = new HttpEntity<>(updatejson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/vendorupdate", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);

		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
			
		}
		return json.toString();
	}
	
	
}*/
