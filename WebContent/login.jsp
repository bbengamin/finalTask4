<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:if test="${not empty sessionScope.user}">
	<jsp:forward page="/controller?command=listTests"></jsp:forward>
</c:if>
<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<script type="text/javascript" src="js/md5.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<body>
<div  class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
	
	
	<div id="center-form" class="span7">
		<%-- CONTENT --%>
		<div id="center-container" class="container">
        <div class="card card-container">
            <img id="profile-img" class="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
            <form class="form-signin" action="controller" method="post">
            	<input type="hidden" name="command" value="login" />
                <span id="reauth-email" class="reauth-email"></span>
                <input type="text" name="login" id="inputEmail" class="form-control" placeholder="<fmt:message key='login_jsp.login'/>" value="${login}"  pattern="[A-Za-z0-9]*" required autofocus>
                <input type="password" id="password" onchange="hash()" name="password"  placeholder="<fmt:message key='login_jsp.password'/>" required/><br> 
                <c:if test="${not empty sessionScope.errorMessage}">
					 <label id="errorLabel">${sessionScope.errorMessage }</label>
					<c:set var="errorMessage" value="" scope="session"></c:set>
				</c:if>
				<input type="hidden" id="hash_password" name="hash_password" /><br> <br> 
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"><fmt:message key='login_jsp.signin'/></button>
            </form>
            <a href="restorePassword.jsp" class="forgot-password"><fmt:message key='login_jsp.fog_pass'/></a>
	        </div><!-- /card-container -->
	    </div><!-- /container -->

		<script type="text/javascript">
			function hash() {
				var pass = document.getElementById("password");
				var hash = document.getElementById("hash_password");
				hash.value = CryptoJS.MD5(pass.value);
			}
		</script>

		<%-- CONTENT --%>


</div>
</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>