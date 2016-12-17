package net.dkahn.service.compteami;

import java.util.List;

import net.dkahn.metier.Compte;



public interface CompteAmiManager {
	List<Compte> getComptesAmiForCompte(String idCompte);
}
