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
				<div class="col-sm-8">
					<div class="form-group">
						<label for="usr">Company Name:</label> <input type="text"
							class="form-control" id="companyname" name="code" maxlength="20">
							<span id="code1" class="text-danger font-weight-bold"></span>
							
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
		<div class="row">
				<div class="col-sm-8">
              <div class="form-group">
						<label for="usr">Description:</label> 
                  <textarea name="" class="form-control" id="description" cols="30" rows="5"></textarea>
                </div>
             
			  </div>
			  </div>
			  </div>		
	</div>
	<div class="row">
		<div class="col-sm-12">
		<button type="submit" id="addButton" class="btn btn-success" onClick="Addcustomer();" >Add</button>
		
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
</script>
</body>
</html>