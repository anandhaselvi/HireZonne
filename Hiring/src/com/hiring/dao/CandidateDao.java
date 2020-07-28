package com.hiring.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hiring.db.DBConnection;

public class CandidateDao implements Candidate{

	private static Logger logger = Logger.getLogger(CandidateDao.class);
	private static DBConnection conn = DBConnection.getInstance();
	private final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	private final SimpleDateFormat dmy = new SimpleDateFormat("MM/dd/yyyy");
	 Date d1;
	 @Override
	public JSONObject insertapplicant(JSONObject json) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArr =json.getJSONArray("CandidateList");
		for(int i=0;i<jsonArr.length();i++) {
			JSONObject candidateJson = jsonArr.getJSONObject(i);
		
			int counter = Nameexists(candidateJson.getString("firstname")+" "+candidateJson.getString("lastname"));
			if(counter>0){
			jsonObject.put("msg","name already exists");
			}
			else{
			Connection con =null;
		    PreparedStatement preparedstmt = null;
			try{
				 String sql="Insert into candidate(name,emailid,dob,profile,userid,resume,createdby,createdon)values(?,?,?,?,?,?,?,?)";
				 con=conn.getConnection();
			     preparedstmt = con.prepareStatement(sql);
			     preparedstmt.setString(1,candidateJson.getString("firstname")+" "+candidateJson.getString("lastname"));
			     preparedstmt.setString(2,candidateJson.getString("emailid"));
			     d1 = dmy.parse(candidateJson.getString("dob"));
			     preparedstmt.setString(3, ymd.format(d1));
			     preparedstmt.setString(4,candidateJson.getString("profile"));
			     preparedstmt.setString(5, candidateJson.getString("userId"));
			     preparedstmt.setString(6, candidateJson.getString("resumeupload"));
			     preparedstmt.setString(7, candidateJson.getString("userId"));
			     preparedstmt.setString(8, LocalDateTime.now().toString());
			     preparedstmt.executeUpdate();
			     jsonObject.put("msg","candidate created successfully");
			     }catch(SQLException| ParseException e) {
					logger.error(e);
					jsonObject.put("msg","candidate creation failed");
			     }
				finally {
					if (con != null) { try { con.close(); } catch (SQLException e){}}
					if (preparedstmt != null) { try {preparedstmt.close();} catch (SQLException e){}}
				  }	
				}
		}
			 return jsonObject;
		}
	 
	public int Nameexists(String name){
		Connection con=null;
		PreparedStatement preparedstmt=null;
		ResultSet rs=null;
		try{
			String sql="SELECT name from candidate WHERE name=?";
			int counter =0;
			con=conn.getConnection();
			preparedstmt=con.prepareStatement(sql);
			preparedstmt.setString(1,name);		
			rs=preparedstmt.executeQuery();
			if(rs.next()){
				counter=1;
			}
			return counter;
		}catch (SQLException e){
			logger.error(e);
			return 0;
			}finally {
				if (con != null) { try { con.close(); } catch (SQLException e){}}
				if (preparedstmt != null) { try {preparedstmt.close();} catch (SQLException e){}}
				if (rs != null) { try { rs.close(); } catch (SQLException e) {}}
    	}
	}
	@Override	
	public JSONObject NameById(String candidateid) {
		JSONObject json = new JSONObject();
		 Connection con = null;
		 PreparedStatement preparedstmt = null;
		 ResultSet rs = null;
		 try{
			 String sql="SELECT name,emailid,dob,profile FROM candidate WHERE candidateid=?";
			 con=conn.getConnection();
			 preparedstmt=con.prepareStatement(sql);
			 preparedstmt.setString(1,candidateid);
			 rs=preparedstmt.executeQuery();
			 if(rs.next()){
				 json.put("name",rs.getString("name"));
				 json.put("emailid",rs.getString("emailid"));
				 d1 = dmy.parse(rs.getString("dob"));
				 json.put("dob", ymd.format(d1));
				 json.put("profile",rs.getString("profile"));
			 }
		 }
		 catch (SQLException  | ParseException e) {
				logger.error(e);
				
			}finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		   }
		return json;
	 }
	
		@Override
		public JSONObject updatecandidate(JSONObject jsonObject){
			JSONObject json=new JSONObject();
			Connection con=null;
			PreparedStatement preparedstmt=null;
			
			try{
				String sql="UPDATE candidate SET name=?,emailid=?,dob=?,profile=? WHERE candidateid=?";
				con=conn.getConnection();
				preparedstmt= con.prepareStatement(sql);
				preparedstmt.setString(1,jsonObject.getString("name"));
				preparedstmt.setString(2,jsonObject.getString("emailid"));
				d1 = dmy.parse(jsonObject.getString("dob"));
				preparedstmt.setString(3,ymd.format(d1));
				preparedstmt.setString(4,jsonObject.getString("profile"));
				preparedstmt.setString(5,jsonObject.getString("candidateid"));
				preparedstmt.executeUpdate();
				json.put("msg","candidate updated successfully");
				return json;
			} catch(SQLException |ParseException e){
				logger.error(e);
				json.put("msg","candidate updation failed");
				return json;
				}finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		  }
		
		}
	@Override
	public JSONObject candidatelist(){
		JSONObject json = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			String sql = "SELECT candidateid,name,emailid,dob,profile from candidate ";
			con = conn.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()){
				JSONObject candidate = new JSONObject();
				candidate.put("candidateid",rs.getInt("candidateid"));
				candidate.put("name",rs.getString("name"));
				candidate.put("emailid",rs.getString("emailid"));
				d1 = ymd.parse(rs.getString("dob"));
	     		candidate.put("dob",(dmy.format(d1)));
				candidate.put("profile",rs.getString("profile"));
				jsonArr.put(candidate);
			}
			json.put("candidatelist", jsonArr);
			} catch (SQLException | ParseException e) {
				logger.error(e);
				
			}finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		   }
		return json;
	}
	
