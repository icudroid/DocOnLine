package net.dkahn.web.dwrs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;

import net.dkahn.metier.Fichier;
import net.dkahn.metier.Repertoire;
import net.dkahn.service.fichier.FichierManager;
import net.dkahn.service.repertoire.RepertoireManager;
import net.dkahn.web.dwrs.util.DWRUtil;

public class FileDwr {
	private RepertoireManager manager;

	public void setManager(RepertoireManager manager) {
		this.manager = manager;
	}
	
	private FichierManager managerFic;

	public void setManagerFic(FichierManager manager) {
		this.managerFic = manager;
	}

	public RemoteFile []getFiles(String idFolder){
		ArrayList<RemoteFile> res = new ArrayList<RemoteFile>();
		Repertoire repertoire = manager.getRepertoire(idFolder);
		Set<Fichier> fichiers = repertoire.getFichiers();
		for (Iterator<Fichier> iterator = fichiers.iterator(); iterator.hasNext();) {
			Fichier fichier = iterator.next();
			res.add(DWRUtil.transforme(fichier));
		}
		
		RemoteFile[] resultat = new RemoteFile[res.size()];
		int i=0;
		for (Iterator<RemoteFile> iterator = res.iterator(); iterator.hasNext();i++) {
			resultat[i] = iterator.next();
			
		}
		return resultat;
	}
	
	public String deleteFile(String idFile){
		managerFic.delete(idFile);
		return "";
	}
	
	public  String[] getUrlImageFromFolder(String idFolder){
		ArrayList<Fichier> res = new ArrayList<Fichier>();
		Repertoire repertoire = manager.getRepertoire(idFolder);
		Set<Fichier> fichiers = repertoire.getFichiers();
		for (Iterator<Fichier> iterator = fichiers.iterator(); iterator.hasNext();) {
			Fichier fichier = iterator.next();
			res.add(fichier);
		}
		
		String[] resultat = new String[res.size()];
		int i=0;
		for (Iterator<Fichier> iterator = res.iterator(); iterator.hasNext();i++) {
			resultat[i] = iterator.next().getBigThumbPath();
			
		}
		return resultat;
	}
	
}
