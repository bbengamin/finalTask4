<%@page import="ua.nure.bogdanov.SummaryTask4.db.Complexity"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
<div class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
	<div class="span7">
		<%-- CONTENT --%>

		<fmt:message key='testResult_jsp.mes1'/>: ${requestScope.userMaxResult}%<br/>
		<fmt:message key='testResult_jsp.mes2'/>: ${sessionScope.lastTestResult}%<br/>
		<a href="controller?command=profile" class="btn btn-default btn-lg"><fmt:message key='testResult_jsp.profile'/></a>
		<a href="controller?command=listTests" class="btn btn-default btn-lg"><fmt:message key='testResult_jsp.tests'/></a>
		<%-- CONTENT --%>

	</div>
</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>