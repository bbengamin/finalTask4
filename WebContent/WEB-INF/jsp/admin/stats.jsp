<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Tests"/>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<%@ include file="/WEB-INF/jspf/tables.jspf"%>

<body>
<div class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
	<div class="span7">
		<table id="table" class="display">
			<thead>
				<tr>
					<th><fmt:message key='stats_jsp.login'/></th>
					<th><fmt:message key='stats_jsp.name'/></th>
			        <th><fmt:message key='stats_jsp.subject'/></th>
			        <th><fmt:message key='stats_jsp.complexity'/></th>
			        <th><fmt:message key='stats_jsp.time'/></th>
			        <th><fmt:message key='stats_jsp.countOfQuestions'/></th>
					<th><fmt:message key='stats_jsp.result'/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="bean" items="${UserTestBeans}">
					<tr>
						<td>${bean.login}</td>
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
		<a href="controller?command=generatePdf&idTest=${requestScope.idTest}" class="btn btn-default btn-lg"><fmt:message key='stats_jsp.pdf'/></a>
	</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</div>
</body>
</html>