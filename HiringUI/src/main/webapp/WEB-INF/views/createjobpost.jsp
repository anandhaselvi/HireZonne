<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<style>
.blur{
opacity:0.3;
}
</style>
	<div class="site-section bg-light">
		  <div id= "jobpost" class="container">
		  <!--form id="jobSubmit" onsubmit="return false" class="p-5 bg-white mt-5"!-->
			<div class="row">			
			<div class="col-md-12 col-lg-10  my-5">
			<h3>Job Posting</h3>
			<span id="message" class="text-danger font-weight-bold" style="color:#E2E6E7;">${message}</span>
			
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
						<div class="form-group">
							<input type="hidden" id="timesheetId">
							<label  class="font-weight-bold" for="title">Title:</label> 
							<input type="text" id="title"  name="title" maxlength="20" class="form-control" >

						</div>
					</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label class="font-weight-bold" for="firstname">Location:</label> 					 
						  <input type="text"  id="location"  name="location" maxlength="20" class="form-control">
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<label class="font-weight-bold" for="fullname">Job Type</label>
					<select class="form-control" id="jobtype" name ="jobtype">
						<option>Select Job Type</option>
						<option value="Full Time">Full Time</option>
						<option value="Part Time">Part Time</option>
						<option value="Freelance">Freelance</option>
						<option value="Internship">Internship</option>
					</select>
				</div>
			<div class="col-sm-6">
				<div class="form-group">
					<label class="font-weight-bold" for="startdate">Duration</label>
					
						<input class="form-control" id="dateofbirth" name="date"
							 type="text"  value="12 month" disabled>
					</div>
				</div>
			</div>
		</div>			
		<div class="col-md-12 mt-2">
			<div class="row">
				 <div class="col-md-6">
					<label class="font-weight-bold" for="fullname">Experience Required</label>
						<input type="text" id="experiencerequired" name="experiencerequired" maxlength="10" class="form-control"> 
					</div>
				<div class="col-md-6">
					<label class="font-weight-bold" for="fullname">Work Authorization#</label>
						<input type="text" id="workauthorization" name="workauthorization" maxlength="20" class="form-control">
					</div>
				</div>
	</div>
		 <div class="col-md-12 mt-2">
			<div class="row">
				 <div class="col-md-6">
					<label class="font-weight-bold" for="fullname">Rate</label>
						<input type="text" id="rate" name="rate" maxlength="10" class="form-control"> 
					</div>
				<div class="col-md-6">
					<label class="font-weight-bold" for="fullname">Visa Type</label>
					<select class="form-control" id="visatype" name ="visatype">
						<option>Select Visa Type</option>
						<option value="Full Time">No Visa Sponsor</option>
						<option value="Part Time">Green Card</option>
						<option value="Freelance">H1-Visa</option>
						<option value="Internship">H4- Visa</option>
						<option value="OPT">OPT</option>
						<option value="CPT">CPT</option>
						<option value ="Others">Others</option>
					</select>
				</div>
		</div>
		</div>
		
	<c:if test="${fn:containsIgnoreCase(sessionScope.role, 'vendor')}">
			<div class="col-md-12 mt-4">
				<div class="row">
				<label class="font-weight-bold pl-3" for="fullname">Job Listing Ref#</label>
				<div class="input-group">
				<div class="col-md-6">
					<input type="text" id="jobId" name="jobId" class="form-control" disabled></div>
					<div class="col-sm-6">
					<button data-toggle="modal" data-target="#loginModal" class="btn btn-success" type="button" onclick="searchjobList()">Search</button>
					</div>
				</div>
			</div>
		</div>
	</c:if>
				  
	<div class="col-md-12 mt-4">
			<div class="row">
				<div class="col-md-12">
					<label class="font-weight-bold" for="fullname">Job Description</label>
					  <textarea name="jobdesc" class="form-control" id="jobdesc" cols="30" rows="5" maxlength="300"></textarea>
				</div>
			</div>
	</div>
	

	<div class="col-md-12 mt-5">
			 <div class="row">
				<div class="col-md-12">
					<button type="submit" id="addButton" class="btn btn-success" onclick="addjob();">Post a Job</button>
			</div>
		</div>
	</div>
				
				
 <div class="modal" id="loginModal">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Job List</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
         

		<div class="table-responsive mt-4">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th></th>
						<th>JobId</th>
						<th>Title</th>
						<th>Description</th>

					</tr>
				</thead>
				<tbody id="jobList">
				</tbody>

			</table>
		</div>
        <!-- Modal footer -->
	        <div class="modal-footer">
	         <button type="submit" class="btn btn-success" onclick="getJobRefNo();">Submit</button>
	        </div>
	      </div>
	    </div>
		</div>
		</div>
		</div>
		</div>
				<!--/form-->
	</div>
 </div>
		
