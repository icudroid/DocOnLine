<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>

<link rel="stylesheet" href="css/homePage.css" type="text/css" media="screen,projection" />

<jsp:useBean id="repertoires" class="java.util.ArrayList" scope="request" />
<script language="javascript">
function upload(){
    parent.startProgress();
};
function selectFolder(id,nom){
	document.getElementById("nomFolder").innerHTML = nom;
	document.forms["filesForm"].elements["idFolder"].value = id;;
};
</script>

<table width="100%" style="background-color: #FFFFFF;padding: 5px;">
<tr>
	<td valign="top">
	Liste des répertoires :<br />
	<div style="overflow: auto;height: 500px">
	<%	
		for (java.util.Iterator<net.dkahn.web.dwrs.RemoteFolder> iterator = repertoires.iterator(); iterator.hasNext();) {
		net.dkahn.web.dwrs.RemoteFolder r =  iterator.next();
		%>
		<a href="javascript:selectFolder('<%=r.getId()%>','<%=r.getNom() %>')"><img src="img/folder.gif" /><%=r.getNom() %></a><br />
	<% } %>
	<div>
	</td>
	<td>
		<html:form action="addFiles" enctype="multipart/form-data" method="post" onsubmit="upload();">
		<p>
		dans : <span id="nomFolder"><%=((net.dkahn.web.dwrs.RemoteFolder)repertoires.get(0)).getNom()%></span><br />
		<html:hidden property="idFolder" value="<%=((net.dkahn.web.dwrs.RemoteFolder)repertoires.get(0)).getId().toString()%>"/>
		<nested:file styleId="file1" property="file1" styleClass="default" /><br />
		Description : <br /><html:textarea property="description1"></html:textarea><br />
		<nested:file styleId="file2" property="file2" styleClass="default" /><br />
		Description : <br /><html:textarea property="description2"></html:textarea><br />
		<nested:file styleId="file3" property="file3" styleClass="default" /><br />
		Description : <br /><html:textarea property="description3"></html:textarea><br />
		<nested:file styleId="file4" property="file4" styleClass="default" /><br />
		Description : <br /><html:textarea property="description4"></html:textarea><br />
		<html:submit value="begin upload" styleId="uploadbutton" /> <br />
		</p>
</html:form>
	</td>
</tr>
</table>


<c:if test="${(not empty uploadLog )}">
	<script language="javascript">
window.onload = function () {
            parent.updateStatusMessage("<c:out value="${uploadLog}" escapeXml="false" />");
            parent.hideProgressBar();
}
</script>
</c:if>
