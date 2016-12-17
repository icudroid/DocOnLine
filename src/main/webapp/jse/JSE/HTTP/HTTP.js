JSEPackage("JSE.HTTP");


JSE.HTTP = {
	JSEwireHub : {
		"responseLoaded" : []
	},
	currentRequest : -1,
	isRequesting : false,
	requests : [],
	asyncRequests : [],
	freeze : false,
	addRequest : function(request, params)
	{
		this.requests.push([request, params+"&version="+_VMVersion+"&charset=UTF-8"]);
		this.nextHTTPCall();
	},
	addAsyncRequest : function(req)
	{
		if (!this.freeze) {
			this.freeze = true;
			this.asyncRequests.push(req);
			var index = (this.asyncRequests.length - 1);
			this.freeze = false;
			return index;
		} else return this.addAsyncRequest(req);
	},
	nextHTTPCall : function()
	{
		if (!this.isRequesting)
		{
			this.currentRequest++;
			if (this.requests.length > this.currentRequest)
			{
				this.isRequesting = true;
				var wires = this.requests[this.currentRequest][0].JSEwireHub["responseLoaded"];
				this.JSEwireHub["responseLoaded"] = new Array();
				for (var i=0; i < wires.length; i++) this.plugWire(wires[i]);
				this.requests[this.currentRequest][0].JSEwireHub["responseLoaded"] = new Array();
				this.requests[this.currentRequest][0].plugWire({
					event : "responseLoaded",
					method : JSE.HTTP._responseLoaded,
					thisObj : JSE.HTTP
				});
				this.requests[this.currentRequest][0]._doRequest(this.requests[this.currentRequest][1]);
			} else {
				this.currentRequest--;
			}
		}
	},
	_responseLoaded : function()
	{
		var wires = this.JSEwireHub["responseLoaded"];
		this.requests[this.currentRequest][0].JSEwireHub["responseLoaded"] = new Array();
		for (var i=0; i < wires.length; i++) this.requests[this.currentRequest][0].plugWire(wires[i]);
		this.stimulate("responseLoaded",this.requests[this.currentRequest][0].response);
		this.isRequesting = false;
		this.nextHTTPCall();
	}	
};