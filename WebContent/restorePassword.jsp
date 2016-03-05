<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<script type="text/javascript" src="js/md5.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<body>
<div class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
	<c:if test="${not empty sessionScope.user}">
		<jsp:forward page="/controller?command=listTests"></jsp:forward>
	</c:if>
	<div class="span7">
		<%-- CONTENT --%>
		
		<c:if test="${not empty errorMessage}">
			<fieldset name="Message">
				<legend>Message</legend>
				${errorMessage }
			</fieldset>
			<br>
			<br>
		</c:if>
		<fmt:message key='restorePassword_jsp.mes'/>:
		<form  action="controller" method="get">
			<input type="hidden" name="command" value="restorePassword" />
			<input name="login" value="${requestScope.login }" autofocus required /><br />
			<input class="btn btn-default btn-lg" type="submit" id="submit" value="<fmt:message key='restorePassword_jsp.restore'/>">
		</form>
		<%-- CONTENT --%>

	</div>
</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>