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
        <div class="row">
          <div class="col-md-12 mb-5 mb-md-0" id="candidateDetails">
		   <div class="row p-5 bg-white">
		   		  <h4>Register Your Profile</h4>
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<input type="hidden" id="timesheetId">
								<label for="firstname">Firstname:</label> 
								<input type="text" class="form-control" id="firstname" name="firstname">
						</div>
					</div>
					<div class="col-sm-6">
					<div class="form-group">
						<label for="lastname">Lastname:</label>
						<input type="text" class="form-control" id="lastname"  name="lastname" ></input>
						<span id="comments1" class="text-danger font-weight-bold"></span>
					</div>
				</div>
			</div>
		</div>
			<div class="col-sm-12">
			<div class="row">
			<div class="col-sm-6">
					<div class="form-group">
						<label for="emailid">Email:</label> <input
							class="form-control"  name="emailid"  id="emailid">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label for="password">Password:</label> <input type="password"
							class="form-control" name="regpassword"  id="regpassword">
					</div>
			
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
					<label for="dateofbirth">Date of Birth:</label>
						<input class="form-control" id="dateofbirth" name="date"
							placeholder="MM/DD/YYYY" type="text"  value="03/09/1998" disabled>
					</div>
				</div>
			<div class="col-sm-6">
				<div class="form-group">
					<label class="resumeupload" for="projectname">Resume Upload:</label>
						<input type="file"  class="form-control" name="file" id="file">				
					</div>
			</div>
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-12">
						<div class="form-group">
						<label for="profile">Profile:</label>
							<textarea name="" class="form-control" id="profile" cols="30" rows="4"></textarea>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
	         <button type="submit" class="btn btn-success" onclick="register()">Submit</button>

	</div>
        </div>
      </div>
    </div>
</div>
<!-- candidate registration-->

         <div class="col-md-12 mb-5 mb-md-0" id="vendorDetails">
		 
		<form action="register" method="post">
		   <div class="row p-5 bg-white">
		  <h4>Register Your Profile</h4>

			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<input type="hidden" value="<%= request.getParameter("id") %>" name="customerId">
							<input type="hidden" value="<%= request.getParameter("Vendor") %>" name="vendorId">
								<label for="companydetails">Company Details:</label> 
								<input type="text" class="form-control" id="companydetails" name="companydetails">
						</div>
					</div>
					<div class="col-sm-6">
					<div class="form-group">
						<label for="taxid">TaxID:</label>
						<input type="text" class="form-control" id="taxid"  name="taxid" ></input>
						<span id="comments1" class="text-danger font-weight-bold"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="row">
			<div class="col-sm-6">
					<div class="form-group">
						<label for="address">Address:</label> <input
							class="form-control"  name="address"  id="address">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label for="contact">Contact:</label> <input type="text"
							class="form-control" name="contact"  id="contact">
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
					<label for="name">Firstname:</label>
						<input class="form-control" id="firstname" name="firstname"
							type="text">
					</div>
				</div>
			<div class="col-sm-6">
				<div class="form-group">
					<label for="email">Lastname:</label>
						<input type="text"  class="form-control" name="lastname" id="lastname">				
					</div>
			</div>
			</div>
			</div>
			<div class="col-sm-12">
			<div class="row">
			<div class="col-sm-6">
				<div class="form-group">
					<label for="email">Email:</label>
						<input type="text"  class="form-control" name="email" id="email">				
					</div>
			</div>
				<div class="col-sm-6">
					<div class="form-group">
					<label for="password">Password:</label>
						<input class="form-control" id="password" name="password"
							type="password">
					</div>
				</div>
			
			</div>
			</div>
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
						<label for="phone">Phone:</label>
							<input type="text" name="phone" class="form-control">
					</div>
				</div>
				<div class="col-sm-6">
				<div class="form-group">
					<label  for="insurance">Insurance:</label>
						<input type="text"  class="form-control" name="insurance" id="insurance">				
					</div>
			</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="certification">Certification:</label>
							<input type="text" name="certification" class="form-control">
					</div>
				</div>
			<div class="col-sm-6">
				<div class="form-group">
					<label for="partnership">Partnership:</label>
						<input type="text"  class="form-control" name="partnership" id="partnership">				
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-12">
	         <button type="submit" class="btn btn-success">Submit</button>

	</div>
   </div>
  </div>
 </div>
 
	</form>
</div>
<!---candidate-->
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
	 var queryString = window.location.search;
	 var urlParams = new URLSearchParams(queryString);	
	 // get the required parameter
	 var param = urlParams.get('type'); 
	 if (param != null){
			document.getElementById("candidateDetails").style.display = 'none';
		}if(urlParams.has('type') && param == null){
			document.getElementById("vendorDetails").style.display = 'none';
		}if (param == null){
			document.getElementById("candidateDetails").style.display = 'none';
		}
 });
 

	
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
    				toastr.success("candidate created successfully"); 
    			  }
    			  if(obj.msg=="candidatename already exists"){
    				toastr.success("candidatename already exists");  
    			  }
    			 if(obj.msg=="candidate creation failed"){
    				 toastr.success("candidate creation failed")
    			 }
    			
    		  }
    	  });
    }        
</script>
</body>
</html>