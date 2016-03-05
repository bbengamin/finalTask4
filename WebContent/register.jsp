<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ taglib prefix="pv" tagdir="/WEB-INF/tags" %>
<html>

<c:set var="title" value="Register" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<div class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
	<div id="center-form" class="span7">
		<div id="center-container" class="container">
		<pv:errorMessage/>
		
		<form id="login_form" action="controller" method="post">
			<input type="hidden" name="command" value="register" />
			<fieldset>
				<legend><fmt:message key='register_jsp.registerMes'/></legend>
				<c:choose>
					<c:when test="${not empty temp}">
						<label><fmt:message key='register_jsp.firstName'/>: </label><input type="text" name="firstName"	value="${temp.firstName}" autofocus required><br />
						<label><fmt:message key='register_jsp.lastName'/>: </label><input type="text" name="lastName" value="${temp.lastName}" autofocus required><br />
						<label><fmt:message key='register_jsp.login'/>: </label><input type="text" name="login"  value="${temp.login}" pattern="[A-Za-z0-9]*"	autofocus required><br />
						<label><fmt:message key='register_jsp.password'/>:	</label><input type="password" name="password" autofocus required><br />
						<label><fmt:message key='register_jsp.confPass'/>: </label><input	type="password" name="password_conf" autofocus required><br />
						<label><fmt:message key='register_jsp.email'/>: </label><input type="email" name="email"	value="${temp.email}" required placeholder="Enter a valid email address"><br />
					</c:when>
					<c:when test="${empty temp}">
						<label><fmt:message key='register_jsp.firstName'/>: </label><input type="text" name="firstName"	 autofocus required><br />
						<label><fmt:message key='register_jsp.lastName'/>: </label><input type="text" name="lastName"  autofocus required><br />
						<label><fmt:message key='register_jsp.login'/>: </label><input type="text" name="login"   pattern="[A-Za-z0-9]*"	autofocus required><br />
						<label><fmt:message key='register_jsp.password'/>:	</label><input type="password" name="password" autofocus required><br />
						<label><fmt:message key='register_jsp.confPass'/>: </label><input	type="password" name="password_conf" autofocus required><br />
						<label><fmt:message key='register_jsp.email'/>: </label><input type="email" name="email" required placeholder="Enter a valid email address"><br />
					</c:when>
				</c:choose>
				<div class="g-recaptcha" data-sitekey="6LfgOQsTAAAAAC7_PNZm4rnCqN5-zcuDuZG8hR1H"></div><br/>
			</fieldset>
			<br />

			<input class="btn btn-default btn-lg" type="submit" value="<fmt:message key='register_jsp.register'/>">
		</form>
		<%-- CONTENT --%>
		<c:set var="temp" value="" scope="session"></c:set>
		</div>
	</div>
</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>