var privateFolderList = new Array();
var shareFolderList = new Array();
var publicFolderList = new Array();


function goDiaporama(type){
var rep ="";
	switch(type){
		case 0 :
		rep = publicCourantFolder;
		break;
		case 1 :
		rep = privateCourantFolder;
		break;
		case 2 :
		rep = shareCourantFolder;
		break;
	}
	
	location.href = "diaporama.do?idFolder="+rep;
}

function goAddCreatePrivateFolder(){
	location.href = "goAddPrivateFolder.do?idFolder="+privateCourantFolder;
};
function goAddCreateShareFolder(){
	location.href = "goAddShareFolder.do?idFolder="+shareCourantFolder;
};
function goAddCreatePublicFolder(){
	location.href = "goAddPublicFolder.do?idFolder="+publicCourantFolder;
};
function goAddPrivateFiles(){
	location.href = "goPrivateUploadForm.do?idFolder="+privateCourantFolder;
};
function goAddShareFiles(){
	location.href = "goShareUploadForm.do?idFolder="+shareCourantFolder;
};
function goAddPublicFiles(){
	location.href = "goPublicUploadForm.do?idFolder="+publicCourantFolder;
};

function fillFolder(rep,content,type){
	var contentOption = "";
	switch(type){
		case 0 :
		publicCourantFolder = rep;
		contentOption="repPublicOption";
		break;
		case 1 :
		privateCourantFolder = rep;
		contentOption="repPrivateOption";
		break;
		case 2 :
		shareCourantFolder = rep;
		contentOption="repShareOption";
		break;
	}
  Folder.getFolders(rep,idCompte,type,null, function(data) {
    var list = data;
    var i = 0;
    var html = "";
    for(i=0;i<list.length;i++){
    	//html += "<a href='javascript:fillFolder("+list[i].id+",\""+content+"\","+type+")'>"+list[i].nom+"</a>|";
    	html += drawRepertoire(list[i],type,content);
    }
    document.getElementById(content).innerHTML = html;
    Folder.getFoldersInfoPath(rep,function(data) {
    	var i = data.length-1;
    	var html = "";
	    for(i;i>=0;i--){
	    	
	    	html += "<a href='javascript:fillFolder("+data[i].id+",\""+content+"\","+type+")'>"+data[i].nom+"</a>";
	    	if(data[i].nom != "/")
	    		html += " / ";
	    	else
	    	html += " ";
	    }
    	document.getElementById(contentOption).innerHTML = html;
    });
    File.getFiles(rep,function(data) {
    	var list = data;
	    var i = 0;
	    var html = "<div id='tileView' class='tvContainer'>";
	    for(i=0;i<list.length;i++){
	    	//html += "<img alt=\"\" src=\""+list[i].thumbPath+"\" />"+list[i].nom+"|";
	    	html += drawFichier(list[i]);
	    }
	    html += "</div>";
    	document.getElementById(content).innerHTML += html;
    });
  });
};
function goShowFile(id){
	window.location.href = "showFile.do?idFile="+id;
};

function drawFichier(fichier){
var html = "<div class='item' style='float:left;padding:5px;cursor:pointer;'>";
html+=  "<table height='100%' cellpadding='0' cellspacing='0' onclick='javascript:goShowFile("+fichier.id+")'>";
html+=  "<tr>";
html+=  "	<td style='text-align:center;' height='105px'>";
html+=  "   	 <img src='"+fichier.thumbPath+"' alt='' class='imageItem'/>    </td>";
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

function drawRepertoire(rep,type,content){
var html = "<div class='item' style='cursor:pointer;float:left;padding:5px;' onclick='javascript:fillFolder("+rep.id+",\""+content+"\","+type+")'>";
html+=  "<table height='100%' cellpadding='0' cellspacing='0'>";
html+=  "<tr>";
html+=  "	<td style='text-align:center;' height='105px'>";
html+=  "   	 <img src='"+rep.urlImage+"' alt='' class='imageItem'/>    </td>";
html+=  "</tr>";
html+=  "<tr>";
html+=  "<td valign='bottom' style='text-align:center'>";
html+=  "   		<a href='javascript:if (event.stopPropagation) { event.stopPropagation();}event.cancelBubble = true;fillFolder("+rep.id+",\""+content+"\","+type+")'><span class='nomItem'>"+rep.nom+"</span></a>";
html+=  "    </td>";
html+=  "</tr>";
html+=  "</table>";
html+=  "</div>";
html+=  "<div style='float:left;padding:5px;height:150px;'></div>";
return html;
};
function load() {
	fillFolder(privateCourantFolder,"repPrivateContent",repPrivateCst);
	fillFolder(publicCourantFolder,"repPublicContent",repPublicCst);
	fillFolder(shareCourantFolder,"repShareContent",repShareCst);
};

function goRepParent(type){
var rep ="";
	switch(type){
		case 0 :
		rep = publicCourantFolder;
		break;
		case 1 :
		rep = privateCourantFolder;
		break;
		case 2 :
		rep = shareCourantFolder;
		break;
	}
	
	Folder.getParent(rep,null,function(data){
	var content = "";
		switch(type){
			case 0 :
			content = "repPublicContent";
			break;
			case 1 :
			content = "repPrivateContent";
			break;
			case 2 :
			content = "repShareContent";
			break;
		}
		fillFolder(data.id,content,type)
	});
};

function deleteCourantFoler(type){
var rep ="";
var content = "";
	switch(type){
		case 0 :
		rep = publicCourantFolder;
		content = "repPublicContent";
		break;
		case 1 :
		rep = privateCourantFolder;
		content = "repPrivateContent";
		break;
		case 2 :
		rep = shareCourantFolder;
		content = "repShareContent";
		break;
	}
	
	Folder.deleteFolder(rep,null,function(data){
		fillFolder(data,content,type);
	});
};

function downloadFoler(type){
var rep ="";
	switch(type){
		case 0 :
		rep = publicCourantFolder;
		break;
		case 1 :
		rep = privateCourantFolder;
		break;
		case 2 :
		rep = shareCourantFolder;
		break;
	}
	
window.location.href = "DownloadRep/"+rep+".zip";
};
