<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery(".dependencies").toggle();
		jQuery("a#toggleLink").click(function () {
			jQuery(".dependencies").toggle();
			});
		
		jQuery("a#customExporter").click(function() {
			var selectedChk = '';
			 jQuery("input:checkbox:checked").each(function() {
		         selectedChk += jQuery(this).val()+"-";
		     });
			 if(selectedChk == ''){
				 alert("Must select modules to download");
			 }else{
			 window.location.href = "${pageContext.request.contextPath}/module/omodexport/exportFile.form?exportType=CUSTOM&moduleId="+selectedChk;
			  }
			
		});
	});
</script>

<div class="boxHeader">
	<b><spring:message code="omodexport.export.header" />
	</b><a id="toggleLink" style="display: block; float: right" href="#"> <spring:message
			code="omodexport.toggleDependencies" /> </a> </b>
</div>
<div class="box">
	<table cellpadding="2" cellspacing="0" width="100%">
		<tr>
			<th><spring:message code="general.name" />
			</th>
			<th><spring:message code="general.version" />
			</th>
			<th></th>
			<th></th>
			<th></th>
		</tr>
		<c:forEach var="moduleMap" items="${modules}" varStatus="status">
			<tr class='${status.index % 2 == 0 ? "evenRow" : "oddRow"}'>
				<td><input type="checkbox" name=""
					value="${moduleMap.key.moduleId}" />${moduleMap.key.name}</td>
				<td>${moduleMap.key.version}</td>
				<td></td>
				<td>
				<a href="${pageContext.request.contextPath}/module/omodexport/exportFile.form?moduleId=${moduleMap.key.moduleId}&exportType=SINGLE"><spring:message code="omodexport.export.single" /></a>
				</td>
				<td>
				<a href="${pageContext.request.contextPath}/module/omodexport/exportFile.form?moduleId=${moduleMap.key.moduleId}&exportType=WITH_DEPENDECIES"><spring:message code="omodexport.export.withDependencies" /></a>
				</td>
			</tr>
			<c:forEach var="dependency" items="${moduleMap.value}">
				<tr class="dependencies">
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*${dependency.name}
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</c:forEach>
		</c:forEach>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>