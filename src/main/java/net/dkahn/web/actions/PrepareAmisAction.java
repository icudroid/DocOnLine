package net.dkahn.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dkahn.metier.Compte;
import net.dkahn.service.compte.CompteManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.ActionSupport;

public class PrepareAmisAction extends ActionSupport{
	
	private CompteManager manager;

	public void setManager(CompteManager manager) {
		this.manager = manager;
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idCompte = ((Compte)request.getSession().getAttribute("compte")).getId().toString();
		
		request.setAttribute("amis", manager.getComptesAmi(idCompte));

		return mapping.findForward("success");
	}
}
