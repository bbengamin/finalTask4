<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>

<c:set var="title" value="Register" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<div class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
	<div class="span7">
		<c:if test="${not empty sessionScope.user}">
			<jsp:forward page="/controller?command=listTests"></jsp:forward>
		</c:if>
		<%-- CONTENT --%>
		<c:if test="${not empty requestScope.errorMessage}">
			<fieldset name="error">
				<legend>Error</legend>
				${requestScope.errorMessage }
			</fieldset>
			<br>
			<br>
		</c:if>
		<fieldset>
			<form action="controller" method="get">
				<input type="hidden" name="command" value="newPassword" />
				<input type="hidden" name="idUser" value="${requestScope.idUser}" />
				<legend><fmt:message key='newPassword_jsp.label'/></legend>
				<label><fmt:message key='newPassword_jsp.password'/>: </label><input type="password" name="password" autofocus required><br />
				<label><fmt:message key='newPassword_jsp.confPass'/>: </label><input type="password" name="password_conf" autofocus	required><br /> 
				<input type="submit" class="btn btn-default btn-lg"  value="<fmt:message key='newPassword_jsp.save'/>">
			</form>
		</fieldset>
		<%-- CONTENT --%>
	</div>
</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>