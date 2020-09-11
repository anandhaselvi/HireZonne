package com.hiring.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	private Date d1;
	 public JSONObject insertApplicant(JSONObject json) {
		JSONObject jsonObject = new JSONObject();
			int counter = Nameexists(json.getString("firstname")+" "+json.getString("lastname"));
			if(counter>0){
			jsonObject.put("msg","name already exists");
			}
			else{
			Connection con =null;
		    PreparedStatement preparedstmt = null;
			try{
				 String sql="Insert INTO candidate(name,emailid,dob,profile,userid,resume,createdby,createdon)VALUES(?,?,?,?,?,?,?,?)";
				 con=conn.getConnection();
			     preparedstmt = con.prepareStatement(sql);
			     preparedstmt.setString(1, json.getString("firstname")+" "+json.getString("lastname"));
			     preparedstmt.setString(2, json.getString("emailid"));
			     d1 = dmy.parse(json.getString("dob"));
			     preparedstmt.setString(3, ymd.format(d1));
			     preparedstmt.setString(4, json.getString("profile"));
			     preparedstmt.setString(5, json.getString("userId"));
			     preparedstmt.setString(6, json.getString("resumeupload"));
			     preparedstmt.setString(7, json.getString("userId"));
			     preparedstmt.setString(8, LocalDateTime.now().toString());
			     preparedstmt.executeUpdate();
			     jsonObject.put("msg","candidate created successfully");
			     }catch(Exception e) {
					logger.error(e);
					jsonObject.put("msg","candidate creation failed");
			     }
				finally {
					if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
					if (preparedstmt != null) { try {preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				  }	
				}
		
			 return jsonObject;
		}
	 
	public int Nameexists(String name){
		Connection con=null;
		PreparedStatement preparedstmt=null;
		ResultSet rs=null;
		try{
			String sql="SELECT name FROM candidate WHERE name=?";
			int counter =0;
			con=conn.getConnection();
			preparedstmt=con.prepareStatement(sql);
			preparedstmt.setString(1,name);		
			rs=preparedstmt.executeQuery();
			if(rs.next()){
				counter=1;
			}
			return counter;
		}catch (Exception e){
			logger.error(e);
			return 0;
			}finally {
				if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				if (preparedstmt != null) { try {preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
    	}
	}
	public JSONObject nameById(String candidateid) {
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
		 catch (Exception e) {
				logger.error(e);
				
			}finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		   }
		return json;
	 }
	
		public JSONObject updateCandidate(JSONObject jsonObject){
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
			} catch(Exception e){
				logger.error(e);
				json.put("msg","candidate updation failed");
				return json;
				}finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		  }
		
		}
	public JSONObject candidateList(){
		JSONObject json = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			String sql = "SELECT candidateid,name,emailid,dob,profile,resume FROM candidate ";
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
				candidate.put("resume", rs.getString("resume"));
				jsonArr.put(candidate);
			}
			json.put("candidateList", jsonArr);
			} catch (Exception e) {
				logger.error(e);
				
			}finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		   }
		return json;
	}
	
public JSONObject candidateMap(String userId) {
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
				+ "FROM candidate INNER JOIN candidatejobpostingmapping cjp ON "
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
			can.put("profile", rs.getString("profile"));
			can.put("candidateId", rs.getString("candidateId"));
			can.put("jobPostingId", rs.getString("jobpostingId"));
			if(rs.getString("customer") != null && rs.getString("reportingto") != null) {
			can.put("submittedto", rs.getString("reportingto").equalsIgnoreCase("0") ? rs.getString("customer"):rs.getString("reportingto"));
			can.put("submittedType", rs.getString("reportingto").equalsIgnoreCase("0") ? "Customer":"Primaryvendor");
			}
			can.put("resume", rs.getString("resume"));
			can.put("candidateJobPostId", rs.getString("id"));
			can.put("createdon", rs.getString("createdon"));
			JSONObject vendorjson = vendordao.getReportingto(rs.getString("createdby"));
			can.put("vendorname",vendorjson.optString("reportingname"));

			jsonarr.put(can);
		}
		json.put("mapList", jsonarr);
		} catch(Exception e) {
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
			String sql = "UPDATE candidatejobpostingmapping SET submittedto=?,createdby=?,createdon=? WHERE candidateId=? AND jobpostingId =?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, jsonObject.getString("submittedto"));
			preparedstmt.setString(2, jsonObject.getString("createdby"));
			preparedstmt.setString(3, LocalDateTime.now().toString());
			preparedstmt.setString(4, jsonObject.getString("candidateId"));
			preparedstmt.setString(5, jsonObject.getString("jobpostingId"));
			preparedstmt.executeUpdate();
			jobdao.candidatejobappliedhistory(jsonObject);
		 	json.put("msg","Profile Submitted");
			return json;
		} catch (Exception e) {
			logger.error(e);
			json.put("msg","Profile Submission failed");
			return json;
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		}
	 }
	
	
	public JSONObject profileList(String profile) {
			Connection con = null;
	        PreparedStatement preparedstmt = null;
	        ResultSet rs = null;
	        JSONObject json1 = new JSONObject();
	        JSONArray jsonArr = new JSONArray();
	        try {
	         String sql = "SELECT name,emailid,dob,profile FROM candidate WHERE profile LIKE  ?";
	         con= conn.getConnection();
	         preparedstmt= con.prepareStatement(sql);
	         preparedstmt.setString(1,"%"+profile+"%");
	         rs= preparedstmt.executeQuery();
	         while(rs.next()) {
	        	 JSONObject jsonobj = new JSONObject();
	        	 jsonobj.put("name",rs.getString("name"));
	        	 jsonobj.put("emailid", rs.getString("emailid"));
	        	 d1=ymd.parse(rs.getString("dob"));
	        	 jsonobj.put("dob", dmy.format(d1));
	        	 jsonobj.put("profile", rs.getString("profile"));
	        	 jsonArr.put(jsonobj);
	           }
	         json1.put("profileList", jsonArr);
	        } catch(Exception  e) {
	        	logger.error(e);
	        }finally {
	   			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
	   			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
	   			 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
	   		}
		return json1;
	}
}

