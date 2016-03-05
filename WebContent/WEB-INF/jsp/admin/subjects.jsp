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
		<a href="#add" class="btn btn-default btn-lg"><fmt:message key='subject_jsp.add'/></a>
		<hr/>
		<table id="table" class="display">
			<thead>
				<tr>
					<th><fmt:message key='subject_jsp.subject_name'/></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="bean" items="${list}">
					<tr>
						<td>${bean.name}</td>
						<td>
							<a href="#edit${bean.id}" class="btn btn-default btn-lg pull-right"><fmt:message key='subject_jsp.edit'/></a>
							<a href="#delete${bean.id}" class="btn btn-default btn-lg pull-right"><fmt:message key='subject_jsp.delete'/></a>
						</td>	
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		
		<%-- ADD DIALOG --%>
		<div id="add" class="modalDialog">
			<div>
				<form  action="controller" method="POST">
		            <div class="form-group">
						<input type="hidden" name="command" value="updateSubject">
						<input type="text" name="name"  pattern="[А-Яа-яA-Za-z0-9+]*"	autofocus required/><br />
						<input class="btn btn-default btn-lg" type="submit" value='<fmt:message key='subject_jsp.add'/>'>
						<a href="#close" title="Close" class="btn btn-default btn-lg"><fmt:message key='updateTest_jsp.close'/></a>
					</div>
				</form >
			</div>
		</div>
		<%-- ADD DIALOG --%>
		
		<%-- EDIT DIALOG --%>
			<c:forEach var="bean" items="${list}">
				<div id="edit${bean.id}" class="modalDialog">
					<div>
						<form  action="controller" method="POST">
			            	<div class="form-group">
				            	<input type="hidden" name="command" value="updateSubject">
								<input type="hidden" name="idSubject" value="${bean.id}">
								<input type="text" name="name"  pattern="[A-Za-z0-9+]*"  value="${bean.name }"	autofocus required/><br />
								<input class="btn btn-default btn-lg" type="submit" value='<fmt:message key='subject_jsp.edit'/>'>
								<a href="#close" title="Close" class="btn btn-default btn-lg"><fmt:message key='updateTest_jsp.close'/></a>
							</div>
						</form >
					</div>
				</div>
			</c:forEach>
		<%-- EDIT DIALOG --%>
		
		<%-- DELETE DIALOG --%>
			<c:forEach var="bean" items="${list}">
				<div id="delete${bean.id}" class="modalDialog">
					<div>
						<form  action="controller" method="POST">
			            	<div class="form-group">
				            	<input type="hidden" name="command" value="delete">
								<input type="hidden" name="idSubject" value="${bean.id}">
								<label for="text"><fmt:message key='subject_jsp.delete_mes'/> ${bean.name}?</label>
								<input class="btn btn-default btn-lg" type="submit" value='<fmt:message key='subject_jsp.delete'/>'>
								<a href="#close" title="Close" class="btn btn-default btn-lg"><fmt:message key='updateTest_jsp.close'/></a>
							</div>
						</form >
					</div>
				</div>
			</c:forEach>
		<%-- EDIT DIALOG --%>
		
		<%-- ADD DIALOG --%>
		<div id="add" class="modalDialog">
			<div>
				<form  action="controller" method="POST">
		            <div class="form-group">
						<input type="hidden" name="command" value="updateSubject">
						<input type="text" name="name"  pattern="[A-Za-z0-9+]*"	autofocus required/><br />
						<input class="btn btn-default btn-lg" type="submit" value='<fmt:message key='subject_jsp.add'/>'>
						<a href="#close" title="Close" class="btn btn-default btn-lg"><fmt:message key='updateTest_jsp.close'/></a>
					</div>
				</form >
			</div>
		</div>
		<%-- ADD DIALOG --%>
	<%-- CONTENT --%>
	</div>
</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>