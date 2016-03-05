<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>

<c:set var="title" value="Settings" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<script type="text/javascript" src="js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<div class="row-fluid">
	<%@ include file="/WEB-INF/jspf/header.jspf" %>
	<div class="span7">
	<%-- CONTENT --%>
		<form class="form-inline" action="controller" method="post">
		  <input type="hidden" name="command" value="changeLocale" />	
		  <div class="form-group">
		    <label for="message"><fmt:message key="settings_jsp.label.set_locale"/>:</label>
		    <select id="message" name="locale">
			    <c:forEach items="${applicationScope.locales}" var="locale">
					<c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
					<option value="${locale.key}" ${selected}>${locale.value}</option>
				</c:forEach>
			</select>
		  </div>
		  <input type="submit"  class="btn btn-default btn-lg" value="<fmt:message key='settings_jsp.form.submit_save_locale'/>">
		</form>
	<%-- CONTENT --%>
	</div>
</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
</body>
</html>