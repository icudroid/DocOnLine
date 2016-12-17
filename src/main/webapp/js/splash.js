/********************************************************************************/
/*				    Script créé par moi-même =)  --->  Nicols	              			*/
/*          C'est un script permettant d'afficher une ou plusieurs images en plein écran		*/
/*	avec possibilité lorsqu'il y a plusieurs images de les afficher en mode diaporama	 	*/
/*		avec bien sur toutes les commandes (Play, Pause, Suivant, Précédent) 		*/
/*															*/
/*						LSD Splash v1.0							*/
/*	       Si vous trouvez des améliorations à y apporter ( et dieu sait qu'il y en a ;-) ), 		*/
/*		     ça serait vraiment sympa de me prévenir : rolling-5@hotmail.fr			*/
/*		Et si vous souhaitez être au courant des mises à jour, envoyez moi un mail		*/
/*															*/
/*					      Mises à jour à venir :						*/
/*		      - Affichage d'une légende sous la photo + numéro/nbr total				*/
/*		- Arrivée en plein écran avec un fondu (un peu plus esthétique =) )			*/
/*       	     - Gestion des touches clavier (Echap, Entrée, Droite, Gauche)			*/
/*															*/
/********************************************************************************/

//Initialisation des variables
var id_img = 0; //Id de l'image courante
var en_cours = false; //Sert à repérer dans quel mode on est : true --> en cours de visionnage   / false --> en pause
var navig = false; //Sert à savoir si les commandes sont affichées ou pas (pour le roll) : true --> les commandes sont affichées   / false --> les commandes sont masquées
var is_array = false; //Est-ce qu'il y a plusieurs photos dans l'argument de départ
var navig_mouse = true; //Fais disparaitre les commandes après un temps d'inactivité de la souris. Mettre à 'false' pour laisser les commandes tout le temps
var img_array = new Array(); //Tableau contenant les images (si is_array == true)
var slide_timeout_value = 4*1000; //Durée entre chaque photo du diaporama     -     En secondes * 1000 ---> en millisecondes ;)
var slide_timeout = null; //Initialisation de la variable du timeout pour le slide
	
	
//Met en place le diaporama en plein écran	
function splash_screen()
	{
		//Création du fond
		var arr_plan = document.createElement('a');
		arr_plan.setAttribute('id', 'splash_screen');
		arr_plan.title = 'Fermer le diaporama';
		arr_plan.style.margin = 0;
		arr_plan.style.width = '100%';
		arr_plan.onclick = function() { splash_close(); return false; }
		
		//Création du container pour les touches et l'image
		var content = document.createElement('div');
		content.setAttribute('id', 'content');
		content.style.width = '400px';
		content.style.height = '350px';
		
		//Commandes
		obj_play = document.createElement('img');
		obj_play.setAttribute('id', 'splash_play');
		obj_play.setAttribute('src', '../img/play.png');
		obj_play.onclick = splash_play;
		obj_play.style.display = 'block';
		content.appendChild(obj_play);
		obj_pause = document.createElement('img');
		obj_pause.setAttribute('id', 'splash_pause');
		obj_pause.setAttribute('src', 'img/pause.png');
		obj_pause.onclick = splash_pause;
		obj_pause.style.display = 'block';
		content.appendChild(obj_pause);
		obj_forward = document.createElement('img');
		obj_forward.setAttribute('id', 'splash_forward');
		obj_forward.setAttribute('src', 'img/forward.png');
		obj_forward.onclick = splash_forward;
		obj_forward.style.display = 'block';
		content.appendChild(obj_forward);
		obj_previous = document.createElement('img');
		obj_previous.setAttribute('id', 'splash_previous');
		obj_previous.setAttribute('src', 'img/previous.png');
		obj_previous.onclick = splash_previous;
		obj_previous.style.display = 'block';
		content.appendChild(obj_previous);
		
		//Création du container pour l'image
		var diap = document.createElement('div');
		diap.setAttribute('id', 'img_content');
		diap.style.width = '300px';
		diap.style.height = '300px';
		diap.className = 'loading';
		content.appendChild(diap);
		
		//Positionnement du container au centre
		array_page_size = getPageSize();
		array_page_scroll = getPageScroll();
		arr_plan.style.height = parseInt(parseFloat(array_page_size[1]));
		
		content.style.left = parseInt((parseFloat(array_page_size[0])/2)-200)+'px';
		content.style.top = parseInt(parseFloat(array_page_scroll[1])+(parseFloat(array_page_size[3])/2)-175)+'px';
		diap.style.left = 50+'px';
		diap.style.top = 50+'px';
		obj_play.style.left = 50+'px';
		obj_play.style.top = 50+'px';
		obj_pause.style.left = 50+'px';
		obj_pause.style.top = 50+'px';
		obj_forward.style.left = 300+'px';
		obj_forward.style.top = 50+'px';
		obj_previous.style.left = 50+'px';
		obj_previous.style.top = 50+'px';
		
		
		obj_body = document.getElementsByTagName('body').item(0);
		obj_body.appendChild(arr_plan);
		obj_body.appendChild(content);
	}

