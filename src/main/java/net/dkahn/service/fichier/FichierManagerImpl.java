package net.dkahn.service.fichier;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.imageio.plugins.common.ImageUtil;
import net.dkahn.dao.compte.CompteDao;
import net.dkahn.dao.fichier.FichierDao;
import net.dkahn.dao.repertoire.RepertoireDao;
import net.dkahn.metier.Compte;
import net.dkahn.metier.Fichier;
import net.dkahn.metier.Repertoire;
import net.dkahn.service.Config;
import net.dkahn.service.URLCalculator;

public class FichierManagerImpl implements FichierManager {
	private FichierDao dao;
	private RepertoireDao daoRepertoire;
	private CompteDao daoCompte;
	

	public void setDaoCompte(CompteDao daoCompte) {
		this.daoCompte = daoCompte;
	}



	public void setDaoRepertoire(RepertoireDao daoRepertoire) {
		this.daoRepertoire = daoRepertoire;
	}



	public void setDao(FichierDao dao) {
		this.dao = dao;
	}

	public void copy(String  idFichier, String idRepDst, String idCompte) {
		Fichier fichier = getFichier(idFichier);
		Repertoire repertoire = fichier.getRepertoire();
		File file = new File(repertoire.getPath()+File.separator+fichier.getNom());
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		create(idRepDst, fichier.getNom(), fichier.getDescription(), in,idCompte);
	}

