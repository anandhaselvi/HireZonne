<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>HireZone</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
 
    <link href="https://fonts.googleapis.com/css?family=Amatic+SC:400,700|Work+Sans:300,400,700" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/fonts/icomoon/style.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/magnific-popup.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/jquery-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/animate.css">

    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/fonts/flaticon/font/flaticon.css">
  
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/aos.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/style.css">
     <link href="${pageContext.request.contextPath}/resources/assets/css/dataTables.bootstrap4.min.css" rel="stylesheet">  
	 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/toastr.css" />

<script src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.js"></script>

  </head>
  <body>
  
  <div class="site-wrap">

    <div class="site-mobile-menu">
      <div class="site-mobile-menu-header">
        <div class="site-mobile-menu-close mt-3">
          <span class="icon-close2 js-menu-toggle"></span>
        </div>
      </div>
      <div class="site-mobile-menu-body"></div>
    </div> <!-- .site-mobile-menu -->
    
    
    <div class="site-navbar-wrap js-site-navbar bg-white">
      
      <div class="container">
        <div class="site-navbar bg-light">
          <div class="py-1">
            <div class="row align-items-center">
              <div class="col-2">
                <h2 class="mb-0 site-logo"><a href="index.html"><strong class="font-weight-bold">HireZone</strong> </a></h2>
              </div>
              <div class="col-10">
                 </div>
			 
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="site-section bg-light ">
      <div class="container">
	   <div class="row align-items-center my-5">
          <div class="col-12">
            <h2>Find Job</h2>
            <form action="#">
			<div class="row">
               <div class="col-md-12">
					<div class="row">
					<div class="col-md-7 ">
						<input type="text" class="col-md-12 mr-3 form-control border-0 px-4" placeholder="job title, keywords or company name ">
                 </div>
                <div class="col-md-3">
                  <input type="submit" class="btn btn-search btn-primary btn-block" value="Search">
                </div>
              </div>
             </div>
             </div>
            </form>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12 mb-5 mb-md-0">
            <h2 class="mb-5 h3">Recent Jobs</h2>
            <div class="rounded border jobs-wrap" id= "job">
			<input type="hidden" id="jobpostingId">
			<input type="hidden" id="customerId" value="0">
			<input type="hidden" id="vendorId" value="0">
			<input type="hidden" id="submitType">
			 </div>
			 <!-- Modal -->
	
<!--Modal-->

 <div class="modal" id="loginModal">
    <div class="modal-dialog modal-lg">
	<form  id ="timesheetSubmit" onsubmit="return false">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Login to share your profile</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
         

		  <div class="row">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-6">
					<div class="form-group">
						<input type="hidden" id="timesheetId">
						<label for="username">Email:</label> <input type="text"
							class="form-control" id="username" name="username" >
					</div>
				</div>
				</div>
				</div>
				<div class="col-sm-12">
				<div class="row">
			<div class="col-sm-6">
				<div class="form-group">
					<label for="password">Password:</label> <input type="password"
						class="form-control" id="password"  name="password" ></input>
				</div>
			</div>
		</div>
		</div>
		<div class="col-sm-12">
				<div class="row">
			<div class="col-sm-6">
				<div class="form-group">
					<button type="submit" class="btn btn-success"onclick="candidatelogin();">Login	</button>
				</div>
			</div>
		</div>
		</div>
	</div>
        <!-- Modal footer -->
        
      </div>
    </div>
	</div>
	</div>
  </form>
  </div>

  </div>	

		</div>
           
        </div>
		
      </div>
      </div>
      <footer class="site-footer">
      <div class="container">
        

        <div class="row">
          <div class="col-md-4">
            <h3 class="mb-4 text-white">HireZone v0.1</h3>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="col-md-10">
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellat quos rem ullam, placeat amet.
					Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellat quos</p>
			 </div>
            </div>
          </div>

          
          <div class="col-md-2">
            <div class="col-md-12">Download from an App store now!</div>
              <div class="col-md-12">
                <p>
                  <span class="icon-apple pb-2 pr-2 pl-0"></span>
                  <span class="icon-android p-2"></span>
                </p>
              </div>
          </div>
        </div>
      </div>
</div>
 </footer>
 <script src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.min.js"></script>
 <script src="${pageContext.request.contextPath}/resources/assets/js/jquery-ui.js"></script>
 <script src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>
 <script src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
 <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.magnific-popup.min.js"></script>
 <script src="${pageContext.request.contextPath}/resources/assets/js/bootstrap-datepicker.min.js"></script>
 <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
 <script src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap4.min.js"></script>
 <script src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>
   <script src="${pageContext.request.contextPath}/resources/assets/js/toastr.js"></script>

