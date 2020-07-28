package com.hiring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Random;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hiring.db.DBConnection;

public class JobDao implements Job {
	private static Logger logger = Logger.getLogger(JobDao.class);
	private static DBConnection conn = DBConnection.getInstance();
	
	@Override
	public JSONObject jobInsert(JSONObject json) {
		Random rand = new Random(); 
		JSONObject jsonobject = new JSONObject();
		Connection con =null;
		PreparedStatement preparedstmt=null;
		String jobId ="";
		try {
			String sql ="INSERT INTO job(title,jobtype,location,experiencerequired,workauthorization,jobdescription,updatedby,jobId,updatedon)"
					+ "VALUES(?,?,?,?,?,?,?,?,?)";
			con =conn.getConnection();
			preparedstmt=con.prepareStatement(sql);
			preparedstmt.setString(1, json.getString("title"));
			preparedstmt.setString(2, json.getString("jobtype"));
			preparedstmt.setString(3, json.getString("location"));
			preparedstmt.setString(4, json.getString("experiencerequired"));
			preparedstmt.setString(5, json.getString("workauthorization"));
			preparedstmt.setString(6, json.getString("jobdescription"));
			preparedstmt.setString(7, json.getString("updatedby"));
			if(json.getString("role").equalsIgnoreCase("customer")) {
			    int randomnumber = rand.nextInt(1000); 
			    jobId = "JID" +randomnumber;
			}else {
				jobId = json.getString("jobId");
			}
			preparedstmt.setString(8, jobId);
			preparedstmt.setString(9, LocalDateTime.now().toString());
			preparedstmt.executeUpdate();
			jsonobject.put("msg","Job created successfully");
			//return json;
		} catch (SQLException e) {
			logger.error(e);
			jsonobject.put("msg","Job created failed");
		} finally {
		 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
		 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
	   }
     
		return jsonobject;
	}

		public int jobExists(String title) {
			Connection con =null;
			PreparedStatement preparedstmt = null;
			ResultSet rs = null;
			try {
				String sql = "SELECT title FROM job WHERE title=?";
				int counter =0;
				con=conn.getConnection();
				preparedstmt=con.prepareStatement(sql);
				preparedstmt.setString(1, title);
				rs=preparedstmt.executeQuery();
				if (rs.next()) {		
					 counter =1;
				}
				return counter;
				} catch(SQLException e) {
					e.printStackTrace();
					return 0;
			} finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
	  }

