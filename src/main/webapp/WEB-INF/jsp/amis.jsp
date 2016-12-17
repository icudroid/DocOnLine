<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<c:if test="${(empty compte)}">
<jsp:forward page="/welcome.do"></jsp:forward>
</c:if>
<script src='dwr/interface/Compte.js'> </script>
<script src='dwr/engine.js'> </script>
<script src='dwr/util.js'> </script>

<script language="javascript">
function addCompteAmi(){
	Compte.addCompteAmi(document.getElementById("pseudo").value,function (){
		document.getElementById("pseudo").value = "";
		Compte.getComptesAmis(function (data){
			redrawList(data);
		});
	});
}
function delCompteAmi(){
Compte.delCompteAmi(document.getElementById("pseudo").value,function (){
		document.getElementById("pseudo").value = "";
		Compte.getComptesAmis(function (data){
			redrawList(data);
		});
	});
}
function redrawList(list){
var amis =  document.getElementById("amis");
amis.options.length = list.length;
for(var i=0;i<list.length;i++){
	amis.options[i].value = list[i].pseudo;
	amis.options[i].text = list[i].pseudo;
}
}
function changePseudo(){
	document.getElementById("pseudo").value = document.getElementById("amis").value;
}
</script>
<table width="100%" style="background-color: #FFFFFF;padding: 5px;">
	<tr>
		<td><a href="goHome.do">Retour</a></td>
	</tr>
	<tr>
		<td valign="top">
			Liste des comptes amis : <br />
			<select size="10" onclick="javascript:changePseudo()" id="amis">
				<c:forEach items="${amis}" var="ami">
					<option value="${ami.pseudo}">${ami.pseudo}</option>
				</c:forEach>
			</select>
		</td>
		<td valign="top">
			<a href="javascript:addCompteAmi()" >Ajouter</a><br />
			<a href="javascript:delCompteAmi()" >Supprimer</a>
		</td>
		<td valign="top">
			Pseudo : <input type="text" id="pseudo"><br />
<!--			Choix du répertoire Partagé :<span id="nomFolder"></span>-->
		</td>
<!--		<td valign="top">-->
<!--			Liste des répertoires :<br />-->
<!--			<div style="overflow: auto;height: 500px">-->
<!--				<c:forEach items="${repertoires}" var="r">-->
<!--					<a href="javascript:selectFolder('${r.id}','${r.nom}')"><img src="img/folder.gif" />${r.nom}</a><br />-->
<!--				</c:forEach>-->
<!--		<div>-->
<!--		</td>-->
	</tr>
</table>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>