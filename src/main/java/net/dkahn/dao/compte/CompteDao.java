package net.dkahn.dao.compte;

import net.dkahn.metier.Compte;

public interface CompteDao {
	Compte getCompte(String id);
	void saveCompte(Compte compte);
	void removeCompte(Compte compte);
	boolean isCompteExists(String email, String pseudo);
	Compte checkLogin(String pseudo, String pwd);
	Compte getCompteByPseudo(String pseudo);
}
