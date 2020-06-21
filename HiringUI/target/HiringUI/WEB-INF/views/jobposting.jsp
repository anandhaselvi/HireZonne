<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"%>
 <jsp:include page="/WEB-INF/views/header.jsp" />
    
 <div class="site-section bg-light">

      <div class="container my-5">
          <h3>Job Posting</h3>
		 <div class="row"> <div class="col-md-8 form-group"><a href="createjobpost">
		 <button type="button" class="btn btn-success">Created Job Listing</button></a>
		  <button type="button" class="btn btn-success">Edit Job Listing</button></div>
		
			</div>
            <div class="table-responsive mt-4">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
					   <th><input type="checkbox" name="select_all" value="1" id="example-select-all"></th>
                      <th>JobListing</th>
                      <th>Location</th>
                      <th>JobType</th>
                      <th>ExperienceRequired</th>
                      <th>KeySkillsRequired</th>
                      <th>JobDescription</th>
                    </tr>
                  </thead>
                  <tbody>
                     <c:forEach var="job" items="${jobListing}">
						<tr>
						<td><input type="checkbox" value=""></td>	
						<td>${job.title}</td>
						<td>${job.location}</td>
						<td>${job.jobtype}</td>
						<td>${job.experiencerequired}</td>
						<td>${job.keyskillsrequired}</td>
						<td>${job.jobbdescription}</td>
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
               candidateProfileSubmit(Ids[0],Ids[1],Ids[2]);
            }
         //}
      });
   });                         
 });
  
  
 </script>