JSEPackage("Dkahn.UI.Diaporama");

JSEImport("JSE.HTML.UIComponent");

Dkahn.UI.Diaporama = function()
{
	this.extend(JSE.HTML.UIComponent);
	this.prepare();

	this.width = this.getPageSize()[2];
	this.height = this.getPageSize()[3];

	this.HTMLElement.id = "diaporamaUI" + ((new Date()).getTime()) + parseInt(Math.random()*1000000);
	this.HTMLElement.className = "diaporama";

	this.divPhoto = document.createElement("div");
	this.divPhoto.style.width = this.width+"px";
	this.divPhoto.style.height = this.height+"px";
	this.divPhoto.style.position = "relative";
	this.divPhoto.style.overflow = "hidden";
	this.HTMLElement.appendChild(this.divPhoto);

	this.divNavigation = document.createElement("div");
	this.divNavigation.className = "diapoNav";
	this.divNavigation.style.position = "absolute";
	this.divNavigation.style.left =Math.ceil((this.width - 65)/2) + "px";
	this.divNavigation.style.top = this.height-25+"px";
	this.divNavigation.style.display = "none";
	
	var divKeys = document.createElement("div");
	divKeys.style.width = "65px";
	divKeys.style.height = "15px";
	divKeys.style.cssFloat = "left";
	divKeys.style.marginLeft = "10px";

	var imgBack = document.createElement("img");
	navigator.isIE6 ?
		imgBack.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='img/back.png',sizingMethod='scale')" :
		imgBack.src = "img/back.png";
	imgBack.className = "hand diapoKey";
	imgBack.expando = this;
	imgBack.onclick = this._diapoNeg;
	divKeys.appendChild(imgBack);

	this.imgPlay = document.createElement("img");
	navigator.isIE6 ?
		this.imgPlay.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='img/play.png',sizingMethod='scale')" :
		this.imgPlay.src = "img/play.png";
	this.imgPlay.className = "hand diapoKey";
	this.imgPlay.expando = this;
	this.imgPlay.onclick = this._diapoRun;
	divKeys.appendChild(this.imgPlay);

	var imgFwd = document.createElement("img");
	navigator.isIE6 ?
		imgFwd.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='img/fwd.png',sizingMethod='scale')" :
		imgFwd.src = "img/fwd.png";
	imgFwd.className = "hand diapoKey";
	imgFwd.expando = this;
	imgFwd.onclick = this._launchDiapoPos;
	divKeys.appendChild(imgFwd);

	this.divNavigation.appendChild(divKeys);
	
	var divClose = document.createElement("div");
	divClose.style.width = "25px";
	divClose.style.height = "15px";
	divClose.style.cssFloat = "left";
	divClose.style.marginLeft = "10px";

	var imgClose = document.createElement("img");
	navigator.isIE6 ?
		imgClose.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='img/stop.png',sizingMethod='scale')" :
		imgClose.src = "img/stop.png";
	imgClose.className = "hand diapoKey";
	imgClose.expando = this;
	imgClose.onclick = this._launchStop
	divClose.appendChild(imgClose);
	
	this.divNavigation.appendChild(divClose);
	
	this.HTMLElement.appendChild(this.divNavigation);
	window._diaporama = this;
	window.onresize = this._onWindowResize;
	document.onmousemove = this._splash_navig;
};

