package net.dkahn.service.fichier;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import net.dkahn.metier.Fichier;

public interface FichierManager {
	Fichier create(String idRepertoire, String nom, String description, byte[] contenu, String compte) throws IOException;
	void move(String fichier, String dst, String compte);
	void copy(String fichier, String dst, String compte);
	void delete(String fichier);
	void createThumb(String pathSrc, String thumbPath) throws IOException;
	void createFileServer(String path, byte[] contenu);
	Fichier getFichier(String idFile);
	void create(String idRepertoire, String nom, String description, InputStream inputStream, String idCompte);
	Set<Fichier> getFichiers(String idFolder);
}
