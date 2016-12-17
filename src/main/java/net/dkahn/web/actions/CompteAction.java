package net.dkahn.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dkahn.metier.Compte;
import net.dkahn.service.compte.CompteManager;
import net.dkahn.service.exception.ExistUser;
import net.dkahn.web.forms.CompteForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.ActionSupport;

public class CompteAction extends ActionSupport{
	private CompteManager manager;

	public void setManager(CompteManager manager) {
		this.manager = manager;
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CompteForm compte = (CompteForm)form;
		Compte compteSession = null;
		try {
			compteSession  = manager.createCompte(compte.getEmail(), compte.getPseudo(), compte.getMotDePasse(), Compte.DROIT_USER);
		} catch (ExistUser e) {
			request.getSession().setAttribute("erreurCreate", "Le compte existe déjà");
			return mapping.findForward("error");
		} catch (Exception e) {
			return mapping.findForward("error");
		}
		
		request.getSession().setAttribute("compte", compteSession);
		return mapping.findForward("success");
		
	}
}
