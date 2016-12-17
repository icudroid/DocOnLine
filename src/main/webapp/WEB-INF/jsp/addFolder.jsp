<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<c:if test="${(empty compte)}">
<jsp:forward page="/welcome.do"></jsp:forward>
</c:if>
<div>
<table  width="100%" style="background-color: #FFFFFF;padding: 5px;">
<tr>
<td><a href="goHome.do">retour</a></td>
</tr>
<tr>
	<td valign="top">
		Vous allez créer un répertoire dans : ${repertoire.nom}<br>
		<html:form action="${action}">
			nom du dossier : <html:text property="nom"></html:text><br />
			<html:hidden property="idFolder" value="${repertoire.id}"/>
			Description : <br /><html:textarea property="description"></html:textarea>
			<html:submit>Ajouter</html:submit>
		</html:form>
	</td>
	<td>
		
	</td>
</tr>
</table>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
