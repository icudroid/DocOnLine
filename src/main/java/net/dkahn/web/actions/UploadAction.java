package net.dkahn.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dkahn.metier.Compte;
import net.dkahn.service.fichier.FichierManager;
import net.dkahn.web.forms.FilesForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.web.struts.ActionSupport;

public class UploadAction extends ActionSupport{
	private FichierManager manager;

	public void setManager(FichierManager manager) {
		this.manager = manager;
	}
	

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		FilesForm files = (FilesForm) form;
		Compte compte = (Compte) request.getSession().getAttribute("compte");
		String idRepertoire = files.getIdFolder();
		
		FormFile file1 = files.getFile1();
			if(!file1.getFileName().equals("")){
				//UploadUtil.writeToDisk(file1, file1.getFileName(), "d:\\");
				manager.create(idRepertoire, file1.getFileName(), files.getDescription1(), file1.getInputStream(), compte.getId().toString());
			}
		
		FormFile file2 = files.getFile2();
		if(!file2.getFileName().equals("")){
			//UploadUtil.writeToDisk(file2, file2.getFileName(), "d:\\");
			manager.create(idRepertoire, file2.getFileName(), files.getDescription1(), file2.getInputStream(), compte.getId().toString());
		}
		
		FormFile file3 = files.getFile3();
		if(!file3.getFileName().equals("")){
			manager.create(idRepertoire, file3.getFileName(), files.getDescription1(), file3.getInputStream(), compte.getId().toString());
			//UploadUtil.writeToDisk(file3, file3.getFileName(), "d:\\");
		}
		
		FormFile file4 = files.getFile4();
		if(!file4.getFileName().equals("")){
			manager.create(idRepertoire, file4.getFileName(), files.getDescription1(), file4.getInputStream(), compte.getId().toString());
			//UploadUtil.writeToDisk(file4, file4.getFileName(), "d:\\");
		}
		
		return mapping.findForward("success");
	}
}
