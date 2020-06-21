package com.hiring.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		int counter = Nameexists(json.getString("firstname")+" "+json.getString("lastname"));
		if(counter>0){
		jsonObject.put("msg","name already exists");
		}
		else{
		Connection con =null;
	    PreparedStatement preparedstmt = null;
		try{
			String sql="Insert into candidate(name,emailid,dob,profile,userid,resume)values(?,?,?,?,?,?)";
		    con=conn.getConnection();
		     preparedstmt = con.prepareStatement(sql);
		     preparedstmt.setString(1,json.getString("firstname")+" "+json.getString("lastname"));
		     preparedstmt.setString(2,json.getString("emailid"));
		     d1 = dmy.parse(json.getString("dob"));
		     preparedstmt.setString(3, ymd.format(d1));
		     preparedstmt.setString(4,json.getString("profile"));
		     preparedstmt.setString(5, json.getString("userId"));
		     preparedstmt.setString(6, json.getString("resumeupload"));
		     preparedstmt.executeUpdate();
		     jsonObject.put("msg","candidate created successfully");
		     }catch(SQLException| ParseException e) {
				//logger.error(e);
		    	 e.printStackTrace();
				 jsonObject.put("msg","candidate creation failed");
		     }
			finally {
				if (con != null) { try { con.close(); } catch (SQLException e){}}
				if (preparedstmt != null) { try {preparedstmt.close();} catch (SQLException e){}}
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
	Connection con = null;
	PreparedStatement preparedstmt = null;
	ResultSet rs = null;
	try {
		String sql ="SELECT distinct candidate.name,candidate.emailid,candidate.dob,candidate.profile,"
				+ "cjp.candidateId,cjp.jobpostingId,cjp.submittedto,submittedType,candidate.resume "
				+ "from candidate INNER JOIN candidatejobpostingmapping cjp ON "
				+ "candidate.userid= cjp.candidateId INNER JOIN candidatejobappliedhistory cih ON "
				+ "cih.candidatejobpostId=cjp.id WHERE cjp.submittedto=?";
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
			can.put("submittedto", rs.getInt("submittedto"));
			can.put("submittedType", rs.getString("submittedType"));
			can.put("resume", rs.getString("resume"));
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
			String sql = "UPDATE candidatejobpostingmapping SET submittedto=? where candidateId=? AND jobpostingId =?";
			con = conn.getConnection();
			preparedstmt = con.prepareStatement(sql);
			preparedstmt.setString(1, jsonObject.getString("submittedto"));
			preparedstmt.setString(2, jsonObject.getString("candidateId"));
			preparedstmt.setString(3, jsonObject.getString("jobpostingId"));
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
}

