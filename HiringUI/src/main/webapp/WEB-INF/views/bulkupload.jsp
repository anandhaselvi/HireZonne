<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<jsp:include page="/WEB-INF/views/header.jsp" />
 
<div class="site-section bg-light">
    <div class="container">
         <form action="#" class="p-5 bg-white mt-5">
             <h3>Customer</h3>
	       <div class="row">
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-6">
				<div class="form-group">
					<label class="candidateupload" for="projectname">Candidate Upload:</label>
						<input type="file"  class="form-control" name="file" id="file">				
					</div>
			</div>
				</div>
			</div>
			
		</div>
		<div class="row">
			<div class="col-sm-12">
			<button type="submit" id="addButton" class="btn btn-success" onClick="register();" >Upload</button>
			</div>
		</div>
  </form>
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
});
function Addcustomer(){
var companyname=$('#companyname').val();
var description=$('#description').val();

$.ajax({
    url: 'custinsert.htm',
    type: "POST",
    async: false,
    data: {
	companyname:companyname,
	description:description
    },
  success: function(result) {
		var obj= JSON.parse(result);
		console.log(obj);
		if(obj.msg == "Customer Created successfully"){
		toastr.success("Customer Created Successfully");
		}
		if(obj.msg == "already exists"){
		toastr.warning("Customer already exists");
		}
		if(obj.msg == "Customer Creation failed"){
		toastr.error("Customer Creation Failed");
		}
		}
});
}

function register(){
	  var file = $('#file')[0].files[0];
	  var formData = new FormData();
		formData.append('file', file);

	  $.ajax({
		  url:'candidatebulkupload.htm',
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