<script>
 $(document).ready(function(){
	 getjobList();
	// $('#myModal').modal('hide');
	   toastr.options = {
				  "closeButton": false,
				  "positionClass": "toast-top-center",
				  "preventDuplicates": false,
				  "showDuration": "300",
				  "hideDuration": "1000",
				  "timeOut": "5000",
				  "extendedTimeOut": "1000"
	};	  
 });
 
function getjobList(){
$.ajax({
	url: 'jobList.htm',
	type:"POST",
	async: false,
	data: {
		
	},
	success: function(result) {
	var obj=JSON.parse(result);
	console.log(obj);
	var jobDrop='';
	for(var i=0;i<obj.jobList.length;i++){
   		jobDrop +='<a href="#" class="job-item d-block d-md-flex align-items-center freelance"><div class="job-details h-100">'
		  +'<div class="p-3 align-self-center">'
  		  +'<h3>'+obj.jobList[i].title+'</h3>' 
	      +'<div class="d-block d-lg-flex">'
 	  	  +'<div class="mr-3"><span class="icon-suitcase mr-1"></span>CTS</div>'
  		  +'<div class="mr-3"><span class="icon-room mr-1"></span>'+obj.jobList[i].location+'</div>'
		  +'<div><span class="icon-clock-o"></span> just a min</div>'
  	      +'</div>'
		  +'<div class="form-group">'+obj.jobList[i].jobdescription+'</div>'
		  +'</div>'
		  +'<div class="job-category align-self-center">'
		  +'<div class="p-3"><button type="submit" data-toggle="modal" data-target="#loginModal" class="btn btn-success" onclick=setId("'+obj.jobList[i].updatedby+'","'+obj.jobList[i].jobpostingId+'","'+obj.jobList[i].submitType+'");>Apply</button>'

		 // +'<div class="p-3"><button type="submit" data-toggle="modal" data-target="#loginModal" class="btn btn-success" onclick=getstatus("'+obj.jobList[i].updatedby+'","'+obj.jobList[i].jobpostingId+'");>Apply</button>'
		  +'</div></div>'
		  +'</a>'
	}
	$('#job').append(jobDrop);
	}
 });
}


function setId(updatedby,jobpostingId,submitType){
	if(submitType.toLowerCase() =="customer"){
			 $('#customerId').val(updatedby);
	}else{
	 $('#vendorId').val(updatedby);
	}
	 $('#jobpostingId').val(jobpostingId);
	 $('#submitType').val(submitType);
	}

	function candidatelogin(){
		var username = $('#username').val();
		var password = $('#password').val();
		$.ajax({
			url:'candidatelogin.htm',
			type:"POST",
			async:false,
			data: {
				username:username,
				password: password
			},
			success: function(result) {
				var obj=JSON.parse(result);
				if(obj.msg == "username not found"){
					//$('#loginModal').modal('hide');
					//$('#myModal').modal('show');
					window.location="register";
				}else if(obj.msg == "login successfully"){
					$('#loginModal').modal('hide');
					 var customerId = $('#customerId').val();
					 var vendorId = $('#vendorId').val();
					 var jobpostingId = $('#jobpostingId').val();
					 var submitType = $('#submitType').val();
					 getstatus(customerId,vendorId,jobpostingId,submitType);
				}
			}
		});
	}

	function getstatus(customerId,vendorId,jobpostingId,submitType){
		$.ajax({
			url:'getstatus.htm',
			type:"POST",
			async:false,
			data: {
				vendorId:vendorId,
				jobpostingId: jobpostingId,
				submitType:submitType,
				customerId:customerId
			},
			success: function(result) {
				var obj=JSON.parse(result);
				if(obj.msg == "Failed"){
					//$('#myModal').modal('show');
				}
				toastr.success(obj.msg);
			}	
		});
	}
	
  function register(){
    	  var firstname=$('#firstname').val();
		  var lastname=$('#lastname').val();
    	  var emailid=$('#emailid').val();
		  var password=$('#regpassword').val();
    	  var dob=$('#dateofbirth').val();
    	  var profile=$('#profile').val();
		  var file = $('#file')[0].files[0];
		  var formData = new FormData();
			formData.append('file', file);
			formData.append('firstname', firstname);
			formData.append('lastname', lastname);
			formData.append('emailid', emailid);
			formData.append('dob', dob);
			formData.append('profile', profile);
			formData.append('password', password);
    	  $.ajax({
    		  url:'addcandidate.htm',
    		  type:"POST",
			  data:formData,
			  contentType: false,
			  processData: false,
    		  success:function(result){
    			  var obj=JSON.parse(result);
    			  if(obj.msg=="candidate created successfully" ){
    				toastr.success("Registration successful"); 
    			  }
    			  if(obj.msg=="name already exists"){
    				toastr.warning("Name already exists");  
    			  }
    			  if(obj.msg=="candidate creation failed"){
    				 toastr.error("Candidate creation failed")
    			  }
    		  }
    	  });
    }        
</script>
</body>
</html>
