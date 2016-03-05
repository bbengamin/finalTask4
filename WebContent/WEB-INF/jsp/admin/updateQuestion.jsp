<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<script type="text/javascript" src="js/jquery.js"></script>
<script>
	$(document).ready(function(){
	  $("#show").click();
	});
</script>
<body>
<div class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
	<div class="span7">
	<%-- CONTENT --%>
		<form action="controller" method="post">
			<input type="hidden" name="command" value="updateQuestion" />
			<input type="hidden" name="idQuestion" value="${requestScope.question.id}" />
			<input type="hidden" name="idTest" value="${requestScope.idTest}" />
			<fmt:message key='updateQuestion_jsp.body'/>:<br>
			<textarea name="body" rows="3">${requestScope.question.body}</textarea><br>
			<input type="submit" class="btn btn-default btn-lg" value="<fmt:message key='updateQuestion_jsp.save'/>">
			<a href="controller?command=updateTestForm&id=${requestScope.idTest}" class="btn btn-default btn-lg"><fmt:message key='updateQuestion_jsp.back'/></a>
		</form>
		<c:if test="${not empty requestScope.question.id}">
			<a href="controller?command=updateAnswerForm&idTest=${requestScope.idTest}&idQuestion=${requestScope.question.id}" class="btn btn-default btn-lg"><fmt:message key='updateQuestion_jsp.new'/></a>
			<a id="show" class="btn btn-primary" role="button" data-toggle="collapse" href="#collapseAnswer" aria-expanded="false" aria-controls="collapseAnswer">
				  <fmt:message key='updateQuestion_jsp.show'/>
			</a>
			<%-- ANSWERS LIST --%>
			<div class="collapse" id="collapseAnswer">
	  			<div class="well">
	  				<c:forEach var="bean" items="${requestScope.answers}">
	  				${bean.body}
	  				<c:if test="${bean.correct}">
	  					<img width="20" height="20" src="img/correct.png">
	  				</c:if>
	  				<a href="controller?command=updateAnswerForm&idTest=${requestScope.idTest}&idQuestion=${requestScope.question.id}&idAnswer=${bean.id}" class="btn btn-default btn-lg pull-right"><fmt:message key='updateQuestion_jsp.edit'/></a>
	  				<a href="#${bean.id}" class="btn btn-default btn-lg pull-right"><fmt:message key='updateQuestion_jsp.delete'/></a>
	  				<hr>
	  				</c:forEach>
	  			</div>
			</div>
			<%-- ANSWERS LIST --%>
			
			<%-- DELETE DIALOG --%>
			<c:forEach var="bean" items="${requestScope.answers}">
				<div id="${bean.id}" class="modalDialog">
					<div>
						<form  action="controller" method="POST">
				            <div class="form-group">
				            	<label for="text"><fmt:message key='updateQuestion_jsp.mes'/> ${bean.body}?</label>
								<input type="hidden" name="command" value="delete">
								<input type="hidden" name="idTest" value="${requestScope.idTest}">
								<input type="hidden" name="idQuestion" value="${requestScope.question.id}" />
								<input type="hidden" name="idAnswer" value="${bean.id}" />
								<input class="btn btn-default btn-lg" type="submit" value='Delete'>
								<a href="#close" title="Close" class="btn btn-default btn-lg">Close</a>
							</div>
						</form >
					</div>
				</div>
			</c:forEach>
			<%-- DELETE DIALOG --%>
		</c:if>
	<%-- CONTENT --%>
	</div>
</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>