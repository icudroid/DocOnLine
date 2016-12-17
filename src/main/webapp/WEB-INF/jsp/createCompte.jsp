<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<c:out value="${erreurCreate}"></c:out>
<div>
<html:form action="createCompte">
	<table width="100%" style="background-color: #FFFFFF;padding: 5px;">
		<tr>
			<td>
				<a href="welcome.do">Page de login</a>
			</td>
		</tr>
		<tr>
			<td>Pseudo :</td>
			<td><html:text property="pseudo"></html:text></td>
			<td><html:errors property="pseudo"/></td>
		</tr>
		<tr>
			<td>Email :</td>
			<td><html:text property="email"></html:text></td>
			<td><html:errors property="email"/></td>
		</tr>
		<tr>
			<td>Mot de passe :</td>
			<td><html:password property="motDePasse"></html:password></td>
			<td><html:errors property="motDePasse"/></td>
		</tr>
		<tr>
			<td>Confirmation :</td>
			<td><html:password property="motDePasseConfirm"></html:password></td>
			<td><html:errors property="motDePasseConfirm"/></td>
		</tr>
		<tr>
			<td colspan="3"><input type="submit" value="Créer" /></td>
		</tr>
		
	</table>
</html:form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>