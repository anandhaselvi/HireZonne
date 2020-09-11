package com.hiring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hiring.db.DBConnection;

public class JobDao implements Job {
	private static Logger logger = Logger.getLogger(JobDao.class);
	private static DBConnection conn = DBConnection.getInstance();
	private static  Random rand = new Random(); 
	public JSONObject jobInsert(JSONObject json) {
       
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArr = json.getJSONArray("jobList");
		 for(int i=0;i<jsonArr.length();i++) {
	          JSONObject jobJson = jsonArr.getJSONObject(i);
	          VendorDao ven= new VendorDao();
	          int vendorId=  ven.checkStatus("Unapproved", jobJson.getString("updatedby"));
	          if(vendorId > 0){
	        	  jsonObject.put("msg", "You should not Post a Job");
		} 
		else {
		Connection con =null;
		PreparedStatement preparedstmt=null;
		String jobId ="";
		try {
			String sql ="INSERT INTO job(title,jobtype,location,experiencerequired,workauthorization,jobdescription,updatedby,jobId,updatedon,rate,visatype)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			con =conn.getConnection();
			preparedstmt=con.prepareStatement(sql);
			preparedstmt.setString(1, jobJson.getString("title"));
			preparedstmt.setString(2, jobJson.getString("jobtype"));
			preparedstmt.setString(3, jobJson.getString("location"));
			preparedstmt.setString(4, jobJson.getString("experiencerequired"));
			preparedstmt.setString(5, jobJson.getString("workauthorization"));
			preparedstmt.setString(6, jobJson.getString("jobdescription"));
			preparedstmt.setString(7, jobJson.getString("updatedby"));
			if(jobJson.getString("role").equalsIgnoreCase("customer")) {
			    int randomnumber = rand.nextInt(1000); 
			    jobId = "JID" +randomnumber;
			}else {
				jobId = jobJson.getString("jobId");
			}
			preparedstmt.setString(8, jobId);
			preparedstmt.setString(9, LocalDateTime.now().toString());
			preparedstmt.setString(10, jobJson.getString("rate"));
			preparedstmt.setString(11, jobJson.getString("visatype"));
			preparedstmt.executeUpdate();
			jsonObject.put("msg","Job created successfully");
			//return json;
		} catch (Exception e) {
			logger.error(e);
			jsonObject.put("msg","Job created failed");
		} finally {
		 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
		 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
	   }
		}
		 }
		return jsonObject;
	}

