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
		<form action="register" method="post">
			<div class="form-info">
				<h2 align="center">HireZone</h2>
			</div>
			<div class="email-w3l"><span  style="font-size:20px;color:#E2E6E7;">FirstName</span>
			<input type="hidden" value="<%= request.getParameter("id") %>" name="role">
				<input class="email p-0 mt-3" type="text" name="firstname"  required="">
			</div>
			<div class="email-w3l"><span  style="font-size:20px;color:#E2E6E7;">LastName</span>
				<input class="email p-0 mt-3" type="text" name="lastname"  required="">
			</div>
			<div class="email-w3l"><span  style="font-size:20px;color:#E2E6E7;">Email</span>
				<input class="email p-0 mt-3" type="email" name="username"  required="">
			</div>
			<div class="email-w3l"><span  style="font-size:20px;color:#E2E6E7;">Password</span>
				<input class="email p-0 mt-3" type="password" name="password"  required="">
			</div>
			<div class="email-w3l"><span  style="font-size:20px;color:#E2E6E7;">ConfirmPassword</span>
				<input class="email p-0 mt-3" type="password" name="confirmpasssword"  required="">
			</div>
			<div class="submit-agileits">
				<input class="login" type="submit" value="register Now">
			</div>
			<span id="message" class="text-danger font-weight-bold">${msg}</span>
			
		</form>
	</div>
</div>
<footer>HireZone v0.1</footer>
  <script src="${pageContext.request.contextPath}/resources/assets/js/jquery-3.3.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/assets/js/jquery-ui.js"></script>
  <script src="${pageContext.request.contextPath}/resources/assets/js/popper.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.magnific-popup.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/assets/js/bootstrap-datepicker.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap4.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>
  <script src="${pageContext.request.contextPath}/resources/assets/js/aos.js"></script>


</body>
</html>