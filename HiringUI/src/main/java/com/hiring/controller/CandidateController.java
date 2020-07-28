package com.hiring.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.DataFormatter;
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
				candimodel.setCandidateid(jsonObj.optString("candidateId"));
				candimodel.setName(jsonObj.optString("name"));
				candimodel.setProfile(jsonObj.optString("profile"));
				candimodel.setEmailid(jsonObj.optString("emailid"));
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
			@RequestParam("candidateJobPostId") String candidateJobPostId, HttpServletRequest request) {
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
		updatejson.put("submittedType", submitType);
		updatejson.put("createdby", userId);
		updatejson.put("candidatejobpostId", candidateJobPostId);
		HttpEntity<String> requestEntity = new HttpEntity<>(updatejson.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/candidateProfileSubmit", HttpMethod.POST,
					requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
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
	
	 @RequestMapping(value = "/candidatebulkupload.htm", method = RequestMethod.POST)
	 @ResponseBody
	 public String candidatebulkupload(MultipartRequest multipartFileRequest,HttpServletRequest request){
	        RestTemplate restTemplate = new RestTemplate();
	        JSONObject json = new JSONObject();
	        JSONObject jsonObject = new JSONObject();
	        JSONArray  rowjsonArr = new JSONArray();
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
	         try {
	 	        MultipartFile multipartfile = multipartFileRequest.getFile("file");
				XSSFWorkbook workbook = new XSSFWorkbook(multipartfile.getInputStream());
				XSSFSheet sheet = workbook.getSheetAt(0);
				int rows = sheet.getPhysicalNumberOfRows();
				System.out.println(rows);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) 
				{
					Row row = rowIterator.next();
					JSONObject rowJson = new JSONObject();
					System.out.println("row"+row);
				    System.out.println(row.getRowNum());
				    
					if(row.getRowNum()!=0) {
					//For each row, iterate through all the columns
						DataFormatter dataFormatter = new DataFormatter();
						//rowJson.put("name", row.getCell(0));
						//rowJson.put("dateofBirth", row.getCell(1));
						System.out.println(row.getCell(1));
						String dateofbirth = dataFormatter.formatCellValue(row.getCell(3));
						System.out.println(dateofbirth);
						//Check the cell type and format accordingly
						 rowJson.put("candidateId", row.getCell(0));
                         rowJson.put("firstname", row.getCell(1).toString());
                         rowJson.put("lastname", row.getCell(2).toString());
                         rowJson.put("emailid", row.getCell(3).toString());
                         String dob = dataFormatter.formatCellValue(row.getCell(4));
                         System.out.println(dob);
                         rowJson.put("dob",dob);
                         rowJson.put("profile",row.getCell(5).toString());
                         rowJson.put("resumeupload", row.getCell(6).toString());
                         rowJson.put("userId", row.getCell(7));
                         rowJson.put("createdby", row.getCell(8));
                        // rowJson.put("createdon", row.getCell(9).toString());
//						DataFormatter dataFormatter = new DataFormatter();
//						rowJson.put("name", row.getCell(0));
//						rowJson.put("dateofBirth", row.getCell(1).getDateCellValue());
//						System.out.println(row.getCell(1).getDateCellValue());
//						String dateofbirth = dataFormatter.formatCellValue(row.getCell(3));
//						System.out.println(dateofbirth);
						rowjsonArr.put(rowJson);
					}
				}
				jsonObject.put("CandidateList", rowjsonArr);
				System.out.println("json"+jsonObject);
				HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toString(),header);
		        ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/candidatebulkupload",
		                HttpMethod.POST, requestEntity, String.class);
		        String jsonResonse = response.getBody();
		        json = new JSONObject(jsonResonse);
		        System.out.println("counter"+json);
			} catch (IOException e) {
				e.printStackTrace();
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