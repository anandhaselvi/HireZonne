<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"%>
 <jsp:include page="/WEB-INF/views/header.jsp" />
    
 <div class="site-section bg-light">

      <div class="container my-5">
          <h3>Candidate Listing</h3>
		<!--   <button type="button" class="btn btn-success">Created Job Listing</button></a>
		  <button type="button" class="btn btn-success">Edit Job Listing</button></div>-->
		
   
	<div class="row">
	<div class="col-md-12 mt-4">
				<div class="row">
				<label class="font-weight-bold pl-3" for="fullname">Profile</label>
				<div class="input-group">
				<div class="col-md-6">
					<input type="profile" class="form-control" id="profile" placeholder=" "></div>
					<div class="col-sm-6">
					<button class="btn btn-success" type="button" onclick="profilecheck();">Search</button>
					</div>
				</div>
			</div>
		</div>
	</div>
		
			
			
            <div class="table-responsive mt-4">
                <table class="table table-bordered" id="dataTable">
                  <thead>
                    <tr>
                      <th width="30%">Name</th>
                      <th>Email</th>
                      <th width="10%">Date Of Birth</th>
                      <th width="50%">Profile</th>
                 <!--     <th>Resume</th>-->  
                       </tr>
                  </thead>
                  <tbody id= "candidatebody" >
                     <c:forEach var="candidate" items="${candidateListing}">
						<tr>
					
						<td>${candidate.name}</td>
						<td>${candidate.emailid}</td>
						<td>${candidate.dob}</td>
						<td>${candidate.profile}</td>
						<!--  <td>${candidate.resume}</td>-->
						</tr>
						</c:forEach>
					 </tbody>    
                </table>
          </div>
   </div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp" />

<script>
$(document).ready(function (){
   var table = $('#dataTable').DataTable({
  searching: false,
  lengthChange: false,
  info:false
      
	  
   });
 });
 
function profilecheck(){
	var profile=$('#profile').val();
	$.ajax({
		url:" profilecheck",
		type:"POST",
		async:true,
		data:{
		  profile:profile
		  
		  },
		success: function(response){
			var obj =JSON.parse(response);
			console.log(obj);
			$('#candidatebody').html('');
			var table='';
			for(var i=0;i<obj.profileList.length;i++){
				table+='<tr><td>'+obj.profileList[i].name+'</td><td>'+obj.profileList[i].emailid+'</td>'
				+'<td>'+obj.profileList[i].dob+'</td><td>'+obj.profileList[i].profile+'</td></tr>';
			}
		$('#candidatebody').append(table);
		/*$('#approvebtn').show();
		$('#rejectbtn').show();*/
		$('#example').show();
		console.log(obj.candidatelist);
		}
	})
}
  
  
 </script>
