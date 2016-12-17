/*
JSE - JavaScript Engine 
Copyright (c) 2006-2008 Stephane Maccari <http://stephane.maccari.fr>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

Credits:
	- Browser detection taken from MooTools (c) 2007 Valerio Proietti
*/


//Default path to Javascript Packages
if (typeof(JSEPath) == "undefined") JSEPath = "";

/*
 * Object augmentation
 * 	- Inheritance	
 * 		- cast(string JSEtype)
 * 		- extend(object,n arguments) 
 * 	- Events & Wires
 * 		- plugWire(JSEwire)
 * 		- unplugWire(JSEwire)
 * 		- stimulate(string event,n arguments)
 * 	- Object isolation
 * 		- isolate()
 * 	- Object preparation 
 * 		- prepare() 		
 */

Object.prototype.cast = function(JSEtype)
{
	// Returns an object containing source object members 
	// augmented by JSEtype instanciated object members
	var result = new (eval(JSEtype))();
	var item;
	for (item in this) 
	{
		result[item] = this[item];
	}
	return result;
};
Object.prototype.extend = function(object)
{
	// Prototype inheritance
	var item;
	for (item in object.prototype) 
	{
		if (!this[item]) this[item] = object.prototype[item];
	}
	// Constructor inheritance, applying arguments
	if (arguments.length == 1) object.call(this);
	else object.apply(this,  Array.prototype.slice.apply(arguments, [1]));
};
Object.prototype.isolate = function()
{
	var item;
	// Delete all listening wires
	if (this.JSEwireHub) {
		for (item in this.JSEwireHub) 
		{
			if ((typeof(this.JSEwireHub[item]) == "object")&&(!Object.prototype[item])) {
				while (this.JSEwireHub[item].length > 0)
				{
					try {
						this.unplugWire(this.JSEwireHub[item][0]);
					} catch(e) {}
				}
			}
		}
	}
	// Delete thisObj references from JSEWires in external JSEWireHubs
	if (this.JSEExtRef) {
		for (var i=0; i < this.JSEExtRef.length; i++)
		{
			var obj = this.JSEExtRef[i];
			if (obj.JSEwireHub) {
				for (item in obj.JSEwireHub) 
				{
					if ((typeof(obj.JSEwireHub[item]) == "object")&&(!Object.prototype[item])) {
						for (var k=0; k < obj.JSEwireHub[item].length; k++)
						{
							try {
								if (obj.JSEwireHub[item][k].thisObj==this) {
									obj.unplugWire(obj.JSEwireHub[item][k]);
									k--;
								}
							} catch(e) {}
						}
					}
				}
			}
		}
		
		this.JSEExtRef = new Array();
	}
};
Object.prototype.plugWire = function(wire)
{
	if (wire.method) {
		// own JSEWireHub
		if (!this.JSEwireHub) this.JSEwireHub = new Array();
		if (!this.JSEwireHub[wire.event]) this.JSEwireHub[wire.event] = new Array();
		this.JSEwireHub[wire.event].push(wire);
		
		// JSEExtRef on destination thisObj (for isolation)
		if (wire.thisObj) {
			try {
			if (!wire.thisObj.JSEExtRef) wire.thisObj.JSEExtRef = new Array();
			wire.thisObj.JSEExtRef.push(this);
			} catch(e) {
				//alert(Object.prototype.plugWire.caller)
			}
		}
	}
};
Object.prototype.unplugWire = function(wire)
{
	// JSEWire deletion in own JSEWireHub
	if (!this.JSEwireHub) this.JSEwireHub = new Array();
	if (this.JSEwireHub[wire.event]) 
	{
		for (var i=0; i < this.JSEwireHub[wire.event].length; i++)
		{
			if ((wire.method == this.JSEwireHub[wire.event][i].method)&&((wire.thisObj == this.JSEwireHub[wire.event][i].thisObj))) 
			{
					this.JSEwireHub[wire.event].splice(i,1);
					i--;
			}
		}
	}
	// JSEWire deletion in the JSEExtRef of the destination thisObj (for isolation)
	if ((wire.thisObj)&&(wire.thisObj.JSEExtRef)&&(wire.thisObj.JSEExtRef.indexOf(this) != -1)) {
		wire.thisObj.JSEExtRef.splice(wire.thisObj.JSEExtRef.indexOf(this),1);
	}
};
Object.prototype.stimulate = function(strEvent)
{
	if (!this.JSEwireHub) this.JSEwireHub = new Array();
	if (!this.JSEwireHub[strEvent]) this.JSEwireHub[strEvent] = new Array();
	if (this.JSEwireHub[strEvent]) {
		var tmpArray = new Array();
		for (var i=0; i < this.JSEwireHub[strEvent].length; i++) tmpArray.push(this.JSEwireHub[strEvent][i]);
		for (var i=0; i < tmpArray.length; i++) {
			var thisObj = ((tmpArray[i].thisObj) ? tmpArray[i].thisObj : this); 
			tmpArray[i].method.apply(thisObj, Array.prototype.slice.apply(arguments, [1]));
		}
	}
};
Object.prototype.prepare = function(debug)
{
	//Instanciate member objects and build wires
	var object = this;
	if ((Object.prototype.prepare.caller != __JSEComponentLoaded)&&(Object.prototype.prepare.caller)&&(Object.prototype.prepare.caller.prototype)) object = Object.prototype.prepare.caller.prototype;
	if (!this._isPrepared) {
		var item;
		for (item in object)
		{
			
			//Member objects instanciation
			if ((object[item] != null)&&(item != "JSEwireHub")) {
				if (object[item].JSEtype) 
				{
					var wires = object[item].JSEwires;
					var hint = object[item].JSEhint;
					var init = object[item].JSEinit;
					var type = object[item].JSEtype;
					var typeParams = object[item].JSEtypeParams;
					if (typeParams) {
						var tmpParams = new Array();
						for (var i=0; i < typeParams.length; i++)
						{
							if ((typeof(typeParams[i]) == "string")&&(typeParams[i].indexOf("JSEInstance") != -1)) {
								var members = typeParams[i].split(".");
								var member = this;
								for (var j=1; j < members.length; j++) {
									member = member[members[j]];
								}
								tmpParams[i] = member;
							} else tmpParams[i] = typeParams[i];
						}
						typeParams = tmpParams;
						this[item] = new Object();
						typeParams.unshift(eval(type));
						this[item].extend.apply(this[item], typeParams);
					} else {
						try{
							this[item] = new (eval(type))();
						} catch (e) {
							JSE.stimulate("onObjectPreparationError", ((navigator.isIE) ? e.toString() : e.message));
						}
					}
					this[item].JSEhint = hint;
					if (wires) {
						//Member object pre-wiring
						for (var i=0; i < wires.length; i++) {
							var wire = new JSEWire(wires[i].event, wires[i].method, wires[i].thisObj);
							if (typeof(wire.method) == "string") {
								var members = wire.method.split(".");
								if (members[0] == "JSEInstance") {
									var method = this;
									for (var j=1; j < members.length; j++) {
										method = method[members[j]];
									}
									wire.method = method;
								}
								else wire.method = eval(wire.method);
							}
							if (typeof(wire.thisObj) == "string") {
								var members = wire.thisObj.split(".");
								if (members[0] == "JSEInstance") {
									var thisObj = this;
									for (var j=1; j < members.length; j++) {
										thisObj = thisObj[members[j]];
									}
									wire.thisObj = thisObj;
								}
								else wire.thisObj = eval(wire.thisObj);
							}
							this[item].plugWire(wire);
						}
					}
					if (init) {
						this[item].init.apply(this[item],init);
					}
				}
			}
			
			
		}
		//Auto wiring
		if (object["JSEwireHub"]) {
			var item = "JSEwireHub";
			var tmpHub = new Array();
			var hubItem;
			for (hubItem in object[item]) {
				if (!Object.prototype[item])
				{
					tmpHub[hubItem] = new Array();
					for (var i=0; i < object[item][hubItem].length; i++)
					{
						if (object[item][hubItem][i]) {
							var wire = new JSEWire( object[item][hubItem][i].event,  object[item][hubItem][i].method,  object[item][hubItem][i].thisObj);
							if (typeof(wire.method) == "string") {
								var members = wire.method.split(".");
								if (members[0] == "JSEInstance") {
									var method = this;
									for (var j=1; j < members.length; j++) {
										method = method[members[j]];
									}
									wire.method = method;
								}
								else wire.method = eval(wire.method);
							}
							if (typeof(wire.thisObj) == "string") {
								var members = wire.thisObj.split(".");
								if (members[0] == "JSEInstance") {
									var thisObj = this;
									for (var j=1; j < members.length; j++) {
										thisObj = thisObj[members[j]];
									}
									wire.thisObj = thisObj;
								}
								else wire.thisObj = eval(wire.thisObj);
							}
							tmpHub[hubItem].push(wire);
						}
					}
				}
			}
			this.JSEwireHub = tmpHub;
		}
	}
};

