package net.dkahn.metier;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Entity
public class Fichier implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue
	private Long id;
	
	private String nom;

	private String url;
	
	private String description;
	
        @Temporal(javax.persistence.TemporalType.DATE)
	private Date dateCreation;
		
	//private byte [] contenu;

	private String thumbPath;
	
	private String bigThumbPath;
	
	@ManyToOne
	private Repertoire repertoire;
	
	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public Repertoire getRepertoire() {
		return repertoire;
	}

	public void setRepertoire(Repertoire repertoire) {
		this.repertoire = repertoire;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
//	public byte[] getContenu() {
//		return contenu;
//	}
//
//	public void setContenu(byte[] contenu) {
//		this.contenu = contenu;
//	}

	public String getBigThumbPath() {
		return bigThumbPath;
	}

	public void setBigThumbPath(String bigThumbPath) {
		this.bigThumbPath = bigThumbPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result
				+ ((repertoire == null) ? 0 : repertoire.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Fichier other = (Fichier) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (repertoire == null) {
			if (other.repertoire != null)
				return false;
		} else if (!repertoire.equals(other.repertoire))
			return false;
		return true;
	}

	

}
