package net.dkahn.web.servlet;

import java.io.IOException;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dkahn.metier.Compte;
import net.dkahn.metier.Repertoire;
import net.dkahn.service.repertoire.RepertoireManager;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class for Servlet: DownloadRep
 *
 */
 public class DownloadRep extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DownloadRep() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String infopath = request.getPathInfo();
		StringBuffer idFolder = new StringBuffer();
		int i =1;
		char c=0;
		while((c=infopath.charAt(i++))!='.'){
			idFolder.append(c);
		}
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		RepertoireManager manager = (RepertoireManager) context.getBean("repertoireManager");
		Repertoire repertoire = manager.getRepertoire(idFolder.toString());
			if(repertoire.getType() == Repertoire.REPERTOIRE_PRIVE || repertoire.getType() == Repertoire.REPERTOIRE_PARTAGE){
				Compte compteSession = (Compte) request.getSession().getAttribute("compte");
				Compte compte = manager.getCompteForRepertoire(idFolder.toString());
				if(Repertoire.RACINE_TYPE[Repertoire.REPERTOIRE_PRIVE].equals(repertoire.getType())){
					if(compteSession == null || !compteSession.equals(compte)){
						throw new IOException("Erreur d'autorisation");
					}
				}else if(Repertoire.RACINE_TYPE[Repertoire.REPERTOIRE_PRIVE].equals(repertoire.getType())){
					if(compteSession == null || !compteSession.getComptesAmis().getAmis().contains(compte)){
						throw new IOException("Erreur d'autorisation");
					}
				}
			}
//		response.setContentLength(arg0)
		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
		manager.download(idFolder.toString(),out);
		out.close();
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}   	  	    
}