//Affichage d'une image
function splash_image(img)
	{
		if(!document.getElementById('splash_screen'))
		{
		splash_screen(); //Mise en place des containers
		}
		
		//Récupération des différents objets (commandes, containers...)
		obj_splash = document.getElementById('splash_screen');
		obj_content = document.getElementById('img_content');
		obj_table = document.getElementById('content');
		obj_play = document.getElementById('splash_play');
		obj_pause = document.getElementById('splash_pause');
		obj_forward = document.getElementById('splash_forward');
		obj_previous = document.getElementById('splash_previous');
		
		//Affichage du loading
		obj_content.className = 'loading';
		
		//Création objet image pour la taille
		image = new Image;
		image.src = img;
		
		//Création image ou récupération image précédente
			if(!document.getElementById('splash_img')) {
			obj_img = document.createElement('img');
			obj_img.setAttribute('id', 'splash_img');
			}
			else
			{
			obj_img = document.getElementById('splash_img');
			}
		
		//Resize du container et de l'image(minimum 300*300 et maximum largeur*hauteur fenêtre)
		array_page_scroll = getPageScroll();
		array_page_size = getPageSize();
		
		var width_content = 300;
		var height_content = 300;
			
			if(image.height < 300 || image.width < 300){ //Si l'image est plus petite que 300*300
				if(image.width < image.height){
				width_content = 300;
				height_content = image.height*width_content/image.width;
				}
				else
				{
				height_content = 300;
				width_content = image.width*height_content/image.height;
				}
			}
			else{ //Si l'image est plus grande que 300*300
				if(image.width > parseInt(parseFloat(array_page_size[3])-20) || image.height > parseInt(parseFloat(array_page_size[4])-20))
				{
					if(image.width > image.height){
					width_content = parseInt(parseFloat(array_page_size[3])-20);
					height_content = image.height*width_content/image.width;
					}
					else
					{
					height_content = parseInt(parseFloat(array_page_size[4])-20);
					width_content = image.width*height_content/image.height;
					}
				}
				else
				{
				width_content = image.width;
				height_content = image.height;
				}
			}
			
		obj_img.setAttribute('width', width_content+'px');
		obj_img.setAttribute('height', height_content+'px');
			
		obj_content.style.width = width_content+'px';
		obj_content.style.height = height_content+'px';
		
			if(document.getElementById('splash_img')) {
			obj_content.removeChild(obj_img);
			}
		
		//Repositionnement du container
		obj_table.style.width = width_content+100+'px';
		obj_table.style.height = height_content+50+'px';
		obj_table.style.top = parseInt(parseFloat(array_page_scroll[1])+(parseFloat(array_page_size[3])/2)-(height_content/2)-50)+'px';
		obj_table.style.left = parseInt((parseFloat(array_page_size[0])/2)-(width_content/2)-50)+'px';
		obj_img.style.top = parseInt(parseFloat(obj_content.style.top))+'px';
		obj_img.style.left = parseInt(parseFloat(obj_content.style.left))+'px';
		//Positionnement des commandes
			if(navig == true)
			{
			obj_play.style.left = parseInt((width_content/2)+50-150)+'px';
				if(en_cours == false){
				obj_play.style.top = 0+'px'; //On affiche le bouton Play si on n'est pas en cours de lecture
				}
				else{
				obj_play.style.top = 50+'px';
				}
			obj_pause.style.left = parseInt((width_content/2)+50-150)+'px';
				if(en_cours == true){
				obj_pause.style.top = 0+'px'; //On affiche le bouton Pause si on n'est pas en cours de lecture
				}
				else{
				obj_pause.style.top = 50+'px';
				}
			obj_forward.style.left = parseInt(width_content+50)+'px';
			obj_forward.style.top = parseInt((height_content/2)+50-150)+'px';
			obj_previous.style.left = 0+'px';
			obj_previous.style.top = parseInt((height_content/2)+50-150)+'px';
			}
			else
			{
			obj_play.style.left = parseInt((width_content/2)+50-150)+'px';
			obj_play.style.top = 50+'px';
			obj_pause.style.left = parseInt((width_content/2)+50-150)+'px';
			obj_pause.style.top = 50+'px';
			obj_forward.style.left = parseInt(width_content)+'px';
			obj_forward.style.top = parseInt((height_content/2)+50-150)+'px';
			obj_previous.style.left = 50+'px';
			obj_previous.style.top = parseInt((height_content/2)+50-150)+'px';
			}
		
			//Affichage de l'image
		
			//Si l'image n'est pas chargée, on laisse le fond 'loading' jusqu'au chargement complet, puis on affiche l'image
			if (!image.complete) {
			image.onload = function() {
				obj_img.setAttribute('src', img);
				obj_img.style.display = 'block';
				obj_content.className = '';
				obj_content.appendChild(obj_img);
				obj_table.appendChild(obj_content);
				}
			}
			//Si l'image a déjà été chargée une fois, on affiche l'image
			else {
				obj_img.setAttribute('src', img);
				obj_img.style.display = 'block';
				obj_content.className = '';
				obj_content.appendChild(obj_img);
				obj_table.appendChild(obj_content);
			}
		
		
			//Si on est en cours de diffusion, on relance le timeout
			if(en_cours == true)
			{
			var maxi = img_array.length;
			id_img += 1;
			if(id_img == maxi){
			id_img = 0;
			}
			var suiv = img_array[id_img];
			slide_timeout = setTimeout("splash_image('"+suiv+"')", slide_timeout_value);
			}
	}