	public JSONObject jobUpdate(JSONObject jsonObject) {
		JSONObject json = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt= null;
		 try {
			 String sql ="UPDATE job SET title=?,location=?,jobtype=?,experiencerequired=?,jobdescription=?,workauthorization=?,updatedby=?,jobId=?,"
			 		+ "updatedon=?,rate=?,visatype=? WHERE jobpostingId=?";
			 con=conn.getConnection();
			 preparedstmt=con.prepareStatement(sql);
			 preparedstmt.setString(1, jsonObject.getString("title"));
			 preparedstmt.setString(2, jsonObject.getString("location"));
			 preparedstmt.setString(3, jsonObject.getString("jobtype"));
			 preparedstmt.setString(4, jsonObject.getString("experiencerequired"));
			 preparedstmt.setString(5, jsonObject.getString("jobdescription"));
			 preparedstmt.setString(6, jsonObject.getString("workauthorization"));
			 preparedstmt.setString(7, jsonObject.getString("updatedby"));
			 preparedstmt.setString(8, jsonObject.getString("jobId"));
			 preparedstmt.setString(9,  LocalDateTime.now().toString());
			 preparedstmt.setString(10, jsonObject.getString("rate"));
			 preparedstmt.setString(11, jsonObject.getString("visatype"));
			 preparedstmt.setString(12, jsonObject.getString("jobpostingId"));
			 preparedstmt.executeUpdate();
			 json.put("msg", "Job Updated");
			 return json;
		 	} catch(Exception e) {
		 		logger.error(e);
		 		json.put("msg", "Job Updated failed");
		 		return json;
		 	} finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 }
        }

	public JSONObject fetchJob(String jobpostingId) {
		JSONObject json = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs = null;
		try {
			String sql ="SELECT title,location,jobtype,experiencerequired,jobdescription,updatedby FROM job WHERE jobpostingId=?";
			con= conn.getConnection();
			preparedstmt=con.prepareStatement(sql);
			preparedstmt.setString(1, jobpostingId);
			rs=preparedstmt.executeQuery();
			if(rs.next()) {
				json.put("title", rs.getString("title"));
				json.put("location", rs.getString("location"));
				json.put("jobtype", rs.getString("jobtype"));
				json.put("experiencerequired",rs.getString("experiencerequired"));
				json.put("jobdescription", rs.getString("jobdescription"));
				json.put("updatedby", rs.getString("updatedby"));
				} 
			}catch (Exception e) {
				logger.error(e);
			}finally {
				 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
				 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
				 if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return json;
	}

	public JSONObject jobList() {
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarr= new JSONArray();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT DISTINCT jobpostingId,title,job.location,jobtype,experiencerequired,jobdescription,updatedby,customer,reportingto FROM job "
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
		} catch(Exception e) {
			logger.error(e);
		}finally { 
			if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			if (stmt != null) { try { stmt.close();} catch (SQLException e){logger.error(e);}}
			if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonobj;
	}

	public JSONObject candidateMapping(JSONObject jsonobj) {
		JSONObject json = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt= null;
		ResultSet rs = null;
		try {
			String sql = "INSERT INTO candidatejobpostingmapping(candidateid,jobpostingId,submittedto,vendorId,customerId,createdby,createdon)VALUES(?,?,?,?,?,?,?)";
			con=conn.getConnection();
			preparedstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			preparedstmt.setString(1, jsonobj.getString("candidateId"));
			preparedstmt.setString(2, jsonobj.getString("jobpostingId"));
			preparedstmt.setString(3, jsonobj.getString("submittedto"));
			preparedstmt.setString(4, jsonobj.getString("vendorId"));
			preparedstmt.setString(5, jsonobj.getString("customerId"));
			preparedstmt.setString(6, jsonobj.getString("createdby"));
			preparedstmt.setString(7, LocalDateTime.now().toString());
			preparedstmt.executeUpdate();
			rs = preparedstmt.getGeneratedKeys();
            if(rs != null && rs.next()){
            	jsonobj.put("candidatejobpostId", rs.getInt(1));
            }
			candidatejobappliedhistory(jsonobj);
			json.put("msg", "Applied");
		}catch(Exception e) {
			logger.error(e);
			json.put("msg", "Failed");
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			 if (rs != null) { try { rs.close();} catch (SQLException e){logger.error(e);}}

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
		} catch(Exception e) {
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
		}catch(Exception e) {
			logger.error(e);
			json.put("msg", "Failed");
		}finally {
			 if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			 if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
		   }
		return json;
	}	
	
	
	public JSONObject vendorCategory(String userId) {
		JSONObject jsonobj = new JSONObject();
		Connection con = null;
		PreparedStatement preparedstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT COUNT(*) AS count,category,userId,users.updatedon FROM vendor " 
						+ "INNER JOIN job ON job.updatedby = vendor.userId " 
						+ "INNER JOIN users ON users.id = vendor.userId  WHERE userId =?"; 
			con=conn.getConnection();
			preparedstmt=con.prepareStatement(sql);
			preparedstmt.setString(1, userId);
			rs= preparedstmt.executeQuery();
			String message="";
			SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			 Date localDate = new Date();  
			 String localformat = dtf.format(localDate);
			 Date d1 = dtf.parse(localformat);
			if(rs.next()) {
				Date d2 = dtf.parse(rs.getString("updatedon"));
				System.out.println(d2);
				if(rs.getString("category").equalsIgnoreCase("limited")) {
					if(d1.after(d2) || rs.getInt("count") == 1) {
						 message ="You cannot able to job post your subscription date is expired!";
					}
				}else if(rs.getString("category").equalsIgnoreCase("gold")) {
					if(d1.after(d2) || rs.getInt("count") == 10) {
						message ="You cannot able to job post your subscription date is expired!";
					}
				}else if(rs.getString("category").equalsIgnoreCase("silver")) {
					if(d1.after(d2) || rs.getInt("count")== 5) {
						message="You cannot able to job post your subscription date is expired!";
					}
				}else if(rs.getString("category").equalsIgnoreCase("platinum")) {
					if(d1.after(d2) || rs.getInt("count") == 100) {
						message="You cannot able to job post your subscription date is expired!";
					}
				}
			}
			jsonobj.put("message", message);
		} catch(Exception e) {
			logger.error(e);
		}finally { 
			if (con != null) { try { con.close(); } catch (SQLException e){logger.error(e);}}
			if (preparedstmt != null) { try { preparedstmt.close();} catch (SQLException e){logger.error(e);}}
			if (rs != null) { try { rs.close(); } catch (SQLException e) {logger.error(e);}}
		}
		return jsonobj;
	}
}