/*
 * Array augmentation
 * 		
 * 		- indexOf(object)
 * 
 */

Array.prototype.indexOf = function(obj)
{
	for (var i=0; i < this.length; i++)
	{
		if (this[i] == obj) return i;
	}
	return -1;
};

/*
 * Browser detection
 * 
 * 		- navigator.isIE
 * 		- navigator.isIE6
 * 		- navigator.isIE7
 * 		- navigator.isSafari
 * 		- navigator.isSafari2
 * 		- navigator.isSafari3
 * 		- navigator.isMozilla
 * 		- navigator.isOpera
 * 		- navigator.isJSECompatible
 */
window.xpath = !!(document.evaluate);
if (window.ActiveXObject) navigator.isIE = navigator[window.XMLHttpRequest ? 'isIE7' : 'isIE6'] = true;
else if (document.childNodes && !document.all && !navigator.taintEnabled) navigator.isSafari = navigator[window.xpath ? 'isSafari3' : 'isSafari2'] = true;
else if (document.getBoxObjectFor != null) navigator.isMozilla = true;
navigator.isOpera = window.opera;
navigator.isJSECompatible = navigator.isIE || navigator.isMozilla || navigator.isOpera || navigator.isSafari3;



/*
 * JSE System Object  
 * 
 */

JSE = {
	JSEwireHub : {
		"loadingNamespace" : [],
		"namespaceLoaded" : [],
		"NSPackLoaded" : [],
		"onInit" : [],
		"onInitError" : [],
		"onObjectPreparationError" : []
	},
	namespaces : [],
	currentPackMaster : null,
	lastNamespacePrepared : 0,
	namespaceLoadIndex : 0,
	namespacePreparedIndex : 0,
	isLoadingApplication : false,
	applicationConfiguration : null,
	applications : [],
	nextApplications : [],
	_namespacesToReload : [],
	verify : null,
	systemInit : function()
	{
		try {
			var loadedApplication = null;
			if (typeof(eval(this.currentPackMaster)) == "function") loadedApplication = eval("new "+this.currentPackMaster+"()");
			else loadedApplication = eval(this.currentPackMaster);
			loadedApplication.init(this.applicationConfiguration);		
			this.applicationConfiguration = null;
			JSE.isLoadingApplication = false;
			this._namespacesToReload = new Array();
			this.stimulate("onInit", loadedApplication);
			
		} catch (e) {
			this.applicationConfiguration = null;
			JSE.isLoadingApplication = false;
			this._namespacesToReload = new Array();
			this.stimulate("onInitError", loadedApplication);
		}
	},
	loadComponent : function(namespace)
	{
		this.namespaces.push(namespace);
		this.appendComponent(namespace, false);
	},
	appendComponent : function(namespace, nocache)
	{
		var domScript = document.createElement("SCRIPT");
		domScript.setAttribute("type","text/javascript");
		if (navigator.isIE) domScript.onreadystatechange = __JSEComponentReady;
		else domScript.onload = __JSEComponentLoaded;
		domScript.nameSpace = namespace.name;
		domScript.setAttribute("src",namespace.url+"?version="+_VMVersion);
		this.stimulate("loadingNamespace", namespace.name);
		if (document.body) document.body.appendChild(domScript);
		else if (document.getElementsByTagName("head")[0]) document.getElementsByTagName("head")[0].appendChild(domScript);
	},
	hasNamespace : function(namespace)
	{
		for (var i=0; i < this.namespaces.length; i++) {
			if (this.namespaces[i].equals(namespace)) return i;
		}
		return false;
	},
	releaseNamespace : function(namespace)
	{
		for (var i=0; i < this.namespaces.length; i++)
		{
			if (this.namespaces[i].name == namespace) {
				this.namespaces.splice(i,1);
				this.namespaceLoadIndex--;
				break;
			}
		}
		eval(namespace + " = undefined");
	},
	getChildNamespaces : function(ns)
	{
		var spaces = new Array();
		for (var i=0; i < this.namespaces.length; i++)
		{
			if (this.namespaces[i].name.indexOf(ns+".") == 0) spaces.push(this.namespaces[i]);
		}
		return spaces;
	},
	extend : function(src, dest)
	{
		var item;
		for (item in dest) if (typeof(src[item]) == "undefined") src[item] = dest[item];
	}
};