//Fermer le diaporama 
function splash_close()
	{
	//On supprime les éléments crées
	obj_body = document.getElementsByTagName('body').item(0);
	obj_body.removeChild(document.getElementById('splash_screen'));
	obj_body.removeChild(document.getElementById('content'));
	//On réinitialise les variables
	en_cours = false;
	navig = false;
	id_img = 0;
	clearTimeout(slide_timeout);
	}

//Activer le mode slide pour les fainéants =) 
function splash_play()
	{
		if(is_array == true)
		{
			en_cours = true;
			//Calcul de l'ID de la prochaine image
			var max = img_array.length;
			id_img += 1;
			if(id_img == max){
			id_img = 0;
			}
			var suivante = img_array[id_img];
			//On lance le timeout
			slide_timeout = setTimeout("splash_image('"+suivante+"')", slide_timeout_value);
			//On affiche la touche 'Pause' au lieu de la touche 'Play'
			obj1_play = document.getElementById('splash_play');
			obj1_pause = document.getElementById('splash_pause');
			obj1_play.style.top = 50+'px';
			obj1_pause.style.top = 0+'px';
		}
	}

//Désactiver le mode slide
function splash_pause()
	{
		if(is_array == true)
		{
			en_cours = false;
			//On annule le timeout
			clearTimeout(slide_timeout);
			//On affiche la touche 'Play' au lieu de la touche 'Pause'
			obj2_play = document.getElementById('splash_play');
			obj2_pause = document.getElementById('splash_pause');
			obj2_play.style.top = 0+'px';
			obj2_pause.style.top = 50+'px';
		}
	}

//Diaporama bouton suivant
function splash_forward()
	{
		if(is_array == true)
		{
			//Calcul de l'ID de la prochaine image
			var limite1 = img_array.length;
			id_img += 1;
			if(id_img == limite1){
			id_img = 0;
			}
			var image_suivante = img_array[id_img];
			splash_image(image_suivante);
			en_cours = false; //On arrete le slide si on utilise le bouton suivant
		}
	}

//Diaporama bouton précédent
function splash_previous()
	{
		if(is_array == true)
		{
			//Calcul de l'ID de l'image précédente
			var limite2 = img_array.length - 1;
			id_img -= 1;
			if(id_img < 0){
			id_img = limite2;
			}
			var image_precedente = img_array[id_img];
			splash_image(image_precedente);
			en_cours = false; //On arrete le slide si on utilise le bouton précédent
		}
	}

//Roll
function splash_roll(idroll,tl,pixel,sens) {
	var obj_roll = document.getElementById(idroll);
	pixel = Math.abs(pixel);
	var pixel2 = pixel - 1;
	var pixel3 = -pixel + 1;
	  if(pixel2 > 0 && pixel3 < 0)
	  {
		if(tl == 'top')
		{
			if(sens == 1) {
			obj_roll.style.top = parseInt(parseFloat(obj_roll.style.top)-1)+'px';
			window.setTimeout("splash_roll('"+idroll+"','top',"+pixel2+",1)",2);
			}
			else {
			obj_roll.style.top = parseInt(parseFloat(obj_roll.style.top)+1)+'px';
			window.setTimeout("splash_roll('"+idroll+"','top',"+pixel3+",0)",2);
			}
		}
		else
		{
			if(sens == 1) {
			obj_roll.style.left = parseInt(parseFloat(obj_roll.style.left)-1)+'px';
			window.setTimeout("splash_roll('"+idroll+"','left',"+pixel2+",1)",2);
			}
			else {
			obj_roll.style.left = parseInt(parseFloat(obj_roll.style.left)+1)+'px';
			window.setTimeout("splash_roll('"+idroll+"','left',"+pixel3+",0)",2);
			}
		}
	  }
	}
	
