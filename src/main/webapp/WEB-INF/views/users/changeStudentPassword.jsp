<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ICR</title>

<!--[if lte IE 8]><script src="/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="/css/registration.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="/css/ie9.css" /><![endif]-->

<!-- Favicon -->
<link rel="shortcut icon"
	href="<c:url value="/img/siteImages/favicon.ico"/>">



</head>
<body class="landing">
	<!-- Page Wrapper -->
	<div id="page-wrapper">

		<!-- Header -->
		<header id="header" class="alt">
			
			<nav id="nav">
				<ul>
					<li class="special"><a href="#menu" class="menuToggle"><span>Menu</span></a>
						<div id="menu">
							<ul>
								<li><a href="homeStudent">Torna alla pagina dello
										studente</a></li>
								<li><a href="logout">Logout</a></li>
							</ul>
						</div></li>
				</ul>
			</nav>
		</header>
	</div>
	<div class="form">
		<div class="tab-content">
			<div id="signup">
				<h1>Modifica Passoword</h1>
				<form:form method="post" action="changeStudentPassword"
					modelAttribute="student" name="f1" onsubmit="return matchpass()">
					<div class="top-row">

						<div class="field-wrap">
							<label>Nuova Password </label>
							<form:input type='password' path="password"
								placeholder="Password" />
								${errPassword}
						</div>
						<div class="field-wrap">
							<label> Conferma Password </label> <input type="password"
								placeholder="Conferma Password" name="password2" />
						</div>

						<form:hidden path="id" />
						<form:hidden path="name" />
						<form:hidden path="surname" />
						<form:hidden path="school" />
						<form:hidden path="schoolGroup" />
						<form:hidden path="section" />
						<form:hidden path="username" />

						<button type="submit" class="button button-block">Conferma</button>
					</div>
				</form:form>

			</div>

			<div id="login"></div>

		</div>
		<!-- tab-content -->

	</div>
	<!-- /form -->

	<!-- Scripts -->
	<script src="/js/jquery.min.js"></script>
	<script src="/js/jquery.scrollex.min.js"></script>
	<script src="/js/jquery.scrolly.min.js"></script>
	<script src="/js/skel.min.js"></script>
	<script src="/js/util.js"></script>
	<!--[if lte IE 8]><script src="/js/ie/respond.min.js"></script><![endif]-->
	<script src="/js/main.js"></script>
	<script src="/js/checkPassword.js"></script>



</body>
</html>