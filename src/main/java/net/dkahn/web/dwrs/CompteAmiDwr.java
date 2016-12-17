package net.dkahn.web.dwrs;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;

import net.dkahn.metier.Compte;
import net.dkahn.service.compte.CompteManager;

public class CompteAmiDwr {
	private CompteManager manager;

	public void setManager(CompteManager manager) {
		this.manager = manager;
	}
	
	public void addCompteAmi(String pseudo){
		HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
		Compte compte = (Compte) request.getSession().getAttribute("compte");
		
		manager.addCompteAmi(compte.getId().toString(),pseudo);
	}
	
	public void delCompteAmi(String pseudo){
		HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
		Compte compte = (Compte) request.getSession().getAttribute("compte");
		
		manager.delCompteAmi(compte.getId().toString(),pseudo);
	}
	
	public RemoteCompte[] getComptesAmis(){
		HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
		Compte compte = (Compte) request.getSession().getAttribute("compte");
		return manager.getComptesAmis(compte.getId().toString());
	}
}
