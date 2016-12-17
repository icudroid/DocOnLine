package net.dkahn.dao.fichier;

import net.dkahn.metier.Fichier;

public interface FichierDao {
	Fichier getFichier(String id);
	void saveFichier(Fichier fichier);
	void removeFichier(Fichier fichier);
}
