<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<c:if test="${(empty compte)}">
<jsp:forward page="/welcome.do"></jsp:forward>
</c:if>
<div>
<table width="100%" style="background-color: #FFFFFF; padding: 5px;">
	<tr>
		<td valign="top">
		Mes comptes amis :<br />
		<div id="amis" style="overflow: auto;height: 500px;"></div>
		</td>
		<td valign="top">
		<html:form action="addCompteAmi">
		Pseudo du compte : <html:text property="pseudo"></html:text>
		<input type="submit" value="Ajouter"/>
		<input type="button" value="Supprimer" onclick="javascript:deleteCompteAmi();"/>
		</html:form>
		</td>
	</tr>
</table>
</div>
<div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>