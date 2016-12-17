JSEPackage("JSE.HTTP.XMLHTTPRequest");
JSEImport("JSE.HTTP");

JSE.HTTP.XMLHTTPRequest = function(url, method)
{
	this.prepare();
	this.url = url;
	if (method) this.method = method;
	else this.method = "GET";
};

JSE.HTTP.XMLHTTPRequest.prototype = {
	JSEwireHub : {
		"responseLoaded" : []
	},
	response : {
		JSEhint : "Response from the server call",
		JSEtype : "Object"
	},
	method : "GET",
	url : "",
	params : "",
	timeout : -1,
	request : function(params)
	{
		//alert("XMLHTTPRequest request : "+this.url)
		JSE.HTTP.addRequest(this, params);
	},
	_doRequest : function(params)
	{
		//params += "&unique="+(new Date()).getTime();
		params += "&charset=UTF-8&from=" + _FwkFromParam;
		params += "&lang=" + _FwkLang;
		params += _YMTC;
		//TODO : params
		if (window.XMLHttpRequest) 
		{
	        __XmlHttpRequest = new XMLHttpRequest();
	    } else if (window.ActiveXObject) 
	    {
	        __XmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	    }
		
		__XmlHttpRequest.onreadystatechange = this.HTTPResponseReady;
		__JSEXmlHttpRequest = this;
		__XmlHttpRequest.open(this.method, this.url +"?"+params, true);
        //alert(this.method+" : "+this.url);
        //alert(this.url);
        if (this.method == "GET") __XmlHttpRequest.send(params);
        else {
        	__XmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        	__XmlHttpRequest.send(params);
        }
	},
	_doCancel : function()
	{
		__XmlHttpRequest.onreadystatechange = function(){};
		this.stimulate("responseCancelled",this);
	},
	HTTPResponseReady : function()
	{
		if (__XmlHttpRequest.readyState == 4) __JSEXmlHttpRequest.HTTPResponseLoaded();
	},
	HTTPResponseLoaded : function()
	{
		//alert("ResponseLoaded");
		this.response.responseText = __XmlHttpRequest.responseText;
		this.response.responseXML = __XmlHttpRequest.responseXML;
		this.stimulate("responseLoaded",this.response);
	}
};