	@Override
	public JSONObject jobUpdate(JSONObject jsonObject) {
		JSONObject json = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt= null;
		 try {
			 String sql ="UPDATE job SET title=?,location=?,jobtype=?,experiencerequired=?,keyskillsrequired=?,jobdescription=?,company=? updatedby=?,status=? WHERE jobpostingId=?";
			 con=conn.getConnection();
			 preparedstmt=con.prepareStatement(sql);
			 preparedstmt.setString(1, jsonObject.getString("title"));
			 preparedstmt.setString(2, jsonObject.getString("location"));
			 preparedstmt.setString(3, jsonObject.getString("jobtype"));
			 preparedstmt.setString(4, jsonObject.getString("experiencerequired"));
		   	 preparedstmt.setString(5, jsonObject.getString("keyskillsrequired"));
			 preparedstmt.setString(6, jsonObject.getString("jobdescription"));
			 preparedstmt.setString(7, jsonObject.getString("company"));
			 preparedstmt.setString(8, jsonObject.getString("updatedby"));
			 preparedstmt.setString(9, jsonObject.getString("jobpostingId"));
			 preparedstmt.executeUpdate();
			 json.put("msg", "Job Updated");
			 return json;
		 } catch(SQLException e) {
		 logger.error(e);
		 json.put("msg", "Job Updated failed");
		 return json;
		 } finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 }
        }

	@Override
	public JSONObject fetchJob(String jobpostingId) {
		JSONObject json = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs = null;
		try {
			String sql ="SELECT title,company,location,jobtype,experiencerequired,keyskillsrequired,jobdescription,updatedby FROM job WHERE jobpostingId=?";
			con= conn.getConnection();
			preparedstmt=con.prepareStatement(sql);
			preparedstmt.setString(1, jobpostingId);
			rs=preparedstmt.executeQuery();
			if(rs.next()) {
				json.put("title", rs.getString("title"));
				json.put("company", rs.getString("company"));
				json.put("location", rs.getString("location"));
				json.put("jobtype", rs.getString("jobtype"));
				json.put("experiencerequired",rs.getString("experiencerequired"));
				json.put("keyskillsrequired", rs.getString("keyskillsrequired"));
				json.put("jobdescription", rs.getString("jobdescription"));
				json.put("updatedby", rs.getString("updatedby"));
				
				} 
			}catch (SQLException e) {
				logger.error(e);
			}finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return json;
	}

	@Override
	public JSONObject jobList() {
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarr= new JSONArray();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT DISTINCT jobpostingId,title,location,jobtype,experiencerequired,jobdescription,updatedby,customer,reportingto FROM job "
					+ " LEFT JOIN vendor ON job.updatedby = vendor.userId ";
			con=conn.getConnection();
			stmt=con.createStatement();
			rs= stmt.executeQuery(sql);
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("jobpostingId", rs.getString("jobpostingId"));
				json.put("title",rs.getString("title"));
				json.put("location", rs.getString("location"));
				json.put("jobtype", rs.getString("jobtype"));
				json.put("experiencerequired", rs.getString("experiencerequired"));
				json.put("jobdescription", rs.getString("jobdescription"));
				json.put("updatedby", rs.getString("updatedby"));
				if(rs.getString("customer") != null && rs.getString("reportingto") != null) {
					json.put("submitType", rs.getString("reportingto").equalsIgnoreCase("0") ? "Primaryvendor":"Secondaryvendor");
				}else {
				json.put("submitType", "Customer");
				}
				jsonarr.put(json);
			}
			jsonobj.put("jobList", jsonarr);
		} catch(SQLException e) {
			logger.error(e);
		}finally { 
			if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
			if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonobj;
	}

	@SuppressWarnings("static-access")
	@Override
	public JSONObject candidatemapping(JSONObject jsonobj) {
		JSONObject json = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt= null;
		try {
			String sql = "INSERT INTO candidatejobpostingmapping(candidateid,jobpostingId,submittedto,vendorId,customerId,createdby,createdon)VALUES(?,?,?,?,?,?,?)";
			con=conn.getConnection();
			preparedstmt = con.prepareStatement(sql,preparedstmt.RETURN_GENERATED_KEYS);
			preparedstmt.setString(1, jsonobj.getString("candidateId"));
			preparedstmt.setString(2, jsonobj.getString("jobpostingId"));
			preparedstmt.setString(3, jsonobj.getString("submittedto"));
			preparedstmt.setString(4, jsonobj.getString("vendorId"));
			preparedstmt.setString(5, jsonobj.getString("customerId"));
			preparedstmt.setString(6, jsonobj.getString("createdby"));
			preparedstmt.setString(7, LocalDateTime.now().toString());
			preparedstmt.executeUpdate();
			ResultSet rs = preparedstmt.getGeneratedKeys();
            if(rs != null && rs.next()){
            	jsonobj.put("candidatejobpostId", rs.getInt(1));
            }
			candidatejobappliedhistory(jsonobj);
			json.put("msg", "Applied");
		}catch(SQLException e) {
			e.printStackTrace();
			json.put("msg", "Failed");
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		   }
		return json;
	}	
	
	
	public JSONObject jobRefNoListing(String userId) {
		VendorDao vendordao = new  VendorDao();
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarr= new JSONArray();
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs = null;
		try {
			JSONObject vendorJson = vendordao.getReportingto(userId);
			String updatedby = vendorJson.optString("reportingto").equalsIgnoreCase("0") ?vendorJson.optString("customer"):vendorJson.optString("reportingto");
			String sql = "SELECT title,jobdescription,jobId FROM job WHERE updatedby=?";
			con=conn.getConnection();
			preparedstmt=con.prepareStatement(sql);
			preparedstmt.setString(1, updatedby);
			rs= preparedstmt.executeQuery();
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("title",rs.getString("title"));
				json.put("jobdescription", rs.getString("jobdescription"));
				json.put("jobId", rs.getString("jobId"));
				jsonarr.put(json);
			}
			jsonobj.put("jobList", jsonarr);
		} catch(SQLException e) {
			logger.error(e);
		}finally { 
			if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonobj;
	}
	
	public JSONObject candidatejobappliedhistory(JSONObject jsonobj) {
		JSONObject json = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt= null;
		try {
			String sql = "INSERT INTO candidatejobappliedhistory(candidateid,jobpostingId,submittedto,submittedType,createdBy,createdOn,candidatejobpostId)VALUES(?,?,?,?,?,?,?)";
			con=conn.getConnection();
			preparedstmt=con.prepareStatement(sql);
			preparedstmt.setString(1, jsonobj.getString("candidateId"));
			preparedstmt.setString(2, jsonobj.getString("jobpostingId"));
			preparedstmt.setString(3, jsonobj.getString("submittedto"));
			preparedstmt.setString(4, jsonobj.getString("submittedType"));
			preparedstmt.setString(5, jsonobj.getString("createdby"));
			preparedstmt.setString(6, LocalDateTime.now().toString());
			preparedstmt.setInt(7, jsonobj.getInt("candidatejobpostId"));
			preparedstmt.executeUpdate();
			json.put("msg", "Applied");
		}catch(SQLException e) {
			logger.error(e);
			json.put("msg", "Failed");
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		   }
		return json;
	}	
	
}
