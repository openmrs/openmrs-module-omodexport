<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery(".dependencies").toggle();
		jQuery("a#toggleLink").click(function () {
			jQuery(".dependencies").toggle();
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
				<td></td>
				<td></td>
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