function __JSEComponentLoaded()
{

		try {
			var obj = JSE.namespaces[JSE.namespaceLoadIndex].name.split(".");
			obj = obj[obj.length - 1];
			if (JSE.namespaces[JSE.namespaceLoadIndex].alias) eval(JSE.namespaces[JSE.namespaceLoadIndex].alias + "=" + JSE.namespaces[JSE.namespaceLoadIndex].name);
			if (eval("typeof("+obj+") == 'undefined'")) eval(obj + "=" + JSE.namespaces[JSE.namespaceLoadIndex].name);
			JSE.stimulate("namespaceLoaded", JSE.namespaces[JSE.namespaceLoadIndex].name);
			if (JSE.getChildNamespaces(JSE.namespaces[JSE.namespaceLoadIndex].name).length > 0) JSE._namespacesToReload.push(JSE.namespaces[JSE.namespaceLoadIndex].name);
			JSE.namespaceLoadIndex++;
			JSE.verifyIndex = 0;
			JSE.verifyNSIndex = -1;
			
		} catch (e) {
			
		}

	if (JSE.namespaceLoadIndex >= JSE.namespaces.length) {
	
		if (JSE.verifyIndex < JSE._namespacesToReload.length) {
				JSE.verifyNSIndex++;
				if (JSE.verifyNSIndex < JSE.getChildNamespaces(JSE._namespacesToReload[JSE.verifyIndex]).length) {
					if (typeof(eval(JSE.getChildNamespaces(JSE._namespacesToReload[JSE.verifyIndex])[JSE.verifyNSIndex].name)) == "undefined") 
						JSE.appendComponent(new JSENamespace(JSE.getChildNamespaces(JSE._namespacesToReload[JSE.verifyIndex])[JSE.verifyNSIndex].name), false);
					else {
						__JSEComponentLoaded(); 
					}
				}
				else {
					JSE.verifyNSIndex = -1;
					JSE.verifyIndex++;
					__JSEComponentLoaded(); 
				}
		}
		else {

			//Objects preparation
			JSE.verify = null; 
			for (var i=JSE.lastNamespacePrepared; i < JSE.namespaces.length; i++) {
				if (typeof(eval(JSE.namespaces[i].name)) != "function") eval(JSE.namespaces[i].name + ".prepare()");
			}
			JSE.lastNamespacePrepared = JSE.namespaces.length - 1;
			JSE.isLoadingModule = false;
			JSE.stimulate("NSPackLoaded");
		}
	}
	this.onload = "";
}
function __JSEComponentReady()
{
	if ((this.readyState == "loaded")||(this.readyState == "complete")) {
		this.onreadystatechange = "";
		__JSEComponentLoaded.call(this);
	}
}

