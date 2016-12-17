<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link type="text/css" rel="stylesheet" href="css/homePage.css" />
<link type="text/css" rel="stylesheet" href="css/splash.css" />

<title>Diaparama</title>

<!-- GESTION JSEPATH/VERSION -->
<script type="text/javascript">
document.write("<" +"script src='jse/version.js?rnd="+(new Date()).getTime()+"'><"+"/script>" );
</script>
<!-- GESTION JSEPATH/VERSION -->
<!-- INJECTION JSE -->
<script type="text/javascript">
document.write("<" +"script src='"+JSEPath+"JSE/JSE.js'><"+"/script>" );
</script>
<!-- INJECTION JSE -->
    
<script type="text/javascript">
    _VMVersion="";
    _FwkFromParam="";
    _FwkLang="fra"
    _YMTC="";
</script>


<script type="text/javascript" src="js/splash.js"></script>

<script src='dwr/interface/Folder.js'> </script>
<script src='dwr/interface/File.js'> </script>
<script src='dwr/engine.js'> </script>
<script src='dwr/util.js'> </script>
<script type="text/javascript">

function retour(){
retour = getParameter("from");
	if(retour =="")
		location.href = "goHome.do";
	else
		location.href = retour;

};

getParameter = function(name)
{
	var str = location.search;
		if(str.indexOf(name+"=") ==-1)
			return "";
	var idxStart = str.indexOf(name+"=") + name.length + 1;
	var idxEnd = ((str.indexOf("&",idxStart) != -1) ? str.indexOf("&",idxStart) : str.length);
	return str.substring(idxStart,idxEnd);
};

JSEImport("Dkahn.UI.Diaporama");
	function diaparama(){
		File.getUrlImageFromFolder(<%=request.getParameter("idFolder")%>,function (data){
		var diaporama = new Dkahn.UI.Diaporama();
		$("diapo").appendChild(diaporama.HTMLElement);
		diaporama.init(data);
		diaporama.plugWire(new JSEWire("onStop",retour));
		});
	}		
</script>
</head>

<body onload="javascript:diaparama();"
	style="background: rgb(0, 0, 0) none repeat scroll 0%; overflow: hidden;">

<div id="diapo" style="height: 100%">

</div>
</body>
</html>
