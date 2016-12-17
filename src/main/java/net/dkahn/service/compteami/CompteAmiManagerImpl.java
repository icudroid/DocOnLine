package net.dkahn.service.compteami;

import java.util.List;

import net.dkahn.dao.compte.CompteDao;
import net.dkahn.dao.compteami.CompteAmiDao;
import net.dkahn.dao.repertoire.RepertoireDao;
import net.dkahn.metier.Compte;
import net.dkahn.metier.Repertoire;

public class CompteAmiManagerImpl implements CompteAmiManager {

	private CompteDao dao;
	private CompteAmiDao daoAmi;
	private RepertoireDao daoRep;
	
	
	
	public void setDao(CompteDao dao) {
		this.dao = dao;
	}



	public void setDaoAmi(CompteAmiDao daoAmi) {
		this.daoAmi = daoAmi;
	}



	public void setDaoRep(RepertoireDao daoRep) {
		this.daoRep = daoRep;
	}


	public List<Compte> getComptesAmiForCompte(String idCompte) {
		return daoAmi.getComptesAmiForCompte(idCompte);
	}

}

