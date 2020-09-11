package com.hiring.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	private static Logger logger = Logger.getLogger(CandidateController.class);
	
	
	@RequestMapping(value = "/reviewcandidate",method=RequestMethod.GET)
	public ModelAndView candidateList(HttpServletRequest request){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = Utilities.userAuthentication(request);
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		String strUserId = request.getSession().getAttribute("userId") != null
				? request.getSession().getAttribute("userId").toString()
				: "";
		HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/reviewcandidate/"+strUserId+"",
					HttpMethod.GET, requestEntity, String.class);
			String mapList = response.getBody();
			JSONObject json = new JSONObject(mapList);
			JSONArray jsonArr = json.getJSONArray("mapList");
			List<CandidateModel> candi = new ArrayList<CandidateModel>();
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				CandidateModel  candimodel = new CandidateModel();
				candimodel.setCandidateid(jsonObj.optString("candidateId"));
				candimodel.setName(jsonObj.optString("name"));
				candimodel.setProfile(jsonObj.optString("profile"));
				candimodel.setEmailid(jsonObj.optString("emailId"));
				candimodel.setSubmittedto(jsonObj.optString("submittedto"));
				candimodel.setJobPostingId(jsonObj.optString("jobPostingId"));
				candimodel.setSubmittedType(jsonObj.optString("submittedType"));
				candimodel.setResume(jsonObj.optString("resume"));
				candimodel.setCandidateJobPostId(jsonObj.optString("candidateJobPostId"));
				candimodel.setVendorname(jsonObj.optString("vendorname"));
				candimodel.setCreatedon(jsonObj.optString("createdon"));
				candi.add(candimodel);
			}
			return new ModelAndView("reviewcandidate", "candidate", candi);
		} catch(Exception e){
			logger.error(e);
		}
		return new ModelAndView();
	}
	
	@RequestMapping(value = "/bulkupload",method=RequestMethod.GET)
	public String bulkupload(){
		return "bulkupload";
	}
	
	@RequestMapping(value = "/candidatelisting",method=RequestMethod.GET)
	public ModelAndView jobposting(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = Utilities.userAuthentication(request);
		HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
		try {
				ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/candidatelisting",
						HttpMethod.GET, requestEntity, String.class);
				String candidateList = response.getBody();
				JSONObject json = new JSONObject(candidateList);
				JSONArray jsonArr = json.getJSONArray("candidateList");
				List<CandidateModel> can = new ArrayList<CandidateModel>();
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj = jsonArr.getJSONObject(i);
					CandidateModel  canModel = new CandidateModel();
					canModel.setName(jsonObj.optString("name"));
					canModel.setEmailid(jsonObj.optString("emailid"));
					canModel.setDob(jsonObj.optString("dob"));
					canModel.setProfile(jsonObj.optString("profile"));
					can.add(canModel);
				}
				return new ModelAndView("candidatelisting", "candidateListing", can);
			} catch (Exception ex) {
				logger.error(ex);
			}
			return new ModelAndView();
		}

	
	@RequestMapping(value = "/candidate",method=RequestMethod.GET)
	public String candidate() {
		return "candidate";
	}
	
	@RequestMapping(value = "/addcandidate.htm",method=RequestMethod.POST)
	@ResponseBody
	public String insert(MultipartRequest multipartFileRequest,HttpServletRequest request){
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();	
		JSONObject jsonObject = new JSONObject();
		HttpHeaders header = new HttpHeaders();
		MultipartFile multipartfile = multipartFileRequest.getFile("file");
		String encodedString;
		try {
			encodedString = Base64.getEncoder().encodeToString(multipartfile.getBytes());
			JSONObject candidateJson = new JSONObject();
			JSONArray jsonArr= new JSONArray();
			candidateJson.put("firstname", request.getParameter("firstname"));
			candidateJson.put("lastname", request.getParameter("lastname"));
			candidateJson.put("password", request.getParameter("password"));
			candidateJson.put("emailid", request.getParameter("emailid"));
			candidateJson.put("username", request.getParameter("emailid"));
			candidateJson.put("role", "candidate");
			candidateJson.put("dob", request.getParameter("dob"));
			candidateJson.put("profile", request.getParameter("profile"));
			candidateJson.put("resumeupload", encodedString);
			jsonArr.put(candidateJson);
			jsonObject.put("candidateList",jsonArr);
			HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toString(), header);
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/addcandidate",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (IOException e) {
			logger.error(e);
		}
		return json.toString();
	}
	
	
	@RequestMapping(value = "/candidateProfileSubmit",method=RequestMethod.POST)
	@ResponseBody
	public String updatevendor(@RequestParam("submittedto") String submittedto,@RequestParam("candidateId") String candidateId,
			@RequestParam("jobpostingId") String jobpostingId, @RequestParam("submitType") String submitType,
			@RequestParam("candidateJobPostId") String candidateJobPostId, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = Utilities.userAuthentication(request);
		String createdBy = request.getSession().getAttribute("userId") != null
				? request.getSession().getAttribute("userId").toString()
				: "";

		JSONObject updatejson = new JSONObject();
		updatejson.put("submittedto", submittedto);
		updatejson.put("candidateId",candidateId);
		updatejson.put("jobpostingId", jobpostingId);
		updatejson.put("submittedType", submitType);
		updatejson.put("createdby", createdBy);
		updatejson.put("candidatejobpostId", candidateJobPostId);
		HttpEntity<String> requestEntity = new HttpEntity<String>(updatejson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/candidateProfileSubmit", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch(Exception e){
			logger.error(e);
		 }
		return json.toString();
	}
	
	@RequestMapping(value = "/fetchresume",method=RequestMethod.GET)
	@ResponseBody
	public String fetchlogo(HttpServletRequest request) {
			RestTemplate restTemplate = new RestTemplate();
			JSONObject json = new JSONObject();
			HttpHeaders header = new HttpHeaders();
			try {
			HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/fetchresume", HttpMethod.GET,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			}catch (Exception ex) {
				logger.error(ex);
			}
			return json.toString();
	}
	
	 @RequestMapping(value = "/candidatebulkupload.htm",method = RequestMethod.POST)
	 @ResponseBody
	 public String candidatebulkupload(MultipartRequest multipartFileRequest,HttpServletRequest request){
	        RestTemplate restTemplate = new RestTemplate();
	        JSONObject json = new JSONObject();
	        JSONObject jsonObject = new JSONObject();
	        JSONArray  rowjsonArr = new JSONArray();
			HttpHeaders header = Utilities.userAuthentication(request);
	         try {
	        	System.out.println("candidatebulkupload");
	 	        MultipartFile multipartfile = multipartFileRequest.getFile("file");
				XSSFWorkbook workbook = new XSSFWorkbook(multipartfile.getInputStream());
				XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) 
				{
					Row row = rowIterator.next();
					JSONObject rowJson = new JSONObject();
					if(row.getRowNum()!=0) {
						//For each row, iterate through all the columns
						//Check the cell type and format accordingly
                         rowJson.put("firstname", row.getCell(0).toString());
                         rowJson.put("lastname", row.getCell(1).toString());
                         rowJson.put("emailid", row.getCell(2).toString());
                         rowJson.put("dob",row.getCell(3).toString());
                         rowJson.put("profile",row.getCell(4).toString());
                         rowJson.put("resumeupload", row.getCell(5).toString());
                         rowJson.put("username", row.getCell(2).toString());
                         rowJson.put("role", "candidate");
						 rowjsonArr.put(rowJson);
					}
				}
				jsonObject.put("candidateList", rowjsonArr);
	        	System.out.println("candidatebulkupload"+jsonObject);
				HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toString(),header);
		        ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/candidatebulkupload",
		                HttpMethod.POST, requestEntity, String.class);
		        String jsonResonse = response.getBody();
		        json = new JSONObject(jsonResonse);
		        System.out.println("jsonResponse"+json);
			} catch (Exception e) {
				e.printStackTrace();
				//logger.error(e);
			}		   
	        return json.toString();
	    }
	 
	 @RequestMapping(value = "/profilecheck",method=RequestMethod.POST)
	 @ResponseBody
	 public String addtimes(@RequestParam("profile") String profile,  HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		HttpHeaders header = Utilities.userAuthentication(request);
		JSONObject canJson = new JSONObject();
		canJson.put("profile", profile);
		HttpEntity<String> requestEntity = new HttpEntity<String>(canJson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/profilecheck", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (Exception ex) {
			logger.error(ex);
		}
		return json.toString();
	}
}



