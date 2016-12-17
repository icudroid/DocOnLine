package net.dkahn.service;

import java.util.LinkedList;

import net.dkahn.metier.Compte;
import net.dkahn.metier.Fichier;
import net.dkahn.metier.Repertoire;

public class URLCalculator {

		public static String compute(Fichier fichier,Compte compte){
			LinkedList<String> compute = compute(fichier.getRepertoire(),new LinkedList<String>());
			String res = Config.URL_BASE_RESOURCES+"/"+compte.getPseudo()+"/";
			String tmp=null;
			while((tmp=compute.pollLast())!=null){
				res+=tmp+"/";
			}
			
			return res+fichier.getNom();
		}
		
		private static LinkedList<String> compute(Repertoire repertoire,LinkedList<String> urls){
			
			if(repertoire.getParent()==null) {
				urls.add(Repertoire.RACINE_TYPE[repertoire.getType()]);
				return urls;
			}
			urls.add(repertoire.getNom());
			return compute(repertoire.getParent(),urls);
		}

		public static String computeThumb(String thumbPath) {
			
			int index = thumbPath.indexOf(Config.URL_BASE_RESOURCES);
			String sub = thumbPath.substring(index, thumbPath.length());
			
			return sub.replace('\\', '/');
		}
}
