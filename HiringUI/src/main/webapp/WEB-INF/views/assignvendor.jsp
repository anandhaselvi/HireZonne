<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

  
<jsp:include page="/WEB-INF/views/header.jsp" />


<div class="site-section bg-light">
	<div class="container my-5">
		<h3>Vendor</h3>
		<div class="row">
			
			<div class="offset-md-8 col-md-4">
				<div class="input-group">
					<input type="email" class="form-control" id="emailid" placeholder="Enter Email">
					<div class="input-group-append">
						<button class="btn btn-success" type="button" onclick="sendmail()">Invite</button>
					</div>
				</div>
			</div>
		</div>
		<div class="table-responsive mt-4">
			<table class="table table-bordered" id="dataTable" width="100%"
				cellspacing="0">
				<thead>
					<tr>
						<th><input type="checkbox" name="select_all" value="1"
							id="example-select-all"></th>
						<th>VendorName</th>
						<th>EmailId</th>
						<th>Location</th>

					</tr>
				</thead>
				<tbody>
					<c:forEach var="assignvendor" items="${ven}">
						<tr>
							<td><input type="checkbox" value="${assignvendor.vendorId}"></td>
							<td>${assignvendor.vendorname}</td>
							<td>${assignvendor.emailId}</td>
							<td>Chennai</td>
						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
		<div class="row">
		<div class="col-md-8 form-group mt-4">
				<button type="button" class="btn btn-success" id="assignvendor">Assign</button>
				<button type="button" class="btn btn-success">Unassign</button>
			</div>
		</div>
	</div>
</div>


<jsp:include page="/WEB-INF/views/footer.jsp" />

<script>

$(document).ready(function (){
   var table = $('#dataTable').DataTable({
   'columnDefs': [{
         'targets': 0,
         'searchable':false,
         'orderable':false
      }],
      'order': [1, 'asc']
      
   });

   // Handle click on "Select all" control
   $('#example-select-all').on('click', function(){
      // Get all rows with search applied
      var rows = table.rows({ 'search': 'applied' }).nodes();
      // Check/uncheck checkboxes for all rows in the table
      $('input[type="checkbox"]', rows).prop('checked', this.checked);
   });

   // Handle click on checkbox to set state of "Select all" control
   $('#example tbody').on('change', 'input[type="checkbox"]', function(){
      // If checkbox is not checked
      if(!this.checked){
         var el = $('#example-select-all').get(0);
         // If "Select all" control is checked and has 'indeterminate' property
         if(el && el.checked && ('indeterminate' in el)){
            // Set visual state of "Select all" control
            // as 'indeterminate'
            el.indeterminate = true;
         }
      }
   });

   // Handle form submission event
   $('#assignvendor').on('click', function(e){
      var form = this;
	  var vendorId =[];
      // Iterate over all checkboxes in the table
      table.$('input[type="checkbox"]').each(function(){
         // If checkbox doesn't exist in DOM
        // if(!$.contains(document, this)){
            // If checkbox is checked
            if(this.checked){
               // Create a hidden element
			   console.log(this.value);
			   vendorId.push(this.value);
              
            }
         //}
      });
	  vendorUpdate(vendorId);
   });
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

function sendmail(){
 var emailid =	$('#emailid').val();
	 $.ajax({
		 url:"sendmail",
		 type:"POST",
		 async:false,
		 data:{
			emailid:emailid
			 },
		 success:function(response){
	      var obj= JSON.parse(response);
	       toastr.success(obj.msg);
		 }
	 });
	}
function vendorUpdate(vendorId) {
          $.ajax({
                  url:'vendorupdate',
                  type:"POST",
                  async:false,
             	  data: {
                  vendorId:vendorId.toString()
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
</script>
