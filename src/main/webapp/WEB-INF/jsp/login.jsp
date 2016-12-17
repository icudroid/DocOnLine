<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>


 
<div>
<table width="100%" style="background-color: #FFFFFF;padding: 5px;">
<tr><td>


<c:out value="${erreurLogin}"></c:out>
<html:form action="login">
	<table>
		<tr>
			<td>Pseudo :</td>
			<td><html:text property="pseudo"></html:text></td>
			<td><html:errors property="pseudo"/></td>
		</tr>
		<tr>
			<td>mot de passe :</td>
			<td><html:password property="pwd"></html:password></td>
			<td><html:errors property="pwd"/></td>
		</tr>
		<tr>
			<td colspan="3"><input type="submit" value="OK" /></td>
		</tr>
	</table>
</html:form>

<a href="goForgotPwd.do">Oublié votre mot de passe</a>
<a href="goCreateCompte.do">Création d'un compte</a>
</td></tr></table>
<div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>