	public void create(String idRepertoire, String nom, String description,
			InputStream inputStream, String idCompte) {
		byte[] buffer;
		try {
			buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			create(idRepertoire,nom,description,buffer,idCompte);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Fichier create(String idRepertoire, String nom,
			String description, byte[] contenu, String idCompte) throws IOException {
		Fichier fichier = new Fichier();
        fichier.setDateCreation(new Date());
        fichier.setDescription(description);
        fichier.setNom(nom);
        //fichier.setContenu(contenu);
        Repertoire repertoire = daoRepertoire.getRepertoire(idRepertoire);
        repertoire.addFichier(fichier);
        Compte compte = daoCompte.getCompte(idCompte);
        fichier.setUrl(URLCalculator.compute(fichier, compte));
        
        //cr�ation du fichier sur le serveur
        createFileServer(repertoire.getPath()+File.separator+fichier.getNom(),contenu);
        //cr�ation du thumbs sur le serveur si c'est une image
		String thumbPath = repertoire.getPath()+File.separator+Config.THUMB+File.separator+fichier.getNom();
		String bigThumbPath = repertoire.getPath()+File.separator+Config.BIGTHUMB+File.separator+fichier.getNom();
		//faire le thumb
		createThumb(repertoire.getPath()+File.separator+fichier.getNom(), thumbPath,fichier);
		
		//faire le thumb
		createBigThumb(repertoire.getPath()+File.separator+fichier.getNom(), bigThumbPath,fichier);

		return fichier;
	}

	public void createFileServer(String path, byte[] contenu) {
		if(contenu ==null){
			contenu = new byte[0];
		}
        FileOutputStream out;
        File file = new File(path);
		try {
			out = new FileOutputStream(file);
			out.write(contenu);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createBigThumb(String pathSrc, String thumbPath) throws IOException {
		new File(thumbPath).mkdirs();
		//cr�ation du thumb
		/*Image image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(pathSrc)).getImage();*/
        Image image = ImageIO.read(new File(pathSrc));

        float x = image.getWidth(null);
		float y = image.getHeight(null);
		float facteur = 1;
		int maxX = Config.DEFAULT_BIGTHUMB_SIZE.x;
		int maxY = Config.DEFAULT_BIGTHUMB_SIZE.y;
		if (x > y && x > maxX) {
				facteur = x / maxX;
		} else if (y > maxY) {
				facteur = y / maxY;
		}

		int dx = (int) (x / facteur);
		int dy = (int) (y / facteur);

        BufferedImage bimage = new BufferedImage(dx, dy, BufferedImage.TYPE_INT_RGB);
        Graphics gc = bimage.createGraphics();

		gc.drawImage(image,0,0,dx,dy,null);		
		gc.dispose();

		try {
			ImageIO.write(bimage, "jpg", new File(thumbPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createThumb(String pathSrc, String thumbPath) throws IOException {
		new File(thumbPath).mkdirs();
		//cr�ation du thumb


        Image image = null;

         image = ImageIO.read(new File(pathSrc));
        //Image image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(pathSrc)).getImage();
		
        float x = image.getWidth(null);
		float y = image.getHeight(null);
		float facteur = 1;
		int maxX = Config.DEFAULT_THUMB_SIZE.x;
		int maxY = Config.DEFAULT_THUMB_SIZE.y;
		if (x > y && x > maxX) {
				facteur = x / maxX;
		} else if (y > maxY) {
				facteur = y / maxY;
		}

		int dx = (int) (x / facteur);
		int dy = (int) (y / facteur);

        BufferedImage bimage = new BufferedImage(dx, dy, BufferedImage.TYPE_INT_RGB);
        Graphics gc = bimage.createGraphics();

		gc.drawImage(image,0,0,dx,dy,null);		
		gc.dispose();

		try {
			ImageIO.write(bimage, "jpg", new File(thumbPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(String idFichier) {
		Fichier fichier = getFichier(idFichier);
		File fileThumb = new File(fichier.getRepertoire().getPath()+File.separator+Config.THUMB+File.separator+fichier.getNom());
		File fileBigThumb = new File(fichier.getRepertoire().getPath()+File.separator+Config.BIGTHUMB+File.separator+fichier.getNom());
		File file = new File(fichier.getRepertoire().getPath()+File.separator+fichier.getNom());
		if(fileThumb.getPath().indexOf(Config.PATH_THUMB_DEFAULT)==-1){
			fileThumb.delete();	
		}
		if(fileBigThumb.getPath().indexOf(Config.PATH_THUMB_DEFAULT)==-1){
			fileBigThumb.delete();	
		}
		file.delete();
		
		Repertoire repertoire = fichier.getRepertoire();
		repertoire.removeFichier(fichier);
		this.dao.removeFichier(fichier);
	}

	public void move(String idFichier, String idRepDst, String idCompte) {
		Fichier fichier = getFichier(idFichier);
		Repertoire repertoire = fichier.getRepertoire();
		File file = new File(repertoire.getPath()+File.separator+fichier.getNom());
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		create(idRepDst, fichier.getNom(), fichier.getDescription(), in,idCompte);		
		delete(idFichier);
	}

	private void createBigThumb(String path,String thumbPath,Fichier fichier) throws IOException {
		
		for (Entry<String, String>  type: Config.TYPE_MINE.entrySet()) {
			String extention = type.getKey();
			String pathThumbDefault = type.getValue();
			
			if(extention.equalsIgnoreCase("default"))continue;
			
			if(path.toUpperCase().indexOf(extention.toUpperCase())!=-1){
				if(pathThumbDefault==null){
					//faire le thumb
					createBigThumb(path,thumbPath);
					//mettre a jour
					fichier.setBigThumbPath(URLCalculator.computeThumb(thumbPath));
					return;
				}else{
					//mettre a jour
					fichier.setBigThumbPath(pathThumbDefault);
					return;
				}
			}
		}
		
		//mettre le thumb par defaut
		fichier.setBigThumbPath(Config.TYPE_MINE.get("default"));
	}
	
	private void createThumb(String path,String thumbPath,Fichier fichier) throws IOException {
		
		for (Entry<String, String>  type: Config.TYPE_MINE.entrySet()) {
			String extention = type.getKey();
			String pathThumbDefault = type.getValue();
			
			if(extention.equalsIgnoreCase("default"))continue;
			
			if(path.toUpperCase().indexOf(extention.toUpperCase())!=-1){
				if(pathThumbDefault==null){
					//faire le thumb
					createThumb(path,thumbPath);
					//mettre a jour
					fichier.setThumbPath(URLCalculator.computeThumb(thumbPath));
					return;
				}else{
					//mettre a jour
					fichier.setThumbPath(pathThumbDefault);
					return;
				}
			}
		}
		
		//mettre le thumb par defaut
		fichier.setThumbPath(Config.TYPE_MINE.get("default"));
	}

	public Fichier getFichier(String id) {
		return dao.getFichier(id);
	}

	public Set<Fichier> getFichiers(String idFolder) {
		return daoRepertoire.getRepertoire(idFolder).getFichiers();
	}

}
