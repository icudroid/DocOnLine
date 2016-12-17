package net.dkahn.web.dwrs;

public class RemoteFile {
	
	private String dateCreation;
	private String description;
	private String url;
	private String nom;
	private Long id;
	private String thumbPath;
	private String bigThumbPath;
	public String getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getThumbPath() {
		return thumbPath;
	}
	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
	public String getBigThumbPath() {
		return bigThumbPath;
	}
	public void setBigThumbPath(String bigThumbPath) {
		this.bigThumbPath = bigThumbPath;
	}
	
	
}
