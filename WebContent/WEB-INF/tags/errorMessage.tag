<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty sessionScope.errorMessage}">
	<fieldset name="error">
		<legend>Message</legend>
		${sessionScope.errorMessage }
	</fieldset>
	<c:set var="errorMessage" value="" scope="session"></c:set>
</c:if>
<hr/>