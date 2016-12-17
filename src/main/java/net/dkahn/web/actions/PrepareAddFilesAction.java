package net.dkahn.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dkahn.service.repertoire.RepertoireManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.ActionSupport;

public class PrepareAddFilesAction extends ActionSupport{
	
	private RepertoireManager manager;

	public void setManager(RepertoireManager manager) {
		this.manager = manager;
	}
	
	private Integer type;
	
	public void setType(Integer type) {
		this.type = type;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idFolder = request.getParameter("idFolder");
		
		request.setAttribute("repertoires", manager.getRepertoires(idFolder));

		return mapping.findForward("success");
	}
}
