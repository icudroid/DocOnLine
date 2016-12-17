package net.dkahn.web.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


public class LoginForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String pseudo;
	private String pwd;

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		if(isBlankString(pseudo)){
			errors.add("pseudo",new ActionMessage("errors.required",new Object[]{"le champs Pseudo "}));
		}
		if(isBlankString(pwd)){
			errors.add("pwd",new ActionMessage("errors.required",new Object[]{"le champs mot de passe "}));
		}
		return errors;
	}

	private boolean isBlankString(String str){
		if(str == null) return true;
		return (str.length()==0);
	}
}