@Override
public JSONObject candidatemap(String userId) {
	JSONObject json = new JSONObject();
	JSONArray jsonarr = new JSONArray();
	VendorDao vendordao = new VendorDao();
	Connection con = null;
	PreparedStatement preparedstmt = null;
	ResultSet rs = null;
	try {
		String sql ="SELECT distinct candidate.name,candidate.emailid,candidate.dob,candidate.profile,"
				+ "cjp.candidateId,cjp.jobpostingId,cjp.submittedto,candidate.resume,cjp.id,vendorname,vendor.customer,"
				+ "vendor.reportingto,cjp.createdon,cjp.createdby "
				+ "from candidate INNER JOIN candidatejobpostingmapping cjp ON "
				+ "candidate.userid= cjp.candidateId LEFT JOIN vendor ON cjp.submittedto = vendor.userId "
				+ " WHERE cjp.submittedto=?";
		con =conn.getConnection();
		preparedstmt = con.prepareStatement(sql);
		preparedstmt.setString(1, userId);
		rs=preparedstmt.executeQuery();
		while(rs.next()) {
			JSONObject can = new JSONObject();
			can.put("name", rs.getString("name"));
			can.put("emailid", rs.getString("emailid"));
			//d1 = ymd.parse(rs.getString("dob"));
     		//can.put("dob",(dmy.format(d1)));
			can.put("profile", rs.getString("profile"));
			can.put("candidateId", rs.getString("candidateId"));
			can.put("jobPostingId", rs.getString("jobpostingId"));
			if(rs.getString("customer") != null && rs.getString("reportingto") != null) {
			can.put("submittedto", rs.getString("reportingto").equalsIgnoreCase("0") ? rs.getString("customer"):rs.getString("reportingto"));
			can.put("submittedType", rs.getString("reportingto").equalsIgnoreCase("0") ? "Customer":"Primaryvendor");
			}
			can.put("resume", rs.getString("resume"));
			can.put("candidateJobPostId", rs.getString("id"));
			//can.put("vendorname", rs.getString("vendorname"));
			can.put("createdon", rs.getString("createdon"));
			JSONObject vendorjson = vendordao.getReportingto(rs.getString("createdby"));
			can.put("vendorname",vendorjson.optString("reportingname"));

			jsonarr.put(can);
		}
		json.put("mapList", jsonarr);
		} catch(SQLException e) {
			logger.error(e);
		} finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
	   }
	return json;
	}	

	public JSONObject candidateProfileSubmit(JSONObject jsonObject) {
		JSONObject json =new JSONObject();
		JobDao jobdao = new JobDao();
		Connection con =null;
		PreparedStatement preparedstmt = null;
		try {
			//String[] vendorIds = jsonObject.getString("vendorId").replaceAll(",$","").split(",");
			String sql = "UPDATE candidatejobpostingmapping SET submittedto=?,createdby=?,createdon=? where candidateId=? AND jobpostingId =?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, jsonObject.getString("submittedto"));
			preparedstmt.setString(2,jsonObject.getString("createdby"));
			preparedstmt.setString(3, LocalDateTime.now().toString());
			preparedstmt.setString(4, jsonObject.getString("candidateId"));
			preparedstmt.setString(5, jsonObject.getString("jobpostingId"));
			preparedstmt.executeUpdate();
			jobdao.candidatejobappliedhistory(jsonObject);
		 	json.put("msg","Profile Submitted");
			return json;
		} catch (SQLException e) {
			logger.error(e);
			json.put("msg","Profile Submission failed");
			return json;
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		}
	 }
	
//	public String read(InputStream file)
//	{
//		try
//		{
//			//FileInputStream file = new FileInputStream(new File("CandidateDetails.xlsx"));
//			System.out.println("file"+file);
//			//Create Workbook instance holding reference to .xlsx file
//			XSSFWorkbook workbook = new XSSFWorkbook(file);
//
//			//Get first/desired sheet from the workbook
//			XSSFSheet sheet = workbook.getSheetAt(0);
//
//			//Iterate through each rows one by one
//			Iterator<Row> rowIterator = sheet.iterator();
//			while (rowIterator.hasNext()) 
//			{
//				Row row = rowIterator.next();
//				System.out.println(row);
//				if(row.getRowNum()!=0) {
//				//For each row, iterate through all the columns
//					DataFormatter dataFormatter = new DataFormatter();
//					System.out.println(dataFormatter.formatCellValue(row.getCell(0)));
//					System.out.println(row.getCell(1));
//					String dateofbirth = dataFormatter.formatCellValue(row.getCell(3));
//					System.out.println(dateofbirth);
//				}
//			}
//			//file.close();
//		} 
//		catch (Exception e) 
//		{
//			logger.error(e);
//		}
//		return null;
//	}
}

