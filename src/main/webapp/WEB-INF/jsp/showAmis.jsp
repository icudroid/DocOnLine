<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<c:if test="${(empty compte)}">
<jsp:forward page="/welcome.do"></jsp:forward>
</c:if>

<script src='js/showAmis.js'> </script>
<script src='dwr/interface/Folder.js'> </script>
<script src='dwr/interface/File.js'> </script>
<script src='dwr/interface/Compte.js'> </script>
<script src='dwr/interface/Session.js'> </script>
<script src='dwr/engine.js'> </script>
<script src='dwr/util.js'> </script>

<script language="javascript">
	var courantFolder = "${folder}";
	var idCompteAmi = "${compteAmi}";
</script>

	<div id="compte">
		<table width="100%">
			<tr>
				<td align="left">
					<span style="color: #FFFFFF;font-size: 16pt;">${compte.pseudo}</span><br />
					<span style="color: #FFFFFF;font-size: 16pt;">${compte.email}</span>
				</td>
				<td align="right">
					<a href="gestionAmis.do" style="color: #FFFFF0">Gestion des comptes amis</a><br />
					<a href="goModCompte.do" style="color: #FFFFF0">Modifier mon compte</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="choixCompteAmi" class="dossier">
		<table width="100%">
			<tr>
				<td>
					Comptes amis : 
					<select onchange="javascript:changePseudo()" id="amis">
						<option value=""></option>
						<c:forEach items="${amis}" var="ami">
							<option value="${ami.id}">${ami.pseudo}</option>
						</c:forEach>
					</select>
				</td>
				<td align="right">
					<a href="goHome.do">Retour à ma page d'acceuil</a>
				</td>
			</tr>
		</table>
	</div>
	
	
	<div class="dossier">
		<table width="100%">
			<tr>
				<td align="left" >
					<img src="img/share_folder.gif"/>
				</td>
				<td align="left" style="font-size: 20px;font-weight: bold;">
					Dossiers Partagés
				</td>
				<td align="right">
					
				</td>
			</tr>
		</table>
	</div>
	<div class="option">
		<table  width="100%">
			<tr>
				<td align="right">
					Options sur le dossier partagé courant : <a href="javascript:goRepParent()">Dossier parent</a> | <a href="javascript:goDiaporama();">Diaporama</a> | <a href="javascript:downloadFoler();">Télécharger</a>
				</td>
			</tr>
			<tr>
				<td align="left"  style="padding: 5px;float: left">
					<span>Repertoire courant : </span><span id="repShareOption"></span>
				</td>
			</tr>
		</table>
	</div>
	<div>
	<table width="100%" style="background-color: #FFFFFF;padding: 5px;border:1px solid #878787;"><tr><td id="repShareContent"></td></tr></table>
	</div>
	<div style="clear:both;"></div>
	
<script type="text/javascript">
	if( idCompteAmi !="" && courantFolder !=""){
		document.getElementById("amis").value = idCompteAmi;
		fillFolder(courantFolder);
	}
	
</script>	
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
