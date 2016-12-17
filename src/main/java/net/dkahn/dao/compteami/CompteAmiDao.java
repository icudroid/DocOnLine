package net.dkahn.dao.compteami;

import java.util.List;

import net.dkahn.metier.Compte;
import net.dkahn.metier.ComptesAmis;

public interface  CompteAmiDao {
	ComptesAmis getCompteAmi(String id);
	void saveCompteAmi(ComptesAmis ami);
	void removeCompteAmi(ComptesAmis ami);
	List<Compte> getComptesAmiForCompte(String idCompte);
}
