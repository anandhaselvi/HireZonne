<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:include page="/WEB-INF/views/header.jsp" />
	<div class="site-section bg-light">
			<div class="container my-5">
				   <h3>Candidate</h3>
					  <div class="row">
				 
					</div>
		          <div class="table-responsive">
		                <table class="table table-bordered" id="dataTable">
		                  <thead>
		                    <tr>
							  <th><input type="checkbox" name="select_all" value="1" id="example-select-all"></th>
		                      <th>Candidate</th>
		                      <c:if test="${fn:containsIgnoreCase(sessionScope.role, 'vendor')}">
		                      <th>Vendor</th>
		                      </c:if>
		                      <th>Created Date </th>
		                   </tr>
		                </thead>		
		               <tbody>
		                  <c:forEach var="reviewcandidate" items="${candidate}">
							<tr>
						<td><input type="checkbox" value="${reviewcandidate.submittedto},${reviewcandidate.jobPostingId},${reviewcandidate.candidateid},${reviewcandidate.submittedType},${reviewcandidate.candidateJobPostId}"></td>	
						<td><a href="#">${reviewcandidate.name}</a></td>
						 <c:if test="${fn:containsIgnoreCase(sessionScope.role, 'vendor')}">
						<td>${reviewcandidate.vendorname}</td>
						</c:if>
						<td>${reviewcandidate.createdon}</td>
						</tr>
						</c:forEach>
					 </tbody>    
		          </table>
		        </div>
				<div class="row">
				 <div class="col-md-8 form-group">
				  		<c:if test="${fn:containsIgnoreCase(sessionScope.role, 'vendor')}">
							<button type="button" class="btn btn-success" id="submitprofile">Submitprofile</button>
						</c:if>
						</div>
				</div>
		  </div>
	</div>
<jsp:include page="/WEB-INF/views/footer.jsp" />
<script src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap4.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/js/toastr.js"></script>

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
   $('#submitprofile').on('click', function(e){
      var form = this;
	  var ids =[];
      // Iterate over all checkboxes in the table
      table.$('input[type="checkbox"]').each(function(){
         // If checkbox doesn't exist in DOM
        // if(!$.contains(document, this)){
            // If checkbox is checked
            if(this.checked){
               // Create a hidden element
			   
			  // vendorId.push(this.value);
			   Ids= this.value.split(',');
               candidateProfileSubmit(Ids[0],Ids[1],Ids[2],Ids[3],Ids[4]);
            }
         //}
      });
	 
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
  
function candidateProfileSubmit(submittedto,jobpostingId,candidateId,submitType,candidateJobPostId) {
          $.ajax({
                  url:'candidateProfileSubmit',
                  type:"POST",
                  async:false,
             	  data: {
					  submittedto:submittedto,
					  candidateId:candidateId,
					  jobpostingId:jobpostingId,
					  submitType:submitType,
					  candidateJobPostId:candidateJobPostId
                    },
                    success: function(result) {
                        var obj=JSON.parse(result);
						if(obj.msg == "Profile Submitted"){
							toastr.success("Profile Submitted"); 
						}
						if(obj.msg == "Profile Submission failed"){
							toastr.error("Failed"); 
						}
						location.reload();
	             }

             });
    }
</script>
