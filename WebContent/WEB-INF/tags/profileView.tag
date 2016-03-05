<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ex" uri="/WEB-INF/tags/userTag.tld"%>
<%@ attribute name="enabledToChange" required="true" %>

<c:if test="${enabledToChange}">
	<form id="login_form" action="controller" method="post">
		<input type="hidden" name="command" value="updateProfile" />
		<c:if test="${not empty requestScope.userProfile}">
			<fieldset>
				<legend><fmt:message key='profile_jsp.profile'/></legend>
				<label><fmt:message key='profile_jsp.firstName'/>: </label>
				<input name="firstName"	value="${requestScope.userProfile.firstName}" autofocus required><br />
				<label><fmt:message key='profile_jsp.lastName'/>: </label>
				<input name="lastName" value="${requestScope.userProfile.lastName}" autofocus required><br />
				<label><fmt:message key='profile_jsp.login'/>: </label>
				<input name="login" value="${requestScope.userProfile.login}"  pattern="[A-Za-z0-9]*" autofocus required><br />
				<label><fmt:message key='profile_jsp.oldPass'/>:</label>
				<input type="password" name="oldPassword"><br />
				<label><fmt:message key='profile_jsp.newPass'/>:</label>
				<input type="password" name="newPassword"><br />
				<label><fmt:message key='profile_jsp.confPass'/>:</label>
				<input type="password" name="password_conf"><br />
				<label><fmt:message key='profile_jsp.email'/>: </label>
				<input type="email" name="email" value="${requestScope.userProfile.email}" required>
			</fieldset>
		</c:if>
		<input class="btn btn-default btn-lg" type="submit" value="<fmt:message key='profile_jsp.save'/>">
	</form>
</c:if>

<c:if test="${!enabledToChange}">
	<fieldset>
		<legend><fmt:message key='profile_jsp.profile'/></legend>
		<ex:User user='${requestScope.userProfile}' />
		<a href="#status" class="btn btn-default btn-lg"><fmt:message key='profile_jsp.changeStatus'/></a>
	</fieldset>
	<form  id="role"  action="controller" method="post">
		<input type="hidden" name="command" value="changeRole"> 
		<input type="hidden" name="idUser" value="${requestScope.userProfile.id}"> 
		<input type="submit" class="btn btn-default btn-lg" value='<fmt:message key='profile_jsp.changeRole'/>'>
	</form>
	<div id="status" class="modalDialog">
		<div>
			<form  action="controller" method="POST">
	            <div class="form-group">
	            	<label for="text"><fmt:message key='profile_jsp.mes'/> ${requestScope.userProfile.login}?</label>
	            	<input class="form-control" type="text" name="block_message"></input><br/>
	            	<input type="hidden" name="command" value="changeStatus"> 
					<input type="hidden" name="currentStatus" value="${requestScope.userProfile.status}">
					<input type="hidden" name="login" value="${requestScope.userProfile.login}">
					<input type="submit" class="btn btn-default btn-lg" value='<fmt:message key='profile_jsp.change'/>'>
					<a href="#close" title="Close" class="btn btn-default btn-lg"><fmt:message key='profile_jsp.close'/></a>
				</div>
			</form >
		</div>
	</div>
</c:if>
