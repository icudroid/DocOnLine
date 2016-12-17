package net.dkahn.service.repertoire;

import java.util.ArrayList;
import java.util.zip.ZipOutputStream;

import net.dkahn.metier.Compte;
import net.dkahn.metier.Repertoire;
import net.dkahn.web.dwrs.RemoteFolder;

public interface RepertoireManager {
	Repertoire createRacine(Compte compte, int type);
	Repertoire create(String idRepertoireParent, String nom, String description, int type);
	void move(String src, String dst, String compte);
	void copy(String src, String dst, String compte);
	void delete(String repertoire);
	Repertoire getRepertoire(String idFolder);
	ArrayList<RemoteFolder> getRepertoires(String idRepertoire);
	void download(String idFolder, ZipOutputStream outputStream);
	Compte getCompteForRepertoire(String string);
}
