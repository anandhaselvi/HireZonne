<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

  
<jsp:include page="/WEB-INF/views/header.jsp" />
 

        <div class="site-section bg-light">

      <div class="container my-5">
          <h3>Vendor</h3>
		 <div class="row"> <div class="col-md-8 form-group"><button type="button" class="btn btn-success">Assign</button>
		  <button type="button" class="btn btn-success">UnAssign</button></div>
		  <div class="col-md-4"> <div class="input-group">
    <input type="text" class="form-control" placeholder="Enter Email">
    <div class="input-group-append">
      <button class="btn btn-success" type="button">
        Invite
      </button>
    </div>
  </div>
</div></div>
            <div class="table-responsive mt-4">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
					         <th><input type="checkbox" name="select_all" value="1" id="example-select-all"></th>

                      <th>Vendor</th>
                      <th>Description</th>
                      <th>isprimary</th>
                      <th>Customer</th>
					  <th>ReportingTo</th>

                    </tr>
                  </thead>
                 
                  <tbody>
                    <tr>
					<td><input type="checkbox" ></td>
                      <td>Candidate1</td>
                      <td>primaryvendor</td>
                      <td>Java developer</td>
                      <td>3years</td><td>primaryvendor</td>
					  
                    </tr>
                   
                   
                   <tr>
				   					<td><input type="checkbox" ></td>

                      <td>Candidate2</td>
                      <td>secondary vendor</td>
                      <td>.Net developer</td>
                      <td>2years</td><td>primaryvendor</td>
                    </tr>
                    <tr>
										<td><input type="checkbox" ></td>

                      <td>Candidate3</td>
                      <td>Direct submission</td>
                      <td>ReactJs</td>
                      <td>2years</td><td>primaryvendor</td>
                    </tr>
                   <tr>
				   					<td><input type="checkbox" ></td>

                      <td>Candidate2</td>
                      <td>secondary vendor</td>
                      <td>.Net developer</td>
                      <td>2years</td><td>primaryvendor</td>
                    </tr>
                    <tr>
										<td><input type="checkbox" ></td>

                      <td>Candidate2</td>
                      <td>secondary vendor</td>
                      <td>.Net developer</td>
                      <td>2years</td><td>primaryvendor</td>
                    </tr>
                    <tr>
										<td><input type="checkbox" ></td>

                      <td>Candidate6</td>
                      <td>secondary vendor</td>
                      <td>UX/UI developer</td>
                      <td>2years</td><td>primaryvendor</td>
                    </tr>
										<td><input type="checkbox" ></td>

                      <td>Candidate2</td>
                      <td>secondary vendor</td>
                      <td>.Net developer</td>
                      <td>2years</td><td>primaryvendor</td>
                    </tr>
                    <tr>
										<td><input type="checkbox" ></td>

                      <td>Candidate</td>
                      <td>secondary vendor</td>
                      <td>.Net developer</td>
                      <td>2years</td><td>primaryvendor</td>
                    </tr>
                    <tr>
										<td><input type="checkbox" ></td>

                      <td>Candidate2</td>
                      <td>secondary vendor</td>
                      <td>.Net developer</td>
                      <td>2years</td><td>primaryvendor</td>
                    </tr>
                  <tr>
				  					<td><input type="checkbox" ></td>

                      <td>Candidate2</td>
                      <td>secondary vendor</td>
                      <td>.Net developer</td>
                      <td>2years</td><td>primaryvendor</td>
                    </tr>
                   
                   
                   
                    <tr>
										<td><input type="checkbox" ></td>

                      <td>Donna Snider</td>
                      <td>Customer Support</td>
                      <td>New York</td>
                      <td>27</td><td>primaryvendor</td>
                    </tr>
                  </tbody>
                </table>
          </div>

   </div>
</div>
    

<jsp:include page="/WEB-INF/views/footer.jsp" />

  		<script>
/*&$(document).ready(function() {
    $('#dataTable').DataTable();
    
} );*/
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
   $('#frm-example').on('submit', function(e){
      var form = this;

      // Iterate over all checkboxes in the table
      table.$('input[type="checkbox"]').each(function(){
         // If checkbox doesn't exist in DOM
         if(!$.contains(document, this)){
            // If checkbox is checked
            if(this.checked){
               // Create a hidden element
               $(form).append(
                  $('<input>')
                     .attr('type', 'hidden')
                     .attr('name', this.name)
                     .val(this.value)
               );
            }
         }
      });
   });

});
</script>