JSE.plugWire(
	{
		event : "NSPackLoaded",
		method : JSE.systemInit,
		thisObj : JSE
	}
);




function JSENamespace(name, url, alias)
{
	this.name = name;
	var basePath = this.name.replace(/\./g,"/");
	basePath += "/" + this.name.split(".")[this.name.split(".").length - 1] + ".js";
	if ((url)&&(url != "")) this.url = url;
	else this.url = JSEPath + basePath;
	if (alias) this.alias = alias;
}
JSENamespace.prototype.equals = function(namespace)
{
	if (this.name == namespace.name) return true;
	return false;
}; 

function JSEImport(packageName, packageURL, aliasName)
{

	if (JSE.currentPackMaster == null) JSE.currentPackMster = packageName;
	var namespace = new JSENamespace(packageName, packageURL, aliasName);
	 
	if (!JSE.hasNamespace(namespace)) {
		try {
			if (typeof(eval(packageName)) != "undefined") JSE._namespacesToReload.push(packageName);
		} catch (e) {

		}
		JSE.loadComponent(namespace);
	} 
}
function JSEPackage(name, idx)
{
	if (!idx) idx = 0;
	var names = name.split(".");
	var object = names[0];
	if (eval("typeof("+object+") == 'undefined'")) eval(object + "= new Object()");
	for (var i=1; i <= idx; i++)
	{
		object += "." + names[i];
		if (eval("typeof("+object+") == 'undefined'")) eval(object + "= new Object()");
	}
	if (names.length > idx + 1) 
	{
		JSEPackage(name, idx+1);
	} 
}
function JSEWire(event, method, thisObj)
{
	this.event = event;
	this.method = method;
	this.thisObj = thisObj;
}

function $(id)
{
	return document.getElementById(id);
}

String.prototype.replaceAll = function(str, replacement)
{
	return this.split(str).join(replacement);
};


/*
 * JSE Application launcher
 * 
 * 		- JSELaunch(object, configuration object, callBackWire, packageURL, aliasName)
 */
function JSELaunch(application, conf, callBackWire, packageURL, aliasName)
{
	if (!JSE.isLoadingApplication) {
		JSE.isLoadingApplication = true;
		JSE.JSEwireHub["onInit"] = new Array();
		if (callBackWire) JSE.plugWire(callBackWire);
		if (conf) JSE.applicationConfiguration = conf;
		else JSE.applicationConfiguration = null;
		JSE.currentPackMaster = application;
		
		var namespace = new JSENamespace(application, packageURL, aliasName);
		if (!JSE.hasNamespace(namespace)) JSEImport(application);
		else JSE.systemInit();
		
	} else {
		JSE.nextApplications.push([application, conf, callBackWire]);
		JSE.plugWire(new JSEWire("onInit",function() {
			if (JSE.nextApplications.length > 0) {
				var tmp = JSE.nextApplications[0];
				JSE.nextApplications.splice(0,1);
				JSELaunch(tmp[0], tmp[1], tmp[2]);
			}	
		},JSE));
	}
} 