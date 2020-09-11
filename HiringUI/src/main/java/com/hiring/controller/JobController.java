package com.hiring.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hiring.model.JobModel;

@Controller
public class JobController {

	private static Logger logger = Logger.getLogger(JobController.class);
	
	@RequestMapping(value = "/createjobpost",method=RequestMethod.GET)
	public ModelAndView createjobpost(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = Utilities.userAuthentication(request);
		String userId = request.getSession().getAttribute("userId") != null
						? request.getSession().getAttribute("userId").toString(): "";
		HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
		try {
			ResponseEntity<String> response =	restTemplate.exchange(Utilities.readProperties() + "/createjobpost/"+userId, HttpMethod.GET,
						requestEntity, String.class);
			String jsonResonse = response.getBody();
			JSONObject json = new JSONObject(jsonResonse);
			mv.addObject("message", json.optString("message"));
			mv.setViewName("createjobpost");
		}
		catch (HttpServerErrorException ex) {
			if (ex.getStatusCode().value() == 401) {
				mv.setViewName("redirect:/login");
			}
		}
		return mv;
	}


	@RequestMapping(value = "/findjob",method=RequestMethod.GET)
	public String findjob() {
		return "findjob";
	}

	@RequestMapping(value = "/jobposting",method=RequestMethod.GET)
	public ModelAndView jobposting(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = Utilities.userAuthentication(request);
		HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
		try {
				ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/jobposting",
						HttpMethod.GET, requestEntity, String.class);
				String jobList = response.getBody();
				JSONObject json = new JSONObject(jobList);
				JSONArray jsonArr = json.getJSONArray("jobList");
				List<JobModel> job = new ArrayList<JobModel>();
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj = jsonArr.getJSONObject(i);
					JobModel  jobModel = new JobModel();
					jobModel.setTitle(jsonObj.getString("title"));
					jobModel.setLocation(jsonObj.getString("location"));
					jobModel.setExperiencerequired(jsonObj.getString("experiencerequired"));
					jobModel.setJobbdescription(jsonObj.getString("jobdescription"));
					jobModel.setKeyskillsrequired(jsonObj.getString("keyskillsrequired"));
					jobModel.setJobtype(jsonObj.getString("jobtype"));
					job.add(jobModel);
				}
				return new ModelAndView("jobposting", "jobListing", job);
			} catch (HttpServerErrorException ex) {
				if (ex.getStatusCode().value() == 401) {
					return  new ModelAndView("login");
				}
			}
			return new ModelAndView();
		}


	@RequestMapping(value="/jobList.htm",method=RequestMethod.POST)
	@ResponseBody
	public String getjobList(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();								
		HttpHeaders header = Utilities.userAuthentication(request);
		HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/jobList",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		} catch (Exception ex) {
			logger.error(ex);
		}
		return json.toString();
    }

	@RequestMapping(value = "/insertjob.htm",method=RequestMethod.POST)
	@ResponseBody
	public String inJob(@RequestParam("title") String title, @RequestParam("location") String location,
			@RequestParam("jobtype") String jobtype,@RequestParam("experiencerequired") String experiencerequired,
			@RequestParam("workauthorization")String workauthorization,@RequestParam("jobdescription") String jobdescription,
			@RequestParam("jobId") String jobId, @RequestParam("rate") String rate,@RequestParam("visatype") String visatype,
			HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();		
		JSONObject jsonObject = new JSONObject();
		HttpHeaders header = Utilities.userAuthentication(request);
		String role = request.getSession().getAttribute("role") != null
						? request.getSession().getAttribute("role").toString(): "";
		JSONObject jobJson = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		jobJson.put("title", title);
		jobJson.put("location", location);
		jobJson.put("jobtype", jobtype);
		jobJson.put("experiencerequired",experiencerequired);
		jobJson.put("workauthorization", workauthorization);
		jobJson.put("jobdescription", jobdescription);
		jobJson.put("updatedby", request.getSession().getAttribute("userId").toString()); 
		jobJson.put("role", role);
		jobJson.put("jobId", jobId);
		jobJson.put("rate", rate);
		jobJson.put("visatype", visatype);
		jsonArr.put(jobJson);
		jsonObject.put("jobList",jsonArr);
		HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toString(), header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/insertjob",
			HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
		} catch (Exception ex) {
			logger.error(ex);
		}
		return json.toString();
	}
	
	@RequestMapping(value="/getstatus.htm",method=RequestMethod.POST)
	@ResponseBody
	public String status(@RequestParam("vendorId") String vendorId,@RequestParam("jobpostingId") String jobpostingId,
			@RequestParam("submitType") String submitType,@RequestParam("customerId") String customerId,HttpServletRequest request) {
	RestTemplate restTemplate = new RestTemplate();
	JSONObject json = new JSONObject();
	HttpHeaders header = Utilities.userAuthentication(request);
	String userId = request.getSession().getAttribute("userId") != null
					? request.getSession().getAttribute("userId").toString(): "";
	JSONObject jsonobj = new JSONObject();
	jsonobj.put("jobpostingId", jobpostingId);
	jsonobj.put("candidateId", userId);
	if(submitType.equalsIgnoreCase("customer")) {
	jsonobj.put("submittedto", customerId);
	}else {
		jsonobj.put("submittedto", vendorId);
	}
	jsonobj.put("vendorId", vendorId);
	jsonobj.put("customerId", customerId);
	jsonobj.put("submittedType", submitType);
	jsonobj.put("createdby", userId);
	HttpEntity<String> requestEntity = new HttpEntity<String>(jsonobj.toString(), header);
	try {
		ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/getstatus",
		HttpMethod.POST, requestEntity, String.class);
		String jsonResonse = response.getBody();
		json = new JSONObject(jsonResonse);
	} catch (Exception ex) {
		logger.error(ex);
	}
	return json.toString();
}
	
	
	
	@RequestMapping(value="/searchjobList.htm",method=RequestMethod.POST)
	@ResponseBody
	public String searchjobList(HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();								
		HttpHeaders header = Utilities.userAuthentication(request);
		String userId = request.getSession().getAttribute("userId") != null
						? request.getSession().getAttribute("userId").toString(): "";
		HttpEntity<String> requestEntity = new HttpEntity<String>("", header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/searchjobList/"+userId+"",
					HttpMethod.POST, requestEntity, String.class);
			String jsonResonse = response.getBody();
			json = new JSONObject(jsonResonse);
			return json.toString();
		} catch (Exception ex) {
			 logger.error(ex);
		}
		return json.toString();
    }
	
	 @RequestMapping(value = "/jobbulkupload.htm",method=RequestMethod.POST)
     @ResponseBody
     public String candidatebulkupload(MultipartRequest multipartFileRequest,HttpServletRequest request){
            RestTemplate restTemplate = new RestTemplate();
            JSONObject json = new JSONObject();
            JSONObject jsonObject = new JSONObject();
            JSONArray  rowjsonArr = new JSONArray();
    		HttpHeaders header = Utilities.userAuthentication(request);
            String userId = request.getSession().getAttribute("userId") != null
                    ? request.getSession().getAttribute("userId").toString()
                    : "";
              try {
                 MultipartFile multipartfile = multipartFileRequest.getFile("file");
                XSSFWorkbook workbook = new XSSFWorkbook(multipartfile.getInputStream());
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext())
                {
                    Row row = rowIterator.next();
                    JSONObject rowJson = new JSONObject();
                    if(row.getRowNum()!=0 ) {
                    	DataFormatter data = new DataFormatter();
                    		//For each row, iterate through all the columns
        					rowJson.put("title", row.getCell(0));
        					rowJson.put("location", row.getCell(1).toString());
        					rowJson.put("jobtype", row.getCell(2).toString());
        					rowJson.put("experiencerequired",row.getCell(3).toString());
                            rowJson.put("workauthorization",data.formatCellValue(row.getCell(4))); 
                            rowJson.put("jobdescription", row.getCell(5).toString());
                            rowJson.put("updatedby", userId);
                            rowJson.put("role", "vendor");
                            rowJson.put("jobId",row.getCell(6));
                            rowJson.put("rate", row.getCell(7).toString());
                            rowJson.put("visatype", row.getCell(8).toString());
                            rowjsonArr.put(rowJson);
                    }
                 } 
                
                jsonObject.put("jobList", rowjsonArr);
                HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toString(),header);
                ResponseEntity<String> response = restTemplate.exchange(Utilities.readProperties() + "/jobbulkupload",
                        HttpMethod.POST, requestEntity, String.class);
                String jsonResonse = response.getBody();
                json = new JSONObject(jsonResonse);
            } catch (IOException e) {
                logger.error(e);
            }          
            return json.toString();
        }
}
	



