<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="pv" tagdir="/WEB-INF/tags" %>
<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/tables.jspf" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
<body>
<div class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
	<div class="span7">
		<%-- CONTENT --%>
		<c:if test="${not empty sessionScope.message}">
			<fieldset name="Message">
				<legend>Message</legend>
				${sessionScope.message }
				<c:set var="message" value="" scope="session"></c:set>
			</fieldset>
			<br>
			<br>
		</c:if>
		<%-- USER TAG --%>
		<pv:profileView enabledToChange="${enabledToChange}" />
		<hr/>
		<table id="table" class="display">
			<thead>
				<tr>
					<th><fmt:message key='profile_jsp.name'/></th>
					<th><fmt:message key='profile_jsp.complexity'/></th>
			        <th><fmt:message key='profile_jsp.subject'/></th>
			        <th><fmt:message key='profile_jsp.time'/></th>
			        <th><fmt:message key='profile_jsp.countOfQuestions'/></th>
			        <th><fmt:message key='profile_jsp.result'/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="bean" items="${UserTestBeans}">
					<tr>
						<td>${bean.name}</td>
						<td>${bean.complexity}</td>
						<td>${bean.subject}</td>
						<td>${bean.timer}</td>
						<td>${bean.countOfQuestions}</td>
						<td>${bean.result}</td>
					</tr>
					</c:forEach>
			</tbody>
		</table>
	<%-- CONTENT --%>
	</div>
</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>