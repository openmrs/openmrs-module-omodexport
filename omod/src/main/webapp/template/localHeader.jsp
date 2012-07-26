<spring:htmlEscape defaultHtmlEscape="true" />
<ul id="menu">
	<li class="first"><a
		href="${pageContext.request.contextPath}/admin"><spring:message
				code="admin.title.short" /></a></li>

	<li
		<c:if test='<%= request.getRequestURI().contains("/exportModules") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/admin/modules/module.list"><spring:message
				code="omodexport.manage" /></a>
	</li>
	
	<li
		<c:if test='<%= request.getRequestURI().contains("/exportModules") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/omodexport/exportFile.form?exportType=ALL"><spring:message
				code="omodexport.export.all" /></a>
	</li>
	
	<li
		<c:if test='<%= request.getRequestURI().contains("/exportModules") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/omodexport/exportFile.form?moduleId=${moduleMap.key.moduleId}&exportType=CUSTOM"><spring:message
				code="omodexport.export.selected" /></a>
	</li>
	
	<!-- Add further links here -->
</ul>
<h2>
	<spring:message code="omodexport.exportModules" />
</h2>
