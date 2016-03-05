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
		<form id="update_answer" action="controller" method="post">
			<input type="hidden" name="command" value="updateAnswer" />
			<input type="hidden" name="idQuestion" value="${requestScope.idQuestion}" />
			<input type="hidden" name="idTest" value="${requestScope.idTest}" />
			<input type="hidden" name="idAnswer" value="${requestScope.answer.id}" />
			<fmt:message key='updateAnswer_jsp.body'/>:<br>
			<textarea name="body" rows="3" >${requestScope.answer.body}</textarea><br>
			<br>
			<c:if test="${requestScope.answer.correct}">
				<input type="checkbox" name="correct" checked="checked"> <fmt:message key='updateAnswer_jsp.correct'/><br>
			</c:if>
			<c:if test="${not requestScope.answer.correct}">
				<input type="checkbox" name="correct"> <fmt:message key='updateAnswer_jsp.correct'/><br>
			</c:if>
			<input type="submit" class="btn btn-default btn-lg" value="<fmt:message key='updateAnswer_jsp.save'/>">
			<a href="controller?command=updateQuestionForm&idTest=${requestScope.idTest}&idQuestion=${requestScope.idQuestion}" class="btn btn-default btn-lg"><fmt:message key='updateAnswer_jsp.back'/></a>
		</form>
		<%-- CONTENT --%>
	</div>
</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>