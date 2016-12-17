JSEPackage("JSE.HTML");

JSEImport("JSE.HTML.UIComponent");
 
JSE.HTML = {
	
	findPosition : function(obj)
	{
	  if( obj.offsetParent ) {
	    for( var posX = 0, posY = 0; obj.offsetParent; obj = obj.offsetParent ) {
	      posX += obj.offsetLeft;
	      posY += obj.offsetTop;
	    }
	    return [ posX, posY ];
	  } else {
	    return [ obj.x, obj.y ];
	  }
	
	},	
	clear : function(HTMLElement)
	{
		/*try {
			if (HTMLElement.childNodes) {
				var nodes = HTMLElement.childNodes.length;
				for (var i=0; i < nodes; i++) JSE.HTML.clear(HTMLElement.childNodes[i]);
			}
		} catch (e) {}*/
		var item;
		for (item in HTMLElement) {
			if ((item != "src")&&(item != "innerHTML")&&(item != "outerHTML")) try {HTMLElement[item]="";} catch (e){}
		}
		
	},
	getStyle : function(element, attribute)
	{
		if(document.defaultView && document.defaultView.getComputedStyle)
		{
		
			var value = document.defaultView.getComputedStyle(element, '').getPropertyValue(attribute.replace(/[A-Z]/g ,function(match, char){return "-" + match.toLowerCase();}));
		}
		else if (element.currentStyle) value = element.currentStyle[attribute];
		else value = false;
		return value;
	}
};