package net.dkahn.service.compte;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.dkahn.dao.compte.CompteDao;
import net.dkahn.dao.compteami.CompteAmiDao;
import net.dkahn.metier.Compte;
import net.dkahn.metier.ComptesAmis;
import net.dkahn.metier.Repertoire;
import net.dkahn.service.exception.ExistUser;
import net.dkahn.service.repertoire.RepertoireManager;
import net.dkahn.web.dwrs.RemoteCompte;
import net.dkahn.web.dwrs.RemoteFolder;
import net.dkahn.web.dwrs.util.DWRUtil;

public class CompteManagerImpl implements CompteManager {

	private CompteDao dao;
	private CompteAmiDao daoAmi;
	private RepertoireManager managerRep;
	
	
	public void setDaoAmi(CompteAmiDao daoAmi) {
		this.daoAmi = daoAmi;
	}

	public void setManagerRep(RepertoireManager managerRep) {
		this.managerRep = managerRep;
	}

	public void setDao(CompteDao dao) {
		this.dao = dao;
	}

	public Compte checkLogin(String pseudo, String pwd) {
		return this.dao.checkLogin(pseudo,pwd);
	}

	public Compte createCompte(String email, String pseudo, String pwd,	Integer droit) throws ExistUser {
		if(this.dao.isCompteExists(email,pseudo)){
			throw new ExistUser(email,pseudo);
		}
		
		Compte compte = new Compte();
		compte.setEmail(email);
		compte.setPseudo(pseudo);
		compte.setMotDePasse(pwd);
		compte.setDroit(droit);
		
		compte.setRacinePrivate(managerRep.createRacine(compte,Repertoire.REPERTOIRE_PRIVE));
		compte.setRacinePublic(managerRep.createRacine(compte,Repertoire.REPERTOIRE_PUBLIC));
		compte.setRacineShare(managerRep.createRacine(compte,Repertoire.REPERTOIRE_PARTAGE));
		ComptesAmis compteAmi = new ComptesAmis();
		compte.setComptesAmis(compteAmi);
		daoAmi.saveCompteAmi(compteAmi);
		this.dao.saveCompte(compte);
		return compte;
	}

	public void delete(Compte compte) {
		this.dao.removeCompte(compte);
	}

	public void update(Compte compte) {
		this.dao.saveCompte(compte);
	}

	@Override
	public Compte getCompte(String idCompte) {
		return dao.getCompte(idCompte);
	}

	@Override
	public Compte getCompteByPseudo(String pseudo) {
		return dao.getCompteByPseudo(pseudo);
		
	}

	@Override
	public Set<RemoteCompte> getComptesAmi(String idCompte) {
		Compte compte = getCompte(idCompte);
		Set<Compte> comptesAmis = compte.getComptesAmis().getAmis();
		Set<RemoteCompte> res= new HashSet<RemoteCompte>();
		for (Iterator<Compte> iterator = comptesAmis.iterator(); iterator.hasNext();) {
			Compte c = iterator.next();
			RemoteCompte rc = new RemoteCompte();
			rc.setId(c.getId());
			rc.setPseudo(c.getPseudo());
			res.add(rc);
		}
		
		return res;
	}

	@Override
	public void addCompteAmi(String idCompte, String pseudo) {
		Compte compte = getCompte(idCompte);
		Compte compteAmi = getCompteByPseudo(pseudo);
		ComptesAmis comptesAmis = compte.getComptesAmis();
		comptesAmis.addAmi(compteAmi);
		daoAmi.saveCompteAmi(comptesAmis);
		dao.saveCompte(compte);
	}

	@Override
	public ArrayList<RemoteFolder> getShareFolder(String idCompte) {
		ArrayList<RemoteFolder> res = new ArrayList<RemoteFolder>();
		Compte compte = getCompte(idCompte);
		Repertoire shareFolder = compte.getRacineShare();
		Set<Repertoire> repertoires = shareFolder.getRepertoires();
		
		for (Iterator<Repertoire> iterator = repertoires.iterator(); iterator.hasNext();) {
			Repertoire repertoire =  iterator.next();
			res.add(DWRUtil.transforme(repertoire));
		}
		return res;
	}

	@Override
	public void delCompteAmi(String idCompte, String pseudo) {
		Compte compte = getCompte(idCompte);
		Compte compteAmi = getCompteByPseudo(pseudo);
		compte.getComptesAmis().removeAmi(compteAmi);
		dao.saveCompte(compte);
	}

	@Override
	public RemoteCompte[] getComptesAmis(String idCompte) {
		Compte compte = getCompte(idCompte);
		Set<Compte> amis = compte.getComptesAmis().getAmis();
		RemoteCompte[] res = new RemoteCompte[amis.size()];
		int i = 0;
		for (Iterator<Compte> iterator = amis.iterator(); iterator.hasNext();i++) {
			Compte ami =  iterator.next();
			res[i] = DWRUtil.transforme(ami);
		}
		return res;
	}

	@Override
	public RemoteFolder getPublicFolder(String idCompte) {
		Compte compte = getCompte(idCompte);
		return DWRUtil.transforme(compte.getRacinePublic());
	}

}
