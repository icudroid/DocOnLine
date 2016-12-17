JSEPackage("JSE.HTML.UIComponent");

JSE.HTML.UIComponent = function(tag)
{
	this.prepare();
	if (tag) {
		if (typeof(tag) == "string") this.HTMLElement = document.createElement(tag);
		else this.HTMLElement = tag;
	}
	else this.HTMLElement = document.createElement("DIV");
	this.HTMLElement.expando = this;
	this.HTMLElement.onclick = this.reportHTMLEvent;
	this.HTMLElement.onmousedown = this.reportHTMLEvent;
	this.HTMLElement.onmouseup = this.reportHTMLEvent;
	this.HTMLElement.onmousemove = this.reportHTMLEvent;
	this.HTMLElement.oncontextmenu = this.reportHTMLEvent;
	if (this.HTMLElement.addEventListener) this.HTMLElement.addEventListener('DOMMouseScroll', this.reportHTMLEvent, false);
	this.HTMLElement.onmousewheel = this.reportHTMLEvent;
	
	/*if (!JSE.UIs) JSE.UIs = new Array();
	JSE.UIs.push(this);*/
};
JSE.HTML.UIComponent.prototype = {
	JSEwireHub : {
		"HTMLEvent_click" : [],
		"HTMLEvent_mousedown" : [],
		"HTMLEvent_mouseup" : [],
		"HTMLEvent_mousemove" : [],
		"HTMLEvent_contextmenu" : [],
		"HTMLEvent_mousewheel" : []
	},
	childComponents : [],
	appendToHTMLElement : function(HTMLElement)
	{
		HTMLElement.appendChild(this.HTMLElement);
	},
	appendChildComponent : function(component)
	{
		this.childComponents.push(component);
		this.HTMLElement.appendChild(component.HTMLElement);
	},
	release : function()
	{
		this.clearDOM();
	},
	clearDOM : function()
	{
		if (this.HTMLElement){
			this.HTMLElement.expando = "";
			this.HTMLElement.onclick = "";
			this.HTMLElement.onmousedown = "";
			this.HTMLElement.onmouseup = "";
			this.HTMLElement.onmousemove = "";
			this.HTMLElement.oncontextmenu = "";
			if (this.HTMLElement.removeEventListener) this.HTMLElement.removeEventListener('DOMMouseScroll', this.reportHTMLEvent, false);
			this.HTMLElement.onmousewheel = "";
		};
		this.HTMLElement = null;
	},
	reportHTMLEvent : function(e)
	{
		if (navigator.isIE) e = window.event;
		var type = e.type;
		if (e.type == "DOMMouseScroll") type = "mousewheel";
		this.expando.stimulate("HTMLEvent_" + type , e, this.expando);
	},
	preventDefAction : function(e)
	{
		if (navigator.isIE) event.returnValue = false;
		else e.preventDefault();
	},
	cancelBubbling : function(e)
	{
		if (navigator.isIE) event.cancelBubble = true;
		else e.stopPropagation();
	}
};

