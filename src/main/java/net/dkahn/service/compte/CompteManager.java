package net.dkahn.service.compte;

import java.util.ArrayList;
import java.util.Set;

import net.dkahn.metier.Compte;
import net.dkahn.service.exception.ExistUser;
import net.dkahn.web.dwrs.RemoteCompte;
import net.dkahn.web.dwrs.RemoteFolder;

public interface CompteManager {
	Compte createCompte(String email, String pseudo, String pwd, Integer droit) throws ExistUser;
	void update(Compte compte);
	void delete(Compte compte);
	Compte checkLogin(String pseudo, String pwd);
	Compte getCompte(String idCompte);
	Compte getCompteByPseudo(String pseudo);
	Set<RemoteCompte> getComptesAmi(String idCompte);
	void addCompteAmi(String idCompte, String pseudo);
	public ArrayList<RemoteFolder> getShareFolder(String idCompte);
	public RemoteFolder getPublicFolder(String idCompte);
	void delCompteAmi(String idCompte, String pseudo);
	RemoteCompte[] getComptesAmis(String idCompte);
}
