package net.dkahn.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.ActionSupport;

public class PrepareUploadAction extends ActionSupport{
	private String action;

	public void setAction(String action) {
		this.action = action;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idFolder = request.getParameter("idFolder");
		request.setAttribute("action",action);
		request.setAttribute("idFolder",idFolder);
		return mapping.findForward("success");
	}
}