<jsp:include page="/WEB-INF/views/footer.jsp" />	  
	<script>
	$(document).ready(function() {
		   toastr.options = {
					  "closeButton": false,
					  "positionClass": "toast-top-center",
					  "preventDuplicates": false,
					  "showDuration": "300",
					  "hideDuration": "1000",
					  "timeOut": "5000",
					  "extendedTimeOut": "1000"
		};	
	var message = "<%= request.getAttribute("message") %>";
	if(message !=""){
		$( "#jobpost" ).addClass( "blur" );

	}
	});
	
	function addjob() {
			var jobId="";
			var title=$('#title').val();
			var location=$('#location').val();
			var jobtype = $("#jobtype").val();
			var experiencerequired=$('#experiencerequired').val();
			var workauthorization=$('#workauthorization').val();
			var jobdescription=$('#jobdesc').val();
			var company=$('#company').val();
			if($("#jobId").val() != undefined){
			jobId = $("#jobId").val();
			}
			var rate=$('#rate').val();
			var visatype=$('#visatype').val();
			$.ajax({
				url:'insertjob.htm',
				type:"POST",
				async:false,
				data: {
					title:title,
					location:location,
					jobtype:jobtype,
					experiencerequired:experiencerequired,
					workauthorization:workauthorization,
					jobdescription:jobdescription,
					jobId:jobId,
					rate: rate,
					visatype: visatype,
				
					},
					success: function(result){
						var obj=JSON.parse(result);
						console.log(obj);
						//obj.alert(msg);
					 if(obj.msg =="Job created successfully"){
							toastr.success("Job Created Successfully");	
					  }
						if(obj.msg == "You cannot post a job"){
							toastr.warning("You cannot Post a Job");
						} 
						if(obj.msg == "Job Creation failed"){
							toastr.error("Job Creation Failed");
							}
						$('#title').val("");
						$('#location').val("");
						$('#jobtype').val("");
						$('#experiencerequired').val("");
						$('workauthorization').val("");
						$('jobdescription').val("");
						$('jobId').val("");
						$('rate').val("");
						$('visatype').val("");
						
					}
		    });
	}

	function searchjobList(){
	$.ajax({
		url: 'searchjobList.htm',
		type:"POST",
		async: false,
		data: {
			
		},
		success: function(result) {
		var obj=JSON.parse(result);
		$('#jobList').html('');
		var jobDrop='';
		for(var i=0;i<obj.jobList.length;i++){
	   		jobDrop +='<tr><td><input type="radio"  value='+obj.jobList[i].jobId+' name="jobId"></td>'
					+'<td>'+obj.jobList[i].jobId+'</td><td>'+obj.jobList[i].title+'</td>' 
					+'<td>'+obj.jobList[i].jobdescription+'</td>'
					+'</tr>'
		}
		$('#jobList').append(jobDrop);
		}
	 });
	}
	 function getJobRefNo(){
		var jobRefNo = $("input[name='jobId']:checked").val();
		if(jobRefNo != undefined){
		$('#loginModal').modal('hide');
		$('#jobId').val(jobRefNo);
		}
	}
	 
	 $('#jobSubmit').validate({
		    rules: {
		    	title:"required",
				location:"required",
				jobtype:"required",
				experiencerequired:"required",
				workauthorization:"required",
				jobdescription:"required",
				jobId:"required",
				rate: "required",
				visatype: "required"
		    },
		    messages: {
		    	title : "Please enter title",
		    	location: "Please enter location",
		    	jobtype: "Please select jobtype",
		    	experiencerequired:"Please enter experience required",
		    	workauthorization: "Please enter workauthorization",
		    	jobdescription:"Please enter jobdescription",
		    	jobId:"Please enter jobId",
		    	rate: "Please enter rate",
		    	visatype:"Please enter visatype"
		    },
		    submitHandler: function(form) {
		    	addjob();
		    }
		  });
		</script>
	</html>   
