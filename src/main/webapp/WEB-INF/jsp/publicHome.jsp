<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <meta name="description" content="" />
        <meta name="keywords" content="" />
        <link rel="stylesheet" href="../css/homePage.css" type="text/css" media="screen,projection" />
        <title>Documents</title>

    </head>
    <body>
    
    <div id="page-block">
	<div id="logo-block">
		<div id="dojo-logo" style="float: left;">
			<a title="Home" href="/DocsOnLine/goHome.do"><img alt="DocsOnLine" src="../img/banner-logo-text.gif" id="logo-img"/></a>
		</div>
		<div id="search-block" style="float: right;">
		</div>	
	</div>
	
	<div style="clear: both;"></div>
	
	
<script src='../js/publicHome.js'> </script>
<script src='../dwr/interface/Folder.js'> </script>
<script src='../dwr/interface/File.js'> </script>
<script src='../dwr/interface/Compte.js'> </script>
<script src='../dwr/interface/Session.js'> </script>
<script src='../dwr/engine.js'> </script>
<script src='../dwr/util.js'> </script>

<script language="javascript">
	var courantFolder = "${folderPublic}";
	var idCompte = "${comptePublic.id}";
	var pseudoCompte = "${comptePublic.pseudo}";
</script>

	<div id="compte">
		<table width="100%">
			<tr>
				<td align="left">
					<span style="color: #FFFFFF;font-size: 16pt;">${comptePublic.pseudo}</span><br />
				</td>
			</tr>
		</table>
	</div>
	
	<div class="dossier">
		<table width="100%">
			<tr>
				<td align="left" >
					<img src="../img/public_folder.gif"/>
				</td>
				<td align="left" style="font-size: 20px;font-weight: bold;">
					Dossiers Public
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
					Options sur le dossier public courant : <a href="javascript:goRepParent()">Dossier parent</a> | <a href="javascript:goDiaporama();">Diaporama</a> | <a href="javascript:downloadFoler();">Télécharger</a>
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
	if( idCompte !="" && courantFolder !=""){
		fillFolder(courantFolder);
	}
	
</script>	
	<div style="clear:both;"></div>
	<div id="menu-footer-block">
					<div id="menu-footer-left"> </div>
					<div id="menu-footer-right"> </div>
	</div>
    </body>
</html>

