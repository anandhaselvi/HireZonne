<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="/WEB-INF/views/header.jsp" />

        <div class="site-section bg-light">

      <div class="container">
        <div class="row">
       
          <div class="col-md-12 col-lg-8  my-5">
          
            
          
            <form action="#" class="p-5 bg-white mt-5">
              
             <h3>Candidate</h3>

              <div class="row form-group">
                <div class="col-md-12 mb-3 mb-md-0">
                  <label class="font-weight-bold" for="fullname">Name</label>
                  <input type="text" id="name" class="form-control" >
                </div>
              </div>

              <div class="row form-group mb-5">
                <div class="col-md-12 mb-3 mb-md-0">
                  <label class="font-weight-bold" for="fullname">EmailID</label>
                  <input type="text" id="emailid" class="form-control">
                </div>
              </div>


             

              <div class="row form-group mb-4">
                <div class="col-md-12 mb-3 mb-md-0">
				      <label class="font-weight-bold" for="fullname">Date of Birth</label>

                  <input type="text" class="form-control">
                </div>
              </div>

              <div class="row form-group">
                <div class="col-md-12 mb-3 mb-md-0">
					<label class="font-weight-bold" for="fullname">Profile</label>
                  <textarea name="" class="form-control" id="" cols="30" rows="5"></textarea>
                </div>
              </div>

              <div class="row form-group">
                <div class="col-md-12">
                  <input type="submit" value="Post a Job" class="btn btn-primary  py-2 px-5">
                </div>
              </div>

  
            </form>
          </div>

         
        </div>
      </div>
     </div> 
      <jsp:include page="/WEB-INF/views/footer.jsp" />
      