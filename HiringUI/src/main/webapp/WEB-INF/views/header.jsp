<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>HireZone</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="shotcut icon" type="image/png" href="/favicon.png">

    <link href="https://fonts.googleapis.com/css?family=Amatic+SC:400,700|Work+Sans:300,400,700" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/fonts/icomoon/style.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/magnific-popup.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/jquery-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/fonts/flaticon/font/flaticon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/aos.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/toastr.css">

  </head>
  <body>
  
  <div class="site-wrap">

    <div class="site-mobile-menu">
      <div class="site-mobile-menu-header">
        <div class="site-mobile-menu-close mt-3">
          <span class="icon-close2 js-menu-toggle"></span>
        </div>
      </div>
      <div class="site-mobile-menu-body"></div>
    </div> <!-- .site-mobile-menu -->
    
    
      <div class="site-navbar-wrap js-site-navbar bg-white">
      
      <div class="container">
        <div class="site-navbar bg-light">
          <div class="py-1">
            <div class="row align-items-center">
              <div class="col-2">
                <h2 class="mb-0 site-logo"><strong class="font-weight-bold">HireZone</strong></h2>
              </div>
              <div class="col-10">
                <nav class="site-navigation text-right" role="navigation">
                  <div class="container">
                    <div class="d-inline-block d-lg-none ml-md-0 mr-auto py-3"><a href="#" class="site-menu-toggle js-menu-toggle text-black"><span class="icon"></span></a></div>
					<ul class="site-menu js-clone-nav d-none d-lg-block">
					<c:choose>
					<c:when test="${fn:containsIgnoreCase(sessionScope.role, 'customer')}">

					   <li><a href="assignvendor">Assign Vendors</a></li>

                      <li><a href="reviewcandidate">Review Candidate</a></li>
                      <li>
                        <a href="createjobpost">Posting Job</a>
                      </li>
					  </c:when>
					  <c:when test="${fn:containsIgnoreCase(sessionScope.role, 'vendor')}">
					  <li>
                        <a href="assignvendor">Assign Vendors</a>
                      </li>
					  <li>
                        <a href="reviewcandidate">Submit Profile</a>
                      </li>
					   <li>
                        <a href="createjobpost">Posting Job</a>
                      </li>
					  </c:when>
					 
					</c:choose>
					  <li class="header-nav-item dropdown">
					  <a class="header-nav-link" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
					   <div class="avatar">
							<img class="avatar-img" src="${pageContext.request.contextPath}/resources/assets/images/user.png" alt="user@email.com">
					  </div>
					</a>
					<div class="dropdown-menu dropdown-menu-right">
							<a href="passwordchange" class="dropdown-item">Reset Password</a>
							<div class="dropdown-divider"></div>
							<a href="login?id=customer" class="dropdown-item">Logout</a>
					 </div>
			         </li>
                    </ul>
                  </div>
                </nav>
              </div>
			 
            </div>
          </div>
        </div>
      </div>
    </div>
  
    </div>