//Roll des commandes de navigation
function splash_navig_roll() {
		//Roll des commandes --> commandes en vue
		if(navig == false)
		{
			if(en_cours == true) {
			splash_roll('splash_pause','top',50,1);
			}
			else {
			splash_roll('splash_play','top',50,1);
			}
			splash_roll('splash_previous','left',50,1);
			splash_roll('splash_forward','left',50,0);
			navig = true;
		}
		//Roll des commandes --> commandes cachées
		else
		{
			if(en_cours == true) {
			splash_roll('splash_pause','top',50,0);
			}
			else {
			splash_roll('splash_play','top',50,0);
			}
			splash_roll('splash_previous','left',50,0);
			splash_roll('splash_forward','left',50,1);
			navig = false;
		}
	}
	
//Check souris pour les commandes de navigation
function splash_navig() {
		if(navig_mouse == true)
		{
			if(navig == true) //Si les commandes sont visibles
			{
			timer = setTimeout("splash_navig_roll()",5000); //Dans 5sec les commandes seront cachées
			document.onmousemove = function() {
					clearTimeout(timer); //Dès que la souris bouge, on réinitialise le timer (fini ou pas)
					splash_navig(); //Et on relance la fonction (timer)
					return false;
				}
			}
			else //Si les commandes sont cachées, dès qu'on bouge la souris, on les réaffiche
			{
			splash_navig_roll();
			}
		}
	}

//Fonction Principale, qui appelle les autres
function splash(images){
		if(isArray(images) == true && images.length > 1){ //Si il y a plusieurs photos en argument, on affiche la premiere et les commandes
			is_array = true;
			img_array = images;
			img = images[id_img];
			loadimg = new Array();
				for(var xx=0; xx<images.length; xx++)
				{
				loadimg[xx] = new Image();
				loadimg[xx].src = img_array[xx];
				}
			
			//On affiche le plein écran
			splash_screen();
			
			//Gestion de l'affichage des commandes
			document.onmousemove = splash_navig;
			
			//On lance l'affichage après le chargement de la première image
			if (!loadimg[0].complete) {
			loadimg[0].onload = function() {
				splash_image(img);
				}
			}
			//Si l'image a déjà été chargée une fois, on affiche l'image
			else {
				splash_image(img);
			}
		}
		else{ //Sinon on affiche une seule photo (-> pas de commandes)
			
			img = new Image();
			img.src = images;
			
			//On affiche le plein écran
			splash_screen();
			
			//On lance l'affichage après le chargement de la première image
			if (!img.complete) {
			img.onload = function() {
				splash_image(images);
				}
			}
			//Si l'image a déjà été chargée une fois, on affiche l'image
			else {
				splash_image(images);
			}
		}
	}
	


	
	
// -----------------------------------------------------------------------------------
//  
// Fonctions annexes, empruntées à un script qui n'est pas de moi
//
// -----------------------------------------------------------------------------------

// -----------------------------------------------------------------------------------
// getPageScroll()
// Returns array with x,y page scroll values.
// Core code from - quirksmode.org
// Code by Lokesh Dhakar
function getPageScroll(){
	var yScroll;
	if (self.pageYOffset) {
		yScroll = self.pageYOffset;
	} else if (document.documentElement && document.documentElement.scrollTop){	 // Explorer 6 Strict
		yScroll = document.documentElement.scrollTop;
	} else if (document.body) {// all other Explorers
		yScroll = document.body.scrollTop;
	}
	arrayPageScroll = new Array('',yScroll) 
	return arrayPageScroll;
}

// -----------------------------------------------------------------------------------
// getPageSize()
// Returns array with page width, height and window width, height
// Core code from - quirksmode.org
// Code by Lokesh Dhakar
// Edit for Firefox by pHaez
//
function getPageSize(){
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
}

//Est-ce que la variable est un tableau  --> Oui : return true / Non : return false	
function isArray(tab)
	{
		if(typeof tab == 'object')
		{
		return true;
		}
		else
		{
		return false;
		}
	}