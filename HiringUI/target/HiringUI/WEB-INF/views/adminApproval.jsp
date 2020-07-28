<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
<div class="site-section bg-light">
	<div class="container my-5">
		<h3>Vendor</h3>
		
		<div class="table-responsive mt-4">
			<table class="table table-bordered" id="dataTable" width="100%"
				cellspacing="0">
				<thead>
					<tr>
						<th>VendorName</th>
						<th>Location</th>
						<th>EmailId</th>
						<th>Phone</th>
						<th>Company</th>
						<th>Status</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
						<tr>
							<td>Vendor</td>
							<td>Chennai</td>
							<td>vendor@hirezone.com</td>
							<td>898798786088</td>
							<td>LT pvt limited</td>
							<td>UnApproved</td>
							<td><button class="btn btn-success">Approve</button>&nbsp;<button class="btn btn-danger">Reject</button>
						</tr>
						<tr>
							<td>SecondaryVendor</td>
							<td>Chennai</td>
							<td>secondaryvendor@hirezone.com</td>
							<td>898798786088</td>
							<td>LT pvt limited</td>
							<td>UnApproved</td>
							<td><button class="btn btn-success">Approve</button>&nbsp;<button class="btn btn-danger">Reject</button>
						</tr>
						<tr>
							<td>tertiaryVendor</td>
							<td>Chennai</td>
							<td>vendor12@hirezone.com</td>
							<td>898798786088</td>
							<td>LT pvt limited</td>
							<td>UnApproved</td>
							<td><button class="btn btn-success">Approve</button>&nbsp;<button class="btn btn-danger">Reject</button>
						</tr>
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
</script>
