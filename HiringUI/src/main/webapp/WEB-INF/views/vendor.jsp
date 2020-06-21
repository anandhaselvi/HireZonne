<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <jsp:include page="/WEB-INF/views/header.jsp" />
    
    <div class="site-section bg-light ">

      <div class="container">
      <form  onsubmit="return false"class="p-5 bg-white mt-5">
          
            
          
            <h3>Vendor</h3>

                 <div class="row">
		<div class="col-sm-12">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
					<input type ="hidden" id="vendorId">
						<label for="usr">Vendor Name:</label> <input type="text"
							class="form-control" id="vendorname" name="code" maxlength="20">
							<span id="code1" class="text-danger font-weight-bold"></span>
							
					</div>
				</div>
				<div class="col-sm-6">
              <div class="form-group">
						<label for="usr">Description:</label> 
                  <textarea name="" class="form-control" id="description" cols="30" rows="3"></textarea>
                </div>
             
			  </div>
			</div>
		</div>
		<div class="col-sm-12">
		<div class="row">
				<div class="col-sm-6">
              <div class="form-group">
						<label for="usr">IsPrimary:</label> 
                    <input type="radio" id="yes"  value="1" name="isprimary"> Yes
					 <input type="radio" id="no" value="0"  name="isprimary"> No

                </div>
             
			  </div>
			  </div>
			  </div>
		<div class="col-sm-12">
		<div class="row">
				<div class="col-sm-6">
              <div class="form-group">
						<label for="usr">Customer:</label> 
                   		
					<select class="form-control" id="Customer" name ="Customer">
					
					
					</select>
					

                </div>
             
			  </div>
			  <div class="col-sm-6">
              <div class="form-group">
						<label for="usr">Reporting To:</label> 
                   	<select class="form-control" id="reportingto" name ="Reportingto">
					</select>
					
                </div>
             
			  </div>
			  </div>
			  </div>			  
	</div>
	<div class="row">
		<div class="col-sm-12">
		<button type="submit" id="addButton"  class="btn btn-success"  onclick="addvendor()"> Add</button>
		<button type="submit" id="updateButton" class="btn btn-success" onclick="vendorUpdate()">Update</button>
		</div>
	</div>

  
            </form>
            </div>

   </div>
<jsp:include page="/WEB-INF/views/footer.jsp" />
<script>
$(document).ready(function(){
	toastr.options = {
	  "closeButton": true,
	  "positionClass": "toast-top-center",
	  "preventDuplicates": false,
	  "showDuration": "300",
	  "hideDuration": "1000",
	  "timeOut": "5000",
	  "extendedTimeOut": "1000"
	};
getCustomer();
getReporting();

} );

function addvendor(){
	var vendorname=$('#vendorname').val();
	var description=$('#description').val();
	var radioValue = $("input[name='isprimary']:checked"). val();
	var customer=$('#Customer').val();
	var reportingto=$('#reportingto').val();
		$.ajax({
			url: 'vendorinsert',
			type: "POST",
			async: false,
			data: {
				vendorname:vendorname,
				description:description,
				isprimary:radioValue,
				customer:customer,
				reportingto:reportingto
				},
			success: function(result) {
				var obj=JSON.parse(result);
				if(obj.msg == "Vendor created successfully"){
					toastr.success("Vendor Created Successfully");
					}
				if(obj.msg == "Vendorname already exists"){
					toastr.warning("Vendorname already exists");
					}
				if(obj.msg == "Vendor created failed"){
					toastr.error("Vendor Creation Failed");
				
				}
			}
			});

}
	function getCustomer() {
			$.ajax({
				url:'getcustomer',
				type:"POST",
				async:false,
				data: {
					
				},
				success: function(result) {
					var obj=JSON.parse(result);
					console.log(obj);
					var customerDrop ='<option value="">Select Customer </option>';
					for(var i=0;i<obj.getCustomer.length;i++){
						customerDrop +='<option value='+obj.getCustomer[i].customerid+'>'+obj.getCustomer[i].companyname+'</option>';
					}
					$('#Customer').append(customerDrop);
				}
			});
		}

		
function getReporting(){
	$.ajax({
		url:'getreporting',
		type:"POST",
		async:false,
		data: {
			
		},
		success: function(result) {
			var obj=JSON.parse(result);
			console.log(obj);
			var reportingrDrop ='<option value="">Select ReportingTo </option>';
			for(var i=0;i<obj.getReporting.length;i++){
				reportingrDrop +='<option value='+obj.getReporting[i].vendorid+'>'+obj.getReporting[i].vendorname+'</option>';
			}
			$('#reportingto').append(reportingrDrop);
		}
	});
}	
		
function vendorUpdate() {
	 var vendorId=$('#vendorId').val();
     var vendorname=$('#vendorname').val();
     var description=$('#description').val();
     var radioValue = $("input[name='isprimary']:checked"). val();
     var customer=$('#Customer').val();
     var reportingto=$('#reportingto').val();
          $.ajax({
                  url:'vendorupdate',
                  type:"POST",
                  async:false,
             data: {
                  vendorId:vendorId,
                  vendorname:vendorname,
                  description:description,
                  isprimary:radioValue,
                  Customer:customer,
                  reportingto:reportingto
                    },
                       success: function(result) {
                           var obj=JSON.parse(result);
                            if(obj.msg == "Vendor Updated"){
                         toastr.success("Vendor Updated Successfully");
                   }
                    if(obj.msg == "Vendor failed"){
	                  toastr.error("Vendor Updation Failed");
                  }
                        $('#vendorname').val("");
                        $('#description').val("");
                         $('#customer').val("");
                        $('#reportingto').val(""); 
	             }
             });
	 
       }		
		
function fetchvendor(id){
	
	$.ajax({
		url: 'fetchlist',
	    type: "POST",
	    async: false,
	    data: { 
	    	vendorId:vendorId
	    },
	    success:function(result){
	    	var obj = JSON.parse(result);
	        console.log(obj);
	        $('#vendorname').val(obj.vendorname);
	        $('#description').val(obj.description);
	        $('#customer').val(obj.customer);
	        $('#reportingto').val(obj.reportingto);
	      
			 }
	    });
	
}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
/*$('#vendorsubmit').validate({
    rules: {
    	vendorname: "required",
    	description: "required",
    	customer:"required",
    	reportingto:"required"
    },
    messages: {
    	vendorname : "Please enter vendorname",
    	description: "Please enter description",
    	customer: "Please enter customer",
    	reportingto:"Please enter reportingto"
    },
    submitHandler: function(form) {
    	addvendor();
    }
  });*/



</script>   
