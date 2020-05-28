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
							class="form-control" id="code" name="code" maxlength="20">
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
                  <textarea name="" class="form-control" id="" cols="30" rows="5"></textarea>
                </div>
             
			  </div>
			  </div>
			  </div>		
	</div>
	<div class="row">
		<div class="col-sm-12">
		<button type="submit" id="addButton"  class="btn btn-success" >Add</button>
		<button type="submit" id="updateButton" class="btn btn-success" onclick="activityUpdate()">Update</button>
		</div>
	</div>

  
            </form>
          </div>
          </div>
<jsp:include page="/WEB-INF/views/footer.jsp" />
          