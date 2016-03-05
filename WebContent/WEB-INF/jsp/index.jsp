<%@page import="ua.nure.bogdanov.SummaryTask4.db.Complexity"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Tests"/>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<%@ include file="/WEB-INF/jspf/tables.jspf"%>
<script type="text/javascript">
function filter1 (phrase, _id){
    var words = phrase.value.toLowerCase().split(" ");
    var table = document.getElementById(_id);
    for (var r = 1; r < table.rows.length; r++){
        var cellsV = table.rows[r].cells[1].innerHTML.replace(/<[^>]+>/g,"");
        var cellsV = [cellsV].join(" ");
        var displayStyle = 'none';
        for (var i = 0; i < words.length; i++) {
        if (cellsV.toLowerCase().indexOf(words[i])>=0)
            displayStyle = '';
        else {
            displayStyle = 'none';
            break;
        }
        }
    table.rows[r].style.display = displayStyle;
    }
}
</script>
<body>
<div class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
	<div class="span7">
		<%-- CONTENT --%> 
		
		<div class="filter pull-right">
			Subjects: <select name="filt" onchange="filter1(this, 'table'); this.options[0].selected = true; ">
			<option value="">-- Select --</option>
				<option value="">All</option>
			<c:forEach var="bean1" items="${requestScope.subjects}">
						<option value="${bean1.name}">${bean1.name}</option>
			</c:forEach>
			</select>
		</div>		
						
		<table id="table" class="hover" width="100%">
			<thead>
				<tr>
					<th><fmt:message key='index_jsp.name'/></th>
			        <th><fmt:message key='index_jsp.subject'/></th>
			        <th><fmt:message key='index_jsp.complexity'/></th>
			        <th><fmt:message key='index_jsp.time'/></th>
			        <th><fmt:message key='index_jsp.countOfQuestions'/></th>
			        <th></th>
				</tr>
			</thead>
			<tbody>
			    <c:forEach var="bean" items="${listTestsBeans}">
				<tr>
					<td>${bean.name}
						<c:if test="${minResult eq bean.id }">
							(HARD) 
						</c:if>
					</td>
					<td>${bean.subject}</td>
					<td>${bean.complexity}</td>
					<td>${bean.timer}</td>
					<td>${bean.countOfQuestions}</td>
					<td><c:choose>
						<%-- ADMIN MENU --%>
						<c:when test="${sessionScope.user.roleId eq 0}">
						<a href="controller?command=updateTestForm&id=${bean.id}" class="btn btn-default btn-lg"><fmt:message key='index_jsp.edit'/></a>
						<a href="#${bean.id}" class="btn btn-default btn-lg"><fmt:message key='index_jsp.delete'/></a>
						<a href="controller?command=stats&idTest=${bean.id}" class="btn btn-default btn-lg"><fmt:message key='index_jsp.stats'/></a>
						</c:when>
						<%-- ADMIN MENU --%>
						<%-- USER MENU --%>
						<c:when test="${sessionScope.user.roleId eq 1}">
						<a href="controller?command=startTest&idTest=${bean.id}" class="btn btn-default btn-lg"><fmt:message key='index_jsp.start'/></a>
						</c:when>
						<%-- USER MENU --%>
					</c:choose></td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
		<script type="text/javascript">
	
		</script>
		<%-- DELETE DIALOG --%>
		<c:forEach var="bean" items="${listTestsBeans}">
			<div id="${bean.id}" class="modalDialog">
				<div>
					<form  action="controller" method="POST">
			            <div class="form-group">
			            	<label for="text"><fmt:message key='index_jsp.delete_mes'/> ${bean.name}?</label>
							<input type="hidden" name="command" value="delete">
							<input type="hidden" name="idTest" value="${bean.id}">
							<input class="btn btn-default btn-lg" type="submit" value='<fmt:message key='index_jsp.delete'/> '>
							<a href="#close" title="Close" class="btn btn-default btn-lg"><fmt:message key='index_jsp.close'/> </a>
						</div>
					</form >
				</div>
			</div>
		</c:forEach>
		<%-- DELETE DIALOG --%>
		<%-- CONTENT --%>
</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</div>
</body>
</html>