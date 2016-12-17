package net.dkahn.dao.repertoire;

import net.dkahn.metier.Compte;
import net.dkahn.metier.Repertoire;

public interface RepertoireDao {
	Repertoire getRepertoire(String id);
	void removeRepertoire(Repertoire repertoire);
	void saveRepertoire(Repertoire repertoire);
	Compte getCompteRacine(String idFolder);
}
