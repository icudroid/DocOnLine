package net.dkahn.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dkahn.metier.Compte;
import net.dkahn.service.compte.CompteManager;
import net.dkahn.web.forms.LoginForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.ActionSupport;

public class LoginAction extends ActionSupport{
	private CompteManager manager;

	public void setManager(CompteManager manager) {
		this.manager = manager;
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginForm login = (LoginForm)form;
		Compte compte = manager.checkLogin(login.getPseudo(), login.getPwd());
		
		if(compte != null){
			request.getSession().setAttribute("compte", compte);
			request.getSession().setAttribute("public", compte.getRacinePublic().getId());
			request.getSession().setAttribute("private", compte.getRacinePrivate().getId());
			request.getSession().setAttribute("share", compte.getRacineShare().getId());
			return mapping.findForward("success");
		}else{
			request.getSession().setAttribute("erreurLogin", "Le login ou le mot de passe sont pas correct.");
			return mapping.findForward("error");
		}
	}
}
