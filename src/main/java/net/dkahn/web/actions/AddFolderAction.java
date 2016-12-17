package net.dkahn.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dkahn.service.repertoire.RepertoireManager;
import net.dkahn.web.forms.FolderForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.ActionSupport;

public class AddFolderAction extends ActionSupport {
	private RepertoireManager manager;

	public void setManager(RepertoireManager manager) {
		this.manager = manager;
	}

	private Integer type;

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FolderForm folderForm = (FolderForm) form;
		manager.create(folderForm.getIdFolder(), folderForm.getNom(),
				folderForm.getDescription(), type);

		return mapping.findForward("success");
	}

}
