package net.dkahn.web.forms;

import org.apache.struts.action.ActionForm;

public class FolderForm extends ActionForm{
	
	private static final long serialVersionUID = 1L;
	private String nom;
	private String idFolder;
	private String description;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
