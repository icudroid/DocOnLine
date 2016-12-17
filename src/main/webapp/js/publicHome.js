function goDiaporama(){
	location.href = "../diaporama.do?idFolder="+courantFolder+"&from=home/"+pseudoCompte;
}

function fillFolder(rep){
	courantFolder = rep;
  Folder.getFolders(rep,idCompte,0,"folderPublic", function(data) {
    var list = data;
    var i = 0;
    var html = "";
    for(i=0;i<list.length;i++){
    	html += drawRepertoire(list[i]);
    }
    document.getElementById("repShareContent").innerHTML = html;
    Folder.getFoldersInfoPath(rep,function(data) {
    	var i = data.length-1;
    	var html = "";
	    for(i;i>=0;i--){
	    	
	    	html += "<a href='javascript:fillFolder("+data[i].id+")'>"+data[i].nom+"</a>";
	    	if(data[i].nom != "/")
	    		html += " / ";
	    	else
	    	html += " ";
	    }
    	document.getElementById("repShareOption").innerHTML = html;
    });
    File.getFiles(rep,function(data) {
    	var list = data;
	    var i = 0;
	    var html = "<div id='tileView' class='tvContainer'>";
	    for(i=0;i<list.length;i++){
	    	html += drawFichier(list[i]);
	    }
	    html += "</div>";
    	document.getElementById("repShareContent").innerHTML += html;
    });
  });
};

function goShowFile(id){
	window.location.href = "../showPublicFile.do?idFile="+id+"&from=home/"+pseudoCompte;
};

function drawFichier(fichier){
var html = "<div class='item' style='float:left;padding:5px;cursor:pointer;'>";
html+=  "<table height='100%' cellpadding='0' cellspacing='0' onclick='javascript:goShowFile("+fichier.id+")'>";
html+=  "<tr>";
html+=  "	<td style='text-align:center;' height='105px'>";
html+=  "   	 <img src='../"+fichier.thumbPath+"' alt='' class='imageItem'/>    </td>";
html+=  "</tr>";
html+=  "<tr>";
html+=  "<td valign='bottom' style='text-align:center'>";
html+=  "   		<a href='javascript:goShowFile("+fichier.id+")'><span class='nomItem'>"+fichier.nom+"</span></a>";
html+=  "    </td>";
html+=  "</tr>";
html+=  "</table>";
html+=  "</div>";
html+=  "<div style='float:left;padding:5px;height:150px;'></div>";
return html;
};

function drawRepertoire(rep){
var html = "<div class='item' style='cursor:pointer;float:left;padding:5px;' onclick='javascript:fillFolder("+rep.id+")'>";
html+=  "<table height='100%' cellpadding='0' cellspacing='0'>";
html+=  "<tr>";
html+=  "	<td style='text-align:center;' height='105px'>";
html+=  "   	 <img src='../"+rep.urlImage+"' alt='' class='imageItem'/>    </td>";
html+=  "</tr>";
html+=  "<tr>";
html+=  "<td valign='bottom' style='text-align:center'>";
html+=  "   		<a href='javascript:if (event.stopPropagation) { event.stopPropagation();}event.cancelBubble = true;fillFolder("+rep.id+")'><span class='nomItem'>"+rep.nom+"</span></a>";
html+=  "    </td>";
html+=  "</tr>";
html+=  "</table>";
html+=  "</div>";
html+=  "<div style='float:left;padding:5px;height:150px;'></div>";
return html;
};

function goRepParent(){
	Folder.getParent(courantFolder,"folderPublic",function(data){
		fillFolder(data.id)
	});
};


function downloadFoler(){
	window.location.href = "../DownloadRep/"+courantFolder+".zip";
};
