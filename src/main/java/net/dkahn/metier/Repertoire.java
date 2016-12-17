package net.dkahn.metier;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;

@Entity
public class Repertoire implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public static final int REPERTOIRE_PUBLIC = 0;
	public static final int REPERTOIRE_PRIVE = 1;
	public static final int REPERTOIRE_PARTAGE = 2;
	public static final String[] RACINE_TYPE = {"Public","Private","Share"};

	
	@Id @GeneratedValue
	private Long id;

	private String nom;
	
        @Column (name="PATH_REP")
	private String path;
	
	private String description;
	
	private Integer type;
	
        @Column (name="DATE_REP")
        @Temporal(javax.persistence.TemporalType.DATE)
	private Date date;
	
        @Temporal(javax.persistence.TemporalType.DATE)
	private Date updateDate;
	
        @OneToOne
        @PrimaryKeyJoinColumn
	private Fichier image;
	
	@OneToMany (mappedBy="parent",cascade=CascadeType.ALL)
	private Set<Repertoire> repertoires = new HashSet<Repertoire>();

	@ManyToOne
	@JoinColumn(name="repparent_fk")
	private Repertoire parent;
	
	@OneToMany (cascade=CascadeType.ALL)
	private Set<Fichier> fichiers = new HashSet<Fichier>();

        public Fichier getImage() {
            return image;
        }

        public void setImage(Fichier image) {
            this.image = image;
        }
	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Repertoire> getRepertoires() {
		return repertoires;
	}

	public void setRepertoires(Set<Repertoire> repertoires) {
		this.repertoires = repertoires;
	}

	public Set<Fichier> getFichiers() {
		return fichiers;
	}

	public void setFichiers(Set<Fichier> fichiers) {
		this.fichiers = fichiers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean addRepertoire(Repertoire r){
		r.setParent(this);
		
		if(getRepertoires().contains(r))
			return false;
		
		return getRepertoires().add(r);
	}

	public boolean removeRepertoire(Repertoire r){
		if(getRepertoires() == null){
			return false;
		}
		return getRepertoires().remove(r);
	}

	public boolean addFichier(Fichier f){

		if(getFichiers().contains(f))
			return false;
		f.setRepertoire(this);
		return getFichiers().add(f);
	}

	public boolean removeFichier(Fichier f){
		if(getFichiers() == null){
			return false;
		}
		f.setRepertoire(null);
		return getFichiers().remove(f);
	}

	
	public Repertoire getParent() {
		return parent;
	}

	public void setParent(Repertoire parent) {
		this.parent = parent;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		final Repertoire other = (Repertoire) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}


	
}

