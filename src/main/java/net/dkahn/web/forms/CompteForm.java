package net.dkahn.web.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class CompteForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String pseudo;

	private String email;

	private String motDePasse;

	private String motDePasseConfirm;

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getMotDePasseConfirm() {
		return motDePasseConfirm;
	}

	public void setMotDePasseConfirm(String motDePasseConfirm) {
		this.motDePasseConfirm = motDePasseConfirm;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		if(isBlankString(pseudo)){
			errors.add("pseudo",new ActionMessage("errors.required",new Object[]{"le champs Pseudo "}));
		}
		
		if(isBlankString(email)){
			errors.add("email",new ActionMessage("errors.required",new Object[]{"le champs email "}));
		}	
		
		if(isBlankString(motDePasse)){
			errors.add("motDePasse",new ActionMessage("errors.required",new Object[]{"le champs mot de passe "}));
		}else if(motDePasse.length()<6){
			errors.add("motDePasse",new ActionMessage("errors.minlength",new Object[]{"le mot de passe ","6"}));
		}
		
		if(isBlankString(motDePasseConfirm)){
			errors.add("motDePasseConfirm",new ActionMessage("errors.required",new Object[]{"le champs mot de passe de confirmation "}));
		}else if(!isEqualsString(motDePasse,motDePasseConfirm)){
			errors.add("motDePasseConfirm",new ActionMessage("errors.invalid",new Object[]{"le mots de passe de confirmation "}));
		}
		
		return errors;
	}

	private boolean isBlankString(String str){
		if(str == null) return true;
		return (str.length()==0);
	}
	
	private boolean isEqualsString(String str1, String str2){
		if(str1 == null || str2 == null) return false;
		return (str1.equals(str2));
	}
	
}
