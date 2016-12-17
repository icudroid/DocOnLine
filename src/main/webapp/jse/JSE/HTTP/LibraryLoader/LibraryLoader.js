JSEPackage("JSE.HTTP.LibraryLoader");

JSEImport("JSE.HTTP");
JSEImport("JSE.HTML.UIComponent");

JSE.HTTP.LibraryLoader = function(url)
{
	this.extend(JSE.HTML.UIComponent);
	this.prepare();
	this.url = url;
	this.HTMLElement.style.display = "none";
	this.appendToHTMLElement(document.body);
};

JSE.HTTP.LibraryLoader.prototype = {
	JSEwireHub : {
		"responseLoaded" : []
	},
	response : {
		JSEhint : "Response from the server call",
		JSEtype : "Object"
	},
	url : {
		JSEhint : "URL to call",
		JSEtype : "String"
	},
	params : {
		JSEhint : "Parameters string for the request",
		JSEtype : "String"
	},
	timeout : -1,
	request : function(params)
	{
		this._doRequest(params);
	},
	_doRequest : function(params)
	{ 
		params += "&charset=UTF-8&version="+ _VMVersion;
		params += "&callBackObject=HTTPResponse";
		params += "&from=" + _FwkFromParam;
		params += "&lang=" + _FwkLang;
		params += _YMTC;
		this.scriptElement = document.createElement("SCRIPT");
		this.scriptElement.expando = this;
		this.scriptElement.setAttribute("type","text/javascript");
		if (navigator.isIE) this.scriptElement.onreadystatechange = this.HTTPResponseReady;
		else this.scriptElement.onload = this.HTTPResponseLoaded;
		this.scriptElement.setAttribute("src",this.url + "?" + params);
		this.HTMLElement.appendChild(this.scriptElement);
	},
	_doCancel : function()
	{
		if (navigator.isIE) this.scriptElement.onreadystatechange = "";
		else this.scriptElement.onload = "";
		this.HTMLElement.removeChild(this.scriptElement);
		this.stimulate("responseCancelled",this);
	},
	HTTPResponseReady : function()
	{
		if ((this.readyState == "loaded")||(this.readyState == "complete")) this.expando.HTTPResponseLoaded.apply(this);
	},
	HTTPResponseLoaded : function()
	{
		this.expando.response = HTTPResponse;
		this.expando.scriptElement = "";
		this.expando.stimulate("responseLoaded",this.expando.response);
		this.expando = "";
		this.onload ="";
		this.onreadystatechange = "";
	}
};

HTTPResponse = null;