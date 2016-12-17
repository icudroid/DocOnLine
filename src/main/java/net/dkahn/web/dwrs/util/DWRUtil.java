package net.dkahn.web.dwrs.util;

import net.dkahn.metier.Compte;
import net.dkahn.metier.Fichier;
import net.dkahn.metier.Repertoire;
import net.dkahn.service.Config;
import net.dkahn.web.dwrs.RemoteCompte;
import net.dkahn.web.dwrs.RemoteFile;
import net.dkahn.web.dwrs.RemoteFolder;
import net.dkahn.web.dwrs.RemoteFolderInfo;

public class DWRUtil {
	public static RemoteFolder transforme(Repertoire repertoire) {
		RemoteFolder res = new RemoteFolder();
		res.setDate(repertoire.getDate().toString());
		res.setDateUpdate((repertoire.getUpdateDate()!=null)?repertoire.getUpdateDate().toString():"");
		res.setDescription(repertoire.getDescription());
		res.setId(repertoire.getId());
		res.setNom(repertoire.getNom());
		res.setType(repertoire.getType());
		/*if (repertoire.getImage() != null)
			res.setUrlImage(repertoire.getImage().getUrl());
		else {*/
			// set default image type folder
			switch (repertoire.getType()) {
			case Repertoire.REPERTOIRE_PRIVE:
				res.setUrlImage(Config.DEFAULT_PRIVATE_FOLDER);
				break;
			case Repertoire.REPERTOIRE_PARTAGE:
				res.setUrlImage(Config.DEFAULT_PARTAGE_FOLDER);
				break;
			case Repertoire.REPERTOIRE_PUBLIC:
				res.setUrlImage(Config.DEFAULT_PUBLIC_FOLDER);
				break;
			}

		//}
		return res;
	}

	public static RemoteFile transforme(Fichier fichier) {
		RemoteFile res = new RemoteFile();
		res.setDateCreation(fichier.getDateCreation().toString());
		res.setDescription(fichier.getDescription());
		res.setId(fichier.getId());
		res.setNom(fichier.getNom());
		res.setUrl(fichier.getUrl());
		res.setThumbPath(fichier.getThumbPath());
		res.setBigThumbPath(fichier.getBigThumbPath());
		return res;
	}

	public static RemoteFolderInfo toInfoPath(Repertoire repertoire) {
		RemoteFolderInfo res = new RemoteFolderInfo();
		res.setId(repertoire.getId());
		res.setNom(repertoire.getNom());
		return res;
	}

	public static RemoteCompte transforme(Compte ami) {
		RemoteCompte res = new RemoteCompte();
		res.setId(ami.getId());
		res.setPseudo(ami.getPseudo());
		return res;
	}
}
