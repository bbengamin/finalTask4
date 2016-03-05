<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<%@ include file="/WEB-INF/jspf/tables.jspf"%>
<body>
<div class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
	<div class="span7">
		<%-- CONTENT --%>
		<table id="table" class="display">
			<thead>
				<tr>
					<th><fmt:message key='users_jsp.firstName'/></th>
					<th><fmt:message key='users_jsp.lastName'/></th>
					<th><fmt:message key='users_jsp.login'/></th>
					<th><fmt:message key='users_jsp.email'/></th>
					<th><fmt:message key='users_jsp.role'/></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="bean" items="${users}">
					<tr>
						<td>${bean.firstName}</td>
						<td>${bean.lastName}</td>
						<td>${bean.login}</td>
						<td>${bean.email}</td>
						<td>
							<c:if test="${bean.roleId == 0}">admin</c:if>
							<c:if test="${bean.roleId == 1}">student</c:if>
						</td>
						<td><a href="controller?command=profile&login=${bean.login}" class="btn btn-default btn-lg"><fmt:message key='users_jsp.view'/></a></td>	
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