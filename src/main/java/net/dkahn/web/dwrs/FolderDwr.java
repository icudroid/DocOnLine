package net.dkahn.web.dwrs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;

import net.dkahn.metier.Compte;
import net.dkahn.metier.Repertoire;
import net.dkahn.service.compte.CompteManager;
import net.dkahn.service.repertoire.RepertoireManager;
import net.dkahn.web.dwrs.util.DWRUtil;

public class FolderDwr {
	private RepertoireManager manager;

	public void setManager(RepertoireManager manager) {
		this.manager = manager;
	}
	
	private CompteManager managerCompte;

	public void setManagerCompte(CompteManager managerCompte) {
		this.managerCompte = managerCompte;
	}

	public RemoteFolder getParent(String idFolder, String sessionAtt){
		RemoteFolder res = null;
		Repertoire repertoire = manager.getRepertoire(idFolder);
		
		if(repertoire.getParent() == null){
			res = DWRUtil.transforme(repertoire);
		}else{
			Repertoire parent = repertoire.getParent();
			HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
				if(sessionAtt ==null || "".equals(sessionAtt)){
					
					switch (parent.getType()) {
						case Repertoire.REPERTOIRE_PUBLIC:
							request.getSession().setAttribute("public", parent.getId());
							break;
						case Repertoire.REPERTOIRE_PRIVE:
							request.getSession().setAttribute("private", parent.getId());
							break;
						case Repertoire.REPERTOIRE_PARTAGE:
							request.getSession().setAttribute("share", parent.getId());
							break;
					}
					
				}else{
					request.getSession().setAttribute(sessionAtt, parent.getId());
				}
			res = DWRUtil.transforme(parent);
		}
		return res;
	}
	
	public RemoteFolder[] getFolders(String idStartFolder,String idCompte,int type,String sessionAtt){
		HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
		
		if(sessionAtt ==null || "".equals(sessionAtt)){
			switch (type) {
				case Repertoire.REPERTOIRE_PUBLIC:
					request.getSession().setAttribute("public", idStartFolder);
					break;
				case Repertoire.REPERTOIRE_PRIVE:
					request.getSession().setAttribute("private", idStartFolder);
					break;
				case Repertoire.REPERTOIRE_PARTAGE:
					request.getSession().setAttribute("share", idStartFolder);
					break;
			}
			
		}else{
			request.getSession().setAttribute(sessionAtt, idStartFolder);
		}
		
		
		ArrayList<RemoteFolder> res = new ArrayList<RemoteFolder>();
		Repertoire repertoire = manager.getRepertoire(idStartFolder);
		Set<Repertoire> repertoires = repertoire.getRepertoires();
		for (Iterator<Repertoire> iterator = repertoires.iterator(); iterator.hasNext();) {
			Repertoire tmp = iterator.next();
				if(tmp.getType() == type){
					res.add(DWRUtil.transforme(tmp));
				}
		}
		RemoteFolder[] resultat = new RemoteFolder[res.size()];
		int i=0;
		for (Iterator<RemoteFolder> iterator = res.iterator(); iterator.hasNext();i++) {
			resultat[i] = iterator.next();
			
		}
		return resultat;
	}
	
	public RemoteFolderInfo[] getFoldersInfoPath(String idEndtFolder){
		ArrayList<RemoteFolderInfo> res = new ArrayList<RemoteFolderInfo>();
		Repertoire repertoire = manager.getRepertoire(idEndtFolder);
		Repertoire child = null;
		res.add(DWRUtil.toInfoPath(repertoire));
		while((child=repertoire.getParent())!=null){
			res.add(DWRUtil.toInfoPath(child));
			repertoire = child;
		}

		RemoteFolderInfo[] resultat = new RemoteFolderInfo[res.size()];
		int i=0;
		for (Iterator<RemoteFolderInfo> iterator = res.iterator(); iterator.hasNext();i++) {
			resultat[i] = iterator.next();
			
		}
		return resultat;
	}
	
	public String deleteFolder(String idRepertoire,String sessionAtt){
		Repertoire repertoire = manager.getRepertoire(idRepertoire);
			if(repertoire.getParent() != null){
				String res = repertoire.getParent().getId().toString();
				manager.delete(idRepertoire);
				HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
				
				if(sessionAtt ==null || "".equals(sessionAtt)){
					switch (repertoire.getType()) {
						case Repertoire.REPERTOIRE_PUBLIC:
							request.getSession().setAttribute("public", res);
							break;
						case Repertoire.REPERTOIRE_PRIVE:
							request.getSession().setAttribute("private", res);
							break;
						case Repertoire.REPERTOIRE_PARTAGE:
							request.getSession().setAttribute("share", res);
							break;
					}
					
				}else{
					request.getSession().setAttribute(sessionAtt, res);
				}
				return res;
			}
		return repertoire.getId().toString();
	}
	
	public RemoteFolder getShareFolder(String idCompteAmi){
		Compte compte = managerCompte.getCompte(idCompteAmi);
		return DWRUtil.transforme(compte.getRacineShare());
	}
}
