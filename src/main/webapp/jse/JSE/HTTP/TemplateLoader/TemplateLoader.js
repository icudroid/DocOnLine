JSEPackage("JSE.HTTP.TemplateLoader");

JSEImport("JSE.HTTP");
JSEImport("JSE.HTTP.XMLHTTPRequest");

JSE.HTTP.TemplateLoader = function(url, replaceMatrix)
{
	this.url = url;
	if (replaceMatrix) this.replaceMatrix = replaceMatrix;
	else this.replaceMatrix = new Array();
	this.prepare();
};

JSE.HTTP.TemplateLoader.prototype = {
	JSEwireHub : {
		"templateReady" : []
	},
	finalHTML : "",
	requester : {
		JSEtype : "JSE.HTTP.XMLHTTPRequest",
		JSEtypeParams : [
			"JSEInstance.url",
			"GET"
		]
	},
	loadTemplate : function()
	{
		this.requester.plugWire(new JSEWire("responseLoaded", this.requester_responseLoaded, this));
		this.requester.request("");
		
	},
	requester_responseLoaded : function(response)
	{
		this.finalHTML = response.responseText;
		var item;
		for (item in this.replaceMatrix)
		{
			this.finalHTML = this.finalHTML.replaceAll(item, this.replaceMatrix[item]);
		}
		this.stimulate("templateReady", this.finalHTML);
	}
}