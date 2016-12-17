<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<c:if test="${(empty compte)}">
<jsp:forward page="/welcome.do"></jsp:forward>
</c:if>



<script src='dwr/interface/File.js'> </script>
<script src='dwr/engine.js'> </script>
<script src='dwr/util.js'> </script>

<script language="javascript">
function deleteFile(){
	File.deleteFile(${img.id},function() {
		window.location.href = "goHome.do";
	});
};
function renommeFile(){

};
function deplacerFile(){
	
};
function copierFile(){

};
function download(){
	window.open("${img.url}");
};

function getPageSize (){
		var xScroll, yScroll;
		if (window.innerHeight && window.scrollMaxY) {	
			xScroll = document.body.scrollWidth;
			yScroll = window.innerHeight + window.scrollMaxY;
		} else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
			xScroll = document.body.scrollWidth;
			yScroll = document.body.scrollHeight;
		} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
			xScroll = document.body.offsetWidth;
			yScroll = document.body.offsetHeight;
		}
		var windowWidth, windowHeight;
		if (self.innerHeight) {	// all except Explorer
			windowWidth = self.innerWidth;
			windowHeight = self.innerHeight;
		} else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
			windowWidth = document.documentElement.clientWidth;
			windowHeight = document.documentElement.clientHeight;
		} else if (document.body) { // other Explorers
			windowWidth = document.body.clientWidth;
			windowHeight = document.body.clientHeight;
		}	
		// for small pages with total height less then height of the viewport
		if(yScroll < windowHeight){
			pageHeight = windowHeight;
		} else { 
			pageHeight = yScroll;
		}
		// for small pages with total width less then width of the viewport
		if(xScroll < windowWidth){	
			pageWidth = windowWidth;
		} else {
			pageWidth = xScroll;
		}
		arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight) 
		return arrayPageSize;
	};

function loadImage(inImg,inMW, inMH){
  var oImg = document.createElement("img");
  oImg.src = inImg;
  oImg.onload = function()
	{
		redimImage(this,inMW, inMH);
	};

};

function redimImage(inImg, inMW, inMH)
{

	var im = new Image();
	im.src = inImg.src;
  var maxWidth = inMW;
  var maxHeight = inMH;
  var dW = 0;
  var dH = 0;
	var h = dH = im.height;
	var w = dW = im.width;
 var facteur =0;
 
   	if (w > h && w > maxWidth) {
		facteur = w / maxWidth;
	} else if (h > maxHeight) {
		facteur = h / maxHeight;
	}else if (w > h && w < maxWidth) {
		facteur = h / maxHeight;
	}else if (h < maxHeight) {
		facteur = h / maxHeight;
	}
	
	dW =  parseInt((w / facteur));
	dH =  parseInt((h / facteur));

	inImg.style.width = dW+"px";
	inImg.style.height = dH+"px";
	document.getElementById("imgContent").appendChild(inImg);
  // On ecrit l'image dans le document
  //document.writeln("<img class=\"imageItem\" src=\"" + inImg.src + "\" width=\"" + dW + "\" height=\"" + dH + "\" border=\"0\">");
};

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
</script>

<div>
<table width="100%" style="background-color: #FFFFFF; padding: 5px;">
	<tr>
		<td><a href="javascript:retour();">Retour</a></td>
	</tr>
	<tr>
		<td>
		<div style="float: left;">${img.nom}</div>
		<div style="float: right;">Options :
		
		<script language="javascript">
			if(getParameter("from") ==""){
				document.write("<a	href=\"javascript:deleteFile();\">Supprimer</a> | <a"+
					"href=\"javascript:renommeFile();\">Renommer</a> | <a"+
					"href=\"javascript:deplacerFile();\">Déplacer</a> | <a"+
					"href=\"javascript:copierFile();\">Copier</a> |");
			}
		</script>
		  <a href="javascript:download();">Télécharger</a></div>
		</td>
	</tr>
	<tr>
		<td align="center" valign="middle" style="padding: 20px;" id="imgContent">
			<script	type="text/javascript">
					loadImage("${img.bigThumbPath}", 680, getPageSize()[3]-200);
			</script>
		</td>
	</tr>
</table>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
