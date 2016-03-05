<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ taglib prefix="pv" tagdir="/WEB-INF/tags" %>
<html>
<c:set var="title" value="Mail" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<script type="text/javascript" src="js/jquery.js"></script>
<body>
<div class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
		<div class="span7">
		<pv:errorMessage/>
		<%-- CONTENT --%>
		<form action="controller" method="post">
			<input type="hidden" value="sendMail" name="command">
	        <label for="mail" ><fmt:message key='mail_jsp.email'/>:</label>
	        <input type="email" name="mail"  required placeholder="Enter a valid email address"/><br/>
	        <label for="subject" ><fmt:message key='mail_jsp.subject'/>:</label>
	        <input type="text" name="subject" pattern="[A-Za-z0-9]*" required/><br/>
	        <textarea name="message" rows="3" required placeholder="enter your message here..."></textarea><br/>
	        <input class="btn btn-default btn-lg" type="submit" value = "<fmt:message key='mail_jsp.send'/>"/>
  		</form>
		<%-- CONTENT --%>
	</div>
</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>