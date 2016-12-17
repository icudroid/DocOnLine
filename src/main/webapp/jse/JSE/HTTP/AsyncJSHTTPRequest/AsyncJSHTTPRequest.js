JSEPackage("JSE.HTTP.AsyncJSHTTPRequest");

JSEImport("JSE.HTTP");
JSEImport("JSE.HTML.UIComponent");

JSE.HTTP.AsyncJSHTTPRequest = function(url)
{
	this.extend(JSE.HTML.UIComponent);
	this.prepare();
	this.url = url;
	this.HTMLElement.style.display = "none";
	this.appendToHTMLElement(document.body);
	
	this.scriptElements = new Array();
	this.timeouts = new Array();
};

JSE.HTTP.AsyncJSHTTPRequest.prototype = {
	JSEwireHub : {
		"requestLaunched" : [],
		"responseLoaded" : [],
		"responseCancelled" : []
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
	setTimeout : function(timeout)
	{
		this.timeout = timeout;
	},
	request : function(params)
	{
		var index = JSE.HTTP.addAsyncRequest(this);
		this._doRequest(params, index);
	},
	_doRequest : function(params, index)
	{ 
		params += "&charset=UTF-8&version="+ _VMVersion + "&callBack=JSE.HTTP.asyncRequests["+index+"].HTTPResponseLoaded";
		params += "&from=" + _FwkFromParam;
		params += "&lang=" + _FwkLang;
		params += _YMTC;
		var scriptElement = document.createElement("SCRIPT");
		scriptElement.setAttribute("type","text/javascript");
		scriptElement.setAttribute("src",this.url + "?" + params);
		//this.scriptElements.push(scriptElement);
		//if (this.timeout != -1) this.timer = setTimeout("JSE.HTTP.asyncJSRequests["+index+"]._doCancel("+(this.scriptElements.length-1)+")", this.timeout);
		this.stimulate("requestLaunched",this.url + "?" + params);
		this.HTMLElement.appendChild(scriptElement);
	},
	_doCancel : function(n)
	{
		this.HTMLElement.removeChild(this.scriptElements[n]);
		this.stimulate("responseCancelled",this);
	},
	HTTPResponseLoaded : function(response)
	{
		if (this.timeout != -1) clearTimeout(this.timer);
		this.stimulate("responseLoaded",response);
	}
};