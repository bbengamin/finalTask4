<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<script type="text/javascript">
	function startTimer() {
		var my_timer = document.getElementById("my_timer");
		var time = my_timer.innerHTML;
		var arr = time.split(":");
		var h = arr[0];
		var m = arr[1];
		var s = arr[2];
		if (s == 0) {
			if (m == 0) {
				if (h == 0) {
					alert("Время вышло");
					window.location.replace("controller?command=listTests");
					return;
				}
				h--;
				m = 60;
				if (h < 10)
					h = "0" + h;
			}
			m--;
			if (m < 10)
				m = "0" + m;
			s = 59;
		} else
			s--;
		if (s < 10)
			s = "0" + s;
		document.getElementById("my_timer").innerHTML = h + ":" + m + ":" + s;
		setTimeout(startTimer, 1000);
	}
</script>
<body onload="startTimer()">

<div class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
	<div class="span7">
	<%-- CONTENT --%>
		<p>
			<span id="my_timer"	style="color: #f00; font-size: 150%; font-weight: bold;">${timer}</span>
		</p>
		<c:if test="${not empty requestScope.question}">
			<div class="pagination pagination-centered">
				<div class="pagination">
					<ul>
						<c:forEach var="i" begin="1" end="${sessionScope.questionCount}">
							<c:if test="${i eq requestScope.questionNumber}">
								<li  class="active"><a href="controller?command=navigation&idQuestion=1&way=${i}"><span><c:out value="${i}"/></span></a></li>
							</c:if>
							<c:if test="${i != requestScope.questionNumber}">
								<li><a href="controller?command=navigation&idQuestion=1&way=${i}"><span><c:out value="${i}"/></span></a></li>
							</c:if>
						</c:forEach>
		     		</ul>
				</div>
				
				<form action="controller" method="get">
					<input type="hidden" name="command" value="answer">
					<input type="hidden" name="idQuestion" value="${requestScope.question.id}">
					
					${requestScope.question.body}<br>
					<div id="question-box">
					<c:forEach var="bean" items="${requestScope.answers}">
						<c:if test="${fn:contains(requestScope.checked, bean.id)}">
							<input type="checkbox" name="answer${bean.id}" checked='checked'> ${bean.body }<br>
							<br>
						</c:if>
						<c:if test="${not fn:contains(requestScope.checked, bean.id)}">
							<input type="checkbox" name="answer${bean.id}"> ${bean.body }<br>
							<br>
						</c:if>
					</c:forEach>
					</div>
					<%-- NAVIGATION --%>
					<a href="controller?command=navigation&idQuestion=${requestScope.question.id}&way=prev" class="btn btn-default btn-lg"><fmt:message key='test_jsp.prev'/></a>
					<input type="submit" class="btn btn-default btn-lg" value='<fmt:message key='test_jsp.answer'/>'>
					<a href="controller?command=navigation&idQuestion=${requestScope.question.id}&way=Next" class="btn btn-default btn-lg"><fmt:message key='test_jsp.next'/></a>
					<%-- NAVIGATION --%>
			</form>
			
		</c:if>
		<form action="controller" method="post">
			<input type="hidden" name="command" value="finishTest">
			<input type="submit" class="btn btn-default btn-lg" value='<fmt:message key='test_jsp.finish'/>'>
		</form>
		</div>
	<%-- CONTENT --%>
</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>