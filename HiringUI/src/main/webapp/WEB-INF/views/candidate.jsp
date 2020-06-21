<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="/WEB-INF/views/header.jsp" />

        <div class="site-section bg-light">

      <div class="container">
        <div class="row">
       
          <div class="col-md-12 col-lg-8  my-5">
          
            
          
             <h3>Candidate</h3>

              <div class="row form-group">
                <div class="col-md-12 mb-3 mb-md-0">
                  <label class="font-weight-bold" for="fullname">Name</label>
                  <input type="text" id="name" class="form-control" >
                </div>
              </div>

              <div class="row form-group mb-5">
                <div class="col-md-12 mb-3 mb-md-0">
                  <label class="font-weight-bold" for="fullname">EmailID</label>
                  <input type="text" id="emailid" class="form-control">
                </div>
              </div>


             

              <div class="row form-group mb-4">
                <div class="col-md-12 mb-3 mb-md-0">
				      <label class="font-weight-bold" for="fullname">Date of Birth</label>

                  <input type="text" class="form-control" id="dob">
                </div>
              </div>

              <div class="row form-group">
                <div class="col-md-12 mb-3 mb-md-0">
					<label class="font-weight-bold" for="fullname">Profile</label>
                  <textarea name="" class="form-control" id="profile" cols="30" rows="5"></textarea>
                </div>
              </div>
 <div class="container my-4">
  				<div class="row">
					<div class="col-sm-12">
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
								<label class="font-weight-bold" for="projectname">Resume Upload:</label>
								<input type="file"  class="form-control" name="file" id="file">
							</div>
						</div>
					</div>
				</div>
			</div>
			
              <div class="row form-group">
                <div class="col-md-12">
                  <input type="submit" value="Post a Job" class="btn btn-primary  py-2 px-5" onclick="addcandidate();">
                </div>
              </div>

  
          </div>

         
        </div>
      </div>
     </div> 
      <jsp:include page="/WEB-INF/views/footer.jsp" />

<script>
$(document).ready(function(){
   
      });      
      
   function addcandidate(){
    	  var name=$('#name').val();
    	  var emailid=$('#emailid').val();
    	  var dob=$('#dob').val();
    	  var profile=$('#profile').val();
		  var file = $('#file')[0].files[0];
			var formData = new FormData();
			formData.append('file', file);
			formData.append('name', name);
			formData.append('emailid', emailid);
			formData.append('dob', dob);
			formData.append('profile', profile);

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