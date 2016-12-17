package net.dkahn.service.repertoire;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.dkahn.dao.repertoire.RepertoireDao;
import net.dkahn.metier.Compte;
import net.dkahn.metier.Fichier;
import net.dkahn.metier.Repertoire;
import net.dkahn.service.Config;
import net.dkahn.service.fichier.FichierManager;
import net.dkahn.web.dwrs.RemoteFolder;
import net.dkahn.web.dwrs.util.DWRUtil;

public class RepertoireManagerImpl implements RepertoireManager {

	private RepertoireDao dao;
	private FichierManager managerFic;

	public void setManagerFic(FichierManager managerFic) {
		this.managerFic = managerFic;
	}

	public void setDao(RepertoireDao dao) {
		this.dao = dao;
	}

	public void copy(String idRepSrc, String idRepDst, String idCompte) {
		Repertoire src = getRepertoire(idRepSrc);
		Repertoire dst = getRepertoire(idRepDst);
		Set<Fichier> fichiers = src.getFichiers();

		for (Fichier fichier : fichiers) {
			managerFic.copy(fichier.getId().toString(), idRepDst, idCompte);
		}

		Set<Repertoire> repertoires = src.getRepertoires();

		for (Repertoire repertoire : repertoires) {
			Repertoire rep = create(idRepDst, src.getNom(), src.getDescription(),dst.getType());
			copy(repertoire.getId().toString(), rep.getId().toString(), idCompte);
		}

	}

	public Repertoire create(String idRepertoireParent, String nom, String description,int type) {
		Repertoire parent = getRepertoire(idRepertoireParent);
		//cr�ation du r�pertoire
		Repertoire repertoire = new Repertoire();
		repertoire.setDate(new Date());
		repertoire.setDescription(description);
		repertoire.setNom(nom);
		repertoire.setPath(parent.getPath() + File.separator + nom);
		repertoire.setType(type);
		parent.addRepertoire(repertoire);

		//cr�ation du r�pertoire sur le serveur
		File rep = new File(repertoire.getPath());
		rep.mkdir();

		return repertoire;
	}

	public Repertoire createRacine(Compte compte,int type) {
		//cr�ation du r�pertoire racine
		Repertoire racine = new Repertoire();
		racine.setDate(new Date());
		racine.setDescription("Répertoire racine de l'utilisateur");
		racine.setNom("/");
		racine.setPath(Config.PATH_SERVER + compte.getPseudo()+ File.separator + Repertoire.RACINE_TYPE[type]);
		racine.setType(type);
		//cr�ation du r�pertoire racine sur le serveur
		File rep = new File(racine.getPath());
		rep.mkdirs();

		return racine;
	}

	public void delete(String idRepertoire) {
		Repertoire repertoire = getRepertoire(idRepertoire);
		deleteRec(repertoire);
		repertoire.getParent().removeRepertoire(repertoire);
		this.dao.removeRepertoire(repertoire);
	}

	public void move(String idRepSrc, String idRepDst, String idCompte) {
		copy(idRepSrc, idRepDst, idCompte);
		delete(idRepSrc);
	}

	private static void deleteRec(Repertoire repertoire) {
		Set<Fichier> fichiers = repertoire.getFichiers();
		for (Fichier fichier : fichiers) {
			File fileThumb = new File(fichier.getRepertoire().getPath()+File.separator+Config.THUMB+File.separator+fichier.getNom());
			File fileBigThumb = new File(fichier.getRepertoire().getPath()+File.separator+Config.BIGTHUMB+File.separator+fichier.getNom());
			
			if(fileThumb.getPath().indexOf(Config.PATH_THUMB_DEFAULT)==-1){
				fileThumb.delete();	
			}
			if(fileBigThumb.getPath().indexOf(Config.PATH_THUMB_DEFAULT)==-1){
				fileBigThumb.delete();	
			}
			
			File file = new File(fichier.getRepertoire().getPath()
					+ File.separator + fichier.getNom());
			file.delete();
		}

		Set<Repertoire> repertoires = repertoire.getRepertoires();

		for (Repertoire rep : repertoires) {
			deleteRec(rep);
		}
		if (repertoire.getParent() == null)
			return;
		new File(repertoire.getPath()+File.separator+Config.THUMB).delete();
		new File(repertoire.getPath()+File.separator+Config.BIGTHUMB).delete();
		new File(repertoire.getPath()).delete();
	}

	public Repertoire getRepertoire(String idFolder) {
		return dao.getRepertoire(idFolder);
	}

	public ArrayList<RemoteFolder> getRepertoires(String idRepertoire) {
		Repertoire repertoire = dao.getRepertoire(idRepertoire);
		ArrayList<RemoteFolder> rs = new ArrayList<RemoteFolder>();
		Set<Repertoire> repertoires = repertoire.getRepertoires();
		rs.add(DWRUtil.transforme(repertoire));
		for (Iterator<Repertoire> iterator = repertoires.iterator(); iterator
				.hasNext();) {
			Repertoire r = iterator.next();
				rs.add(DWRUtil.transforme(r));
		}
		return rs;
	}

	public void download(String idFolder, ZipOutputStream outputStream) {
		Repertoire repertoire = getRepertoire(idFolder);
		download(repertoire.getPath(),idFolder,outputStream);
	}
	
	private void download(String beginPath, String idFolder,ZipOutputStream outputStream) {
		Repertoire repertoire = getRepertoire(idFolder);
		
		Set<Fichier> fichiers = repertoire.getFichiers();
		
		for (Iterator<Fichier> iterator = fichiers.iterator(); iterator.hasNext();) {
			Fichier fichier =  iterator.next();
			File file = new File(repertoire.getPath()+File.separator+fichier.getNom());
			ZipEntry zipEntry = new ZipEntry(getRelativePath(beginPath,repertoire.getPath())+fichier.getNom());
			try {
				outputStream.putNextEntry(zipEntry);
				FileInputStream in = new FileInputStream(file);
				byte bs[] = new byte[in.available()];
				in.read(bs);
				in.close();
				outputStream.write(bs);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		Set<Repertoire> repertoires = repertoire.getRepertoires();
		
		for (Iterator<Repertoire> iterator = repertoires.iterator(); iterator.hasNext();) {
			download(beginPath,iterator.next().getId().toString(),outputStream);
		}
	}

	private String getRelativePath(String beginPath, String path) {
		if(path.length()>beginPath.length()){
			return path.substring(beginPath.length()+1, path.length())+File.separator;
		}else{
			return "";
		}
	}

	public Compte getCompteForRepertoire(String idFolder) {
		Repertoire repertoire = getRepertoire(idFolder);
		Repertoire racine = repertoire;
		while(racine.getParent()!=null){
			racine = racine.getParent();
		}

		return dao.getCompteRacine(racine.getId().toString());
	}


}
