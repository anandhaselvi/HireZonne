<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<div class="site-section bg-light">
	<div class="container my-5">
		<h3>Vendor</h3>
		
		<div class="table-responsive mt-4">
			<table class="table table-bordered" id="dataTable">
				<thead>
					<tr>
					<th><input type="checkbox" name="select_all" value="1"
							id="example-select-all"></th>
						<th>VendorName</th>
						<th>EmailId</th>
						<th>Phone</th>
						<th>Company</th>
						<th>Status</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
						<c:forEach var="adminApproval" items="${ven}">
						<tr>
							<td><input type="checkbox" value="${adminApproval.vendorId}"></td>
							<td>${adminApproval.vendorname}</td>
							<td>${adminApproval.emailId}</td>
							<td>${adminApproval.phonenumber}</td>
							<td>${adminApproval.companydetails}</td>
							<td>${adminApproval.status}</td>
							<td><button class="btn btn-success" onclick="vendorUpdate(${adminApproval.vendorId})">Approve</button>&nbsp;
							<button class="btn btn-danger" onclick="send('Declined',${adminApproval.vendorId})" >Reject</button>&nbsp;
							<button class="btn btn-info" onclick="send('SeekMoreInformation',${adminApproval.vendorId})" >Seek More Information</button>
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
   columnDefs: [{
         'targets': 6,
         'searchable':false,
         'orderable':false

      }],
   });
   vendorUpdate();
   
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
function vendorUpdate(vendorId) {
	// var vendorId=<%= request.getParameter("vendorId")%>
	// var status =	$('#status').val();
    $.ajax({
            url:'vendorupdate',
            type:"POST",
            async:false,
       	  data: {
            vendorId:vendorId
                 },
              success: function(result) {
                  var obj=JSON.parse(result);
              if(obj.msg == "Vendor Updated"){
                toastr.success("Vendor Updated Successfully");
             }
              if(obj.msg == "Vendor failed"){
                toastr.error("Vendor Updation Failed");
            }
            
           }
       });
    }
function send(submitType,vendorId){
	 $.ajax({
		 url:"send",
		 type:"POST",
		 async:false,
		 data:{
		     vendorId:vendorId,
			 submitType:submitType
			 },
		 success:function(response){
	      var obj= JSON.parse(response);
	      console.log(obj);
	      if(obj.msg == "Sent message successfully"){
              toastr.success("Sent message successfully");
          }
	      if(obj.msg == "Message sent failed"){
              toastr.error("Messaage sent Failed");
          }
 		}
	 });
	}
</script>
