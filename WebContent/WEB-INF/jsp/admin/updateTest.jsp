<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Edit test" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
$(document).on('change', '.btn-file :file', function() {
	  var input = $(this),
	      numFiles = input.get(0).files ? input.get(0).files.length : 1,
	      label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
	  input.trigger('fileselect', [numFiles, label]);
	});
	$(document).ready(function(){
	  $("#show").click();
	});

	$(document).ready( function() {
	    $('.btn-file :file').on('fileselect', function(event, numFiles, label) {
	        
	        var input = $(this).parents('.input-group').find(':text'),
	            log = numFiles > 1 ? numFiles + ' files selected' : label;
	        
	        if( input.length ) {
	            input.val(log);
	        } else {
	            if( log ) alert(log);
	        }
	    });
	});
</script>
<body>
<div class="row-fluid">
	<%-- HEADER --%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<%-- HEADER --%>
	<div class="span7">
		<%-- CONTENT --%>
		<c:if test="${not empty requestScope.errorMessage}">
			<fieldset name="error">
				<legend>Error</legend>
				${requestScope.errorMessage }
			</fieldset>
		</c:if>

		<form  action="controller" method="POST">
            <div class="form-group">
				<input type="hidden" name="command" value="updateTest" />
				<input type="hidden" name="idTest" value="${requestScope.test.id}" />
            	<label for="name"><fmt:message key='updateTest_jsp.name'/>: </label>
				<input id="name" name="name" type="text" value="${requestScope.test.name }" autofocus	required />
			</div>
            <div class="form-group">
            	<label for="sub"><fmt:message key='updateTest_jsp.subject'/>: </label>
				<select id="sub" name="sub">
					<c:forEach var="bean1" items="${requestScope.subjects}">
						<c:if test="${requestScope.test.subject eq bean1.id}">
							<option selected value="${bean1.id}">${bean1.name}</option>
						</c:if>
						<c:if test="${requestScope.test.subject ne bean1.id}">
							<option value="${bean1.id}">${bean1.name}</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
            	<label for="compl"><fmt:message key='updateTest_jsp.complexity'/>: </label>
				<select id="compl" name="compl">
					<c:forEach var="bean2" items="${requestScope.complexitys}">
						<c:choose>
							<c:when test="${requestScope.test.complexity eq bean2.id}">
								<option selected value="${bean2.name}">${bean2.name}</option>
							</c:when>
							<c:when test="${requestScope.test.complexity ne bean2.id}">
								<option value="${bean2.name}">${bean2.name}</option>
							</c:when>
						</c:choose>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
            	<label for="time"><fmt:message key='updateTest_jsp.time'/>: </label>
				<input id="time" name="time" value="${requestScope.test.timer }" type="text" maxlength='3' size="3" pattern="[0-9]+" autofocus required />
			</div>
			<input class="btn btn-default btn-lg" type="submit" value='<fmt:message key='updateTest_jsp.save'/>'>
		</form>
		<hr>
		<c:if test="${not empty requestScope.test.id}">
			<label><fmt:message key='updateTest_jsp.questioncount'/>: ${fn:length(requestScope.questions)}</label>
			<a href="controller?command=updateQuestionForm&idTest=${requestScope.test.id}" class="btn btn-default btn-lg"><fmt:message key='updateTest_jsp.new'/></a>
			<a href="#upload" class="btn btn-default btn-lg"><fmt:message key='updateTest_jsp.file'/></a>
			<a id="show" class="btn btn-primary" role="button" data-toggle="collapse" href="#collapseExample" aria-expanded="true" aria-controls="collapseExample">
			 <fmt:message key='updateTest_jsp.show'/>
			</a>
			<%-- QUESTIONS LIST --%>
			<div class="collapse" id="collapseExample">
	  			<div class="well">
					<c:forEach var="bean" items="${requestScope.questions}">
						${bean.body}
						<a href="controller?command=updateQuestionForm&idTest=${requestScope.test.id}&idQuestion=${bean.id}" class="btn btn-default btn-lg pull-right"><fmt:message key='updateTest_jsp.edit'/></a>
						<a href="#${bean.id}" class="btn btn-default btn-lg pull-right"><fmt:message key='updateTest_jsp.delete'/></a>
						<hr>
					</c:forEach>
				</div>
			</div>
			<%-- QUESTIONS LIST --%>
		</c:if>
		
		<%-- DELETE DIALOG --%>
		<c:forEach var="bean" items="${requestScope.questions}">
			<div id="${bean.id}" class="modalDialog">
				<div>
					<form  action="controller" method="POST">
			            <div class="form-group">
			            	<label for="text"><fmt:message key='updateTest_jsp.deletemes'/> ${bean.body}?</label>
							<input type="hidden" name="command" value="delete">
							<input type="hidden" name="idTest" value="${requestScope.test.id}">
							<input type="hidden" name="idQuestion" value="${bean.id}" />
							<input class="btn btn-default btn-lg" type="submit" value='<fmt:message key='updateTest_jsp.delete'/>'>
							<a href="#close" title="Close" class="btn btn-default btn-lg"><fmt:message key='updateTest_jsp.close'/></a>
						</div>
					</form >
				</div>
			</div>
		</c:forEach>
		<%-- DELETE DIALOG --%>
		
		<%-- UPLOAD FILE DIALOG --%>
		<div id="upload" class="modalDialog">
			<div>
				<label class="control-label"><fmt:message key='updateTest_jsp.select'/></label>
				<form action="uploadFile" method="post" enctype="multipart/form-data">
					<input type="hidden" name="idTest" value="${requestScope.test.id}" />			                        
					<div class="input-group">
		                <input class="form-control input-lg input-xlarge" readonly>
		                <span class="input-group-btn">
		                    <span class="btn btn-primary btn-file">
		                        <fmt:message key='updateTest_jsp.browse'/>&hellip; <input name="file" type="file" multiple>
		                    </span>
		                </span>
	         		</div>
					<input type="submit" class="btn btn-default btn-lg" value="<fmt:message key='updateTest_jsp.upload'/>"/>
					<a href="#close" title="Close" class="btn btn-default btn-lg"><fmt:message key='updateTest_jsp.close'/></a>
				</form>
			</div>
		</div>
		<%-- UPLOAD FILE DIALOG --%>
	</div>
</div>
	<%-- CONTENT --%>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>