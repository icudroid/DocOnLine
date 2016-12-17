<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@page import="net.dkahn.metier.Repertoire"%>

<c:if test="${(empty compte)}">
<jsp:forward page="/welcome.do"></jsp:forward>
</c:if>

<script src='js/homePage.js'> </script>
<script src='dwr/interface/Folder.js'> </script>
<script src='dwr/interface/File.js'> </script>
<script src='dwr/engine.js'> </script>
<script src='dwr/util.js'> </script>


<script language="javascript">
	var privateCourantFolder=${private};
	var shareCourantFolder=${share};
	var publicCourantFolder=${public};
	
	var idCompte = ${compte.id};
	var repPublicCst = <%=Repertoire.REPERTOIRE_PUBLIC%>;
	var repPrivateCst = <%=Repertoire.REPERTOIRE_PRIVE%>;
	var repShareCst = <%=Repertoire.REPERTOIRE_PARTAGE%>;
</script>

<script type="text/javascript">
	load();
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
					<a href="showPartageAmis.do" style="color: #FFFFF0">Voir les documents partagés de mes amis</a><br />
					<a href="goModCompte.do" style="color: #FFFFF0">Modifier mon compte</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="dossier">
		<table width="100%">
			<tr>
				<td align="left" >
					<img src="img/lock_folder.gif"/>
				</td>
				<td align="left" style="font-size: 20px;font-weight: bold;">
					Dossiers Privés
				</td>
				<td align="right">
					<a href="javascript:goRepParent(1)">Dossier parent</a>| 
					<a href="javascript:goAddCreatePrivateFolder()">Créer un dossier</a>|
					<a href="javascript:goAddPrivateFiles()">Ajouter des fichiers</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="option">
		<table  width="100%">
			<tr>
				<td align="right">
					Options sur le dossier privé courant : <a href="javascript:goDiaporama(1);">Diaporama</a> | <a href="javascript:deleteCourantFoler(1);">Supprimer</a> | <a href="">Renommer</a> | <a href="">Déplacer</a> | <a href="">Copier</a> | <a href="javascript:downloadFoler(1);">Télécharger</a>
				</td>
			</tr>
			<tr>
				<td align="left"  style="padding: 5px;float: left">
					<span>Repertoire courant : </span><span id="repPrivateOption"></span>
				</td>
			</tr>
		</table>
	</div>
	<div>
	<table width="100%" style="background-color: #FFFFFF;padding: 5px;border:1px solid #878787;"><tr><td id="repPrivateContent"></td></tr></table>
	</div>
	<div style="clear:both;"></div>
	
	
	
	
	
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
					<a href="javascript:goRepParent(2)">Dossier parent</a>| 
					<a href="javascript:goAddCreateShareFolder()">Créer un dossier</a>|
					<a href="javascript:goAddShareFiles()">Ajouter des fichiers</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="option">
		<table  width="100%">
			<tr>
				<td align="right">
					Options sur le dossier partagé courant : <a href="javascript:goDiaporama(2);">Diaporama</a> | <a href="javascript:deleteCourantFoler(2);">Supprimer</a> | <a href="">Renommer</a> | <a href="">Déplacer</a> | <a href="">Copier</a> | <a href="javascript:downloadFoler(2);">Télécharger</a>
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
	
	
	
	<div class="dossier">
		<table width="100%">
			<tr>
				<td align="left" >
					<img src="img/public_folder.gif"/>
				</td>
				<td align="left" style="font-size: 20px;font-weight: bold;">
					Dossiers Publics
				</td>
				<td align="right">
					<a href="javascript:goRepParent(0)">Dossier parent</a>| 
					<a href="javascript:goAddCreatePublicFolder()">Créer un dossier</a>|
					<a href="javascript:goAddPublicFiles()">Ajouter des fichiers</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="option">
		<table  width="100%">
			<tr>
				<td align="right">
					Options sur le dossier public courant : <a href="javascript:goDiaporama(0);">Diaporama</a> | <a href="javascript:deleteCourantFoler(0);">Supprimer</a> | <a href="">Renommer</a> | <a href="">Déplacer</a> | <a href="">Copier</a> | <a href="javascript:downloadFoler(0);">Télécharger</a>
				</td>
			</tr>
			<tr>
				<td align="left"  style="padding: 5px;float: left">
					<span>Repertoire courant : </span><span id="repPublicOption"></span>
				</td>
			</tr>
		</table>
	</div>
	<div>
	<table width="100%" style="background-color: #FFFFFF;padding: 5px;border:1px solid #878787;"><tr><td id="repPublicContent" ></td></tr></table>
	</div>
	
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
