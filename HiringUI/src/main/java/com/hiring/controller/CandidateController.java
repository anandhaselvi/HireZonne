package com.hiring.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import com.hiring.model.CandidateModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;



@Controller
public class CandidateController {
	@RequestMapping(value = "/reviewcandidate", method = RequestMethod.GET)
	public ModelAndView candidateList(HttpServletRequest request){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString()
				: "";
		String userId = request.getSession().getAttribute("userId") != null
						? request.getSession().getAttribute("userId").toString()
						: "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username", username);
		HttpEntity<String> requestEntity = new HttpEntity<>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/reviewcandidate/"+userId+"",
					HttpMethod.GET, requestEntity, String.class);
			String mapList = response.getBody();
			JSONObject json = new JSONObject(mapList);
			JSONArray jsonArr = json.getJSONArray("mapList");
			List<CandidateModel> candi = new ArrayList<CandidateModel>();
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				CandidateModel  candimodel = new CandidateModel();
				candimodel.setCandidateid(jsonObj.getString("candidateId"));
				candimodel.setName(jsonObj.getString("name"));
				candimodel.setProfile(jsonObj.getString("profile"));
				candimodel.setEmailid(jsonObj.getString("emailid"));
				candimodel.setSubmittedto(jsonObj.getInt("submittedto"));
				candimodel.setJobPostingId(jsonObj.getString("jobPostingId"));
				candimodel.setSubmittedType(jsonObj.getString("submittedType"));
				candimodel.setResume(jsonObj.getString("resume"));
				candi.add(candimodel);
			}
			return new ModelAndView("reviewcandidate", "candidate", candi);
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ModelAndView();
	}
	
	
	@RequestMapping(value = "/addcandidate.htm", method = RequestMethod.POST)
	@ResponseBody
	public String insert(MultipartRequest multipartFileRequest,HttpServletRequest request){
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();								
		HttpHeaders header = new HttpHeaders();
		MultipartFile multipartfile = multipartFileRequest.getFile("file");
		String encodedString;
		try {
			encodedString = Base64.getEncoder().encodeToString(multipartfile.getBytes());
			JSONObject candidateJson = new JSONObject();
			candidateJson.put("firstname", request.getParameter("firstname"));
			candidateJson.put("lastname", request.getParameter("lastname"));
			candidateJson.put("password", request.getParameter("password"));
			candidateJson.put("emailid", request.getParameter("emailid"));
			candidateJson.put("username", request.getParameter("emailid"));
			candidateJson.put("role", "candidate");
			candidateJson.put("dob", request.getParameter("dob"));
			candidateJson.put("profile", request.getParameter("profile"));
			candidateJson.put("resumeupload", encodedString);
			System.out.println(candidateJson);
			HttpEntity<String> requestEntity = new HttpEntity<>(candidateJson.toString(), header);
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/addcandidate",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			System.out.println("counter"+json);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/candidateProfileSubmit", method = RequestMethod.POST)
	@ResponseBody
	public String updatevendor(@RequestParam("submittedto") String submittedto,@RequestParam("candidateId") String candidateId,
			@RequestParam("jobpostingId") String jobpostingId, @RequestParam("submitType") String submitType,
			HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null
				? request.getSession().getAttribute("username").toString()
				: "";
		String userId = request.getSession().getAttribute("userId") != null
				? request.getSession().getAttribute("userId").toString()
				: "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject updatejson = new JSONObject();
		updatejson.put("submittedto", submittedto);
		updatejson.put("candidateId",candidateId);
		updatejson.put("jobpostingId", jobpostingId);
		updatejson.put("submitType", submitType);
		updatejson.put("createdby", userId);
		HttpEntity<String> requestEntity = new HttpEntity<>(updatejson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/candidateProfileSubmit", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			System.out.println("counter"+json);
		} catch(Exception e){
				e.printStackTrace(); 
		 }
		return json.toString();
	}
	@RequestMapping(value = "/fetchresume", method = RequestMethod.GET)
	@ResponseBody
	public String fetchlogo(HttpServletRequest request) {
			RestTemplate restTemplate = new RestTemplate();
			JSONObject json = new JSONObject();
			HttpHeaders header = new HttpHeaders();
			try {
			HttpEntity<String> requestEntity = new HttpEntity<>("", header);
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/fetchresume", HttpMethod.GET,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			}catch (Exception ex) {
			}
			return json.toString();
	}
}

/*@RequestMapping(value = "/updatecandidate", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestParam("candidateid") String candidateid,@RequestParam("name") String name, @RequestParam("emailid") String emailid,
			@RequestParam("dob") String dob,@RequestParam("profile") String profile ,HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("username") != null ?
				request.getSession().getAttribute("username").toString():"";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("username",username);
		JSONObject dateJson = new JSONObject();
		dateJson.put("candidateid",Integer.toString(candidateid));
		dateJson.put("name", name);
		dateJson.put("emailid", emailid);
		dateJson.put("dob", dob);
		dateJson.put("profile", profile);
		
		HttpEntity<String> requestEntity = new HttpEntity<>(dateJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/update", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		}  catch (HttpClientErrorException | HttpServerErrorException ex) {
			logger.error(ex.getResponseBodyAsString());
		}
		return json.toString();
	}
	
	@RequestMapping(value = "/getcandidate.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getCandidate(@RequestParam("candidateid") String candidateid, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		/*String token = request.getSession().getAttribute("token") != null
				? request.getSession().getAttribute("token").toString()
				: "";
		String username = request.getSession().getAttribute("name") != null
				? request.getSession().getAttribute("name").toString(): "";
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.set("Authorization", token);
		header.set("name",name);		
		JSONObject candiJson = new JSONObject();
		candiJson.put("candidateid", candidateid);
		HttpEntity<String> requestEntity = new HttpEntity<>(candiJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getCustomer",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (Exception e){
			e.printStackTrace();
		}
		return json.toString();
	}
}	*/  