Dkahn.UI.Diaporama.prototype = {
	JSEwireHub : {
		"onStop" : []
	},
	width : 0,
	height : 0,
	photos : [],
	keys : [],
	position : 0,
	isPlaying : false,
	timeLine : null,
	zIndex : 0,
	navig_mouse :true,
	navig : false,
	// compteur d'images valides
	countImg : 0,
	isFirst : true,
	_onWindowResize : function (){
		_diaporama.width = _diaporama.getPageSize()[2];
		_diaporama.height = _diaporama.getPageSize()[3];
		_diaporama.divPhoto.style.width = _diaporama.width+"px";
		_diaporama.divPhoto.style.height = _diaporama.height+"px";
		_diaporama.divNavigation.style.left =Math.ceil((_diaporama.width - 65)/2) + "px";
		_diaporama.divNavigation.style.top = _diaporama.height-25+"px";
		for (var i=0; i<_diaporama.photos.length; i++) _diaporama.resizeImage(_diaporama.photos[i], (_diaporama.width), (_diaporama.height));
			
		_diaporama._showDiapo(_diaporama.position);
	},
	getPageSize : function(){
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
	},
	// attend le tableau de src et celui optionnel des commentaires
	init : function(diapos)
	{
		//this.divNumbers.innerHTML = "";
		this.divPhoto.innerHTML = "";
		this.keys = new Array();
		this.photos = new Array();
		this.zIndex = 0;
		this.position = 0;
		this.countImg = 0;
		this.isFirst = true;

		for (var i=0; i<diapos.length; i++)
			if (this.testPropValue(diapos[i])) this._loadPhoto(diapos[i]);
		
	},
	// teste la chaine de caracteres
	testPropValue : function(propriety)
	{
		return ((propriety != "") && (propriety != " ") && (typeof(propriety) != "undefined") && (propriety != "0m"));
	},
		// retaille l'image avec des valeurs winWidth et winHeight max tout en gardant le ratio
	resizeImage : function(image, winWidth, winHeight)
	{
		var im = new Image();
		im.src = image.src;
		image.style.width = "auto";
		image.style.height = "auto";
		var x = im.width;
		var y = im.height;
		var facteur = 1;
		var maxX = winWidth;
		var maxY = winHeight;
		var dx =  0;
		var dy =  0;
		
		if (x > y && x > maxX) {
			facteur = x / maxX;
		} else if (y > maxY) {
			facteur = y / maxY;
		}else if (x > y && x < maxX) {
			facteur = y / maxY;
		}else if (y < maxY) {
			facteur = y / maxY;
		}
		
		dx =  parseInt((x / facteur));
		dy =  parseInt((y / facteur));
		//alert(facteur+" " +x+" "+y+" "+dx+" " +dy);
	    image.style.width = dx+"px";
	    image.style.height = dy+"px";
		
	},
	// calcule les parametres style.left et style.top de l'image pour bien la placer au centre
	replaceImage : function(image, winWidth, winHeight)
	{
		var width = image.offsetWidth;
		var height = image.offsetHeight;
		if (width < winWidth)
			image.style.left = Math.ceil((winWidth - width)/2) + "px";
		if (height < winHeight)
			image.style.top = Math.ceil((winHeight - height)/2) + "px";
	},
	// Private
	_loadPhoto : function(path)
	{
		var current = document.createElement("img");
		current.expando = this;
		current.onload = function()
		{
			this.expando.resizeImage(this, (this.expando.width), (this.expando.height));
			this.expando._storePhoto(this);
			this.expando = null;
		};
		current.style.visibility = "hidden";
		current.style.position = "absolute";
		current.style.zIndex = this.zIndex;
		current.src = this.isFirst ? path+"?7" : path;
		this.isFirst = false;
	},
	_storePhoto : function(img)
	{
		var spanTemp = document.createElement("span");
		spanTemp.innerHTML = "&nbsp;" + (this.countImg+1) + "&nbsp;";
		spanTemp.className = "diapoNumber hand";
		spanTemp.photoNumber = this.countImg;
		spanTemp.expando = this;
		spanTemp.onclick = this._changePosition;
		//this.divNumbers.appendChild(spanTemp);
		this.keys.push(spanTemp);
		this.photos.push(img);
		this.divPhoto.appendChild(img);
		this.countImg++;
		if (this.countImg == 1) {
			this._showDiapo(this.position);
			this._start();
		}
	
	},

	// affiche la photo selectionnee
	_showDiapo : function(idx)
	{
		this._hidePhotos();
		this.zIndex++;
		this._resetKeysStyle();
		this.keys[idx].className = "diapoNumberInactive default";
		this.replaceImage(this.photos[idx], this.width, this.height);
		this.photos[idx].style.zIndex = this.zIndex;
		this.photos[idx].style.visibility = "visible";
	},
	_hidePhotos : function()
	{
		for (var i=0; i<this.photos.length; i++) this.photos[i].style.visibility = "hidden";
	},
	_resetKeysStyle : function()
	{
		for (var i=0; i<this.keys.length; i++) this.keys[i].className = "diapoNumber hand";
	},

	// precedent
	_diapoNeg : function()
	{
		if (this.expando.photos.length > 0) {
			this.expando.position--;
			if (this.expando.position < 0)
				this.expando.position = this.expando.photos.length-1;
			this.expando._showDiapo(this.expando.position);
		}
	},

	// lecture
	_diapoRun : function()
	{
		if (this.expando.photos.length > 0) {
			if (this.expando.isPlaying) {
				clearInterval(this.expando.timeLine);
				this.expando.isPlaying = false;
			}
			else {
				this.expando.timeLine = setInterval("$('"+this.expando.HTMLElement.id+"').expando._diapoPos()", 4000);
				this.expando.isPlaying = true;
			}
			this.expando.imgPlay.src = (this.expando.isPlaying) ? "img/pause.png" : "img/play.png";
		}
	},
	_start : function()
	{
		if (this.photos.length > 0) {
			if (this.isPlaying) {
				clearInterval(this.timeLine);
				this.isPlaying = false;
			}
			else {
				this.timeLine = setInterval("$('"+this.HTMLElement.id+"').expando._diapoPos()", 4000);
				this.isPlaying = true;
			}
			this.imgPlay.src = (this.isPlaying) ? "img/pause.png" : "img/play.png";
		}
	},
	_launchDiapoPos : function() {this.expando._diapoPos();},
	// suivant
	_diapoPos : function()
	{
		if (this.photos.length > 0) {
			this.position++;
			if (this.position > this.photos.length-1)
				this.position = 0;
			this._showDiapo(this.position);
		}
	},
	_launchStop : function(){
		this.expando.stimulate("onStop");
	},
	// choisir une image
	_changePosition : function()
	{
		this.expando.position = this.photoNumber;
		this.expando._showDiapo(this.photoNumber);
	},
	_hideRemote : function(){
		this.divNavigation.style.display = "none";
		this.navig =false;
	},
	_show : function(){
		this.divNavigation.style.display = "";
		this.navig =true;
	},
	_splash_navig :function() {
		if(_diaporama.navig_mouse == true){
			if(_diaporama.navig == true) //Si les commandes sont visibles
			{
			timer = setTimeout("_diaporama._hideRemote()",5000); //Dans 5sec les commandes seront cachées
			document.onmousemove = function() {
					clearTimeout(timer); //Dès que la souris bouge, on réinitialise le timer (fini ou pas)
					_diaporama._splash_navig(); //Et on relance la fonction (timer)
					return false;
				}
			}
			else //Si les commandes sont cachées, dès qu'on bouge la souris, on les réaffiche
			{
				_diaporama._show();
			}
		}
	}
	
};