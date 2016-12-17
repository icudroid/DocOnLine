package net.dkahn.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dkahn.metier.Fichier;
import net.dkahn.service.fichier.FichierManager;
import net.dkahn.web.dwrs.util.DWRUtil;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.ActionSupport;


public class ShowFileAction extends ActionSupport{
	private FichierManager manager;

	public void setManager(FichierManager manager) {
		this.manager = manager;
	}
	

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idFile = request.getParameter("idFile");
		Fichier fichier =manager.getFichier(idFile);
		request.setAttribute("img", DWRUtil.transforme(fichier));
		return mapping.findForward("success");
	}
}
