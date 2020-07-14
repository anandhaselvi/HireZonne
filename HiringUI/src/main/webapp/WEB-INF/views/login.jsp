<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HireZone</title> 
<link rel="shotcut icon" type="image/png" href="/favicon.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/fonts/icomoon/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css">
<link href="${pageContext.request.contextPath}/resources/assets/css/loginstyle.css" rel="stylesheet" type="text/css" media="all">
</head>
<body class="bg-dark">
<div class="form">
<h1></h1>
	<div class="form-content">
		<form action="login" method="post">
			<div class="form-info">
				<h2 align="center">HireZone</h2>
			</div>
			<span id="message" class="text-danger font-weight-bold" style="color:#E2E6E7;">${msg}</span>
			<div class="email-w3l">
				<span class="i1"><i class="icon-envelope-o" aria-hidden="true"></i></span>
				<input class="email" type="email" name="username" placeholder="Email" required="">
			</div>
			<div class="pass-w3l">
			<!---728x90--->
			<span class="i2"><i class="icon-unlock" aria-hidden="true"></i></span>
				<input class="pass" type="password" name="password" placeholder="Password" required="">
			</div>
			
			<div class="col-sm-12">
		<div class="row">
				<div class="col-sm-6 left">
					<input type="checkbox" value="Remember me">&nbsp;Remember me
			  </div>
			  <div class="col-sm-6 right">
					<a href="#" style="text-decoration:none;">Forgot Password?</a>
			  </div>
			  </div>
			  <div class="clear"></div>
			  </div>
			
			<div class="submit-agileits">
				<input class="login" type="submit" value="login">
			</div>
			<div class="col-sm-12 my-4">
			<span style="color:#E2E6E7;">Don't have an account?</span>&nbsp;&nbsp;<a href="register" class="text-info" id="candidateregister">Register here</a>
			<a href="register?type=vendor" class="text-info" id="vendorregister">Register here</a>
			</div>
			<input type="hidden" value="<%= request.getParameter("id") %>">
		</form>
	</div>
</div>
<footer>HireZone v0.1</footer>
  <script src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.min.js"></script>

<script>
$(document).ready(function(){
	debugger;
	 var queryString = window.location.search;
	 var urlParams = new URLSearchParams(queryString);	
	 // get the required parameter
	 var param = urlParams.get('id'); 
		if(param != undefined){
			document.getElementById("candidateregister").style.display = 'none';
		}else{
			document.getElementById("vendorregister").style.display = 'none';
		}
 });
</script>
</body>
</html>