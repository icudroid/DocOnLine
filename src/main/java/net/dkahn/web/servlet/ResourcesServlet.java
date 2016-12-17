package net.dkahn.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dkahn.metier.Compte;
import net.dkahn.metier.Repertoire;
import net.dkahn.service.Config;
import net.dkahn.service.compte.CompteManager;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class for Servlet: RessourcesServlet
 *
 */
public class ResourcesServlet extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	public ResourcesServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String infoPath = request.getPathInfo();
		StringTokenizer st = new StringTokenizer(infoPath, "/");
		int step = 0;
		String nom = null;
		String type = null;
		StringBuffer repertoires = new StringBuffer();
		while (st.hasMoreTokens()) {
			String value = st.nextToken();
			switch (step) {
			case 0:
				//récupération du nom
				nom = value;
				break;
			case 1:
				//récupération du type
				type = value;
				break;
			default:
				repertoires.append(value);
				if(st.hasMoreTokens())
					repertoires.append(File.separator);
				break;
			}
			step++;
		}
		
		if(!Repertoire.RACINE_TYPE[Repertoire.REPERTOIRE_PUBLIC].equals(type)|| !"Default".equals(type)){
			//vérification des droits
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			CompteManager manager = (CompteManager) context.getBean("compteManager");
			Compte compte = manager.getCompteByPseudo(nom);
			Compte compteSession = (Compte) request.getSession().getAttribute("compte");
			if(Repertoire.RACINE_TYPE[Repertoire.REPERTOIRE_PRIVE].equals(type)){
				if(compteSession == null || !compteSession.equals(compte)){
					throw new IOException("Erreur d'autorisation");
				}
			}else if(Repertoire.RACINE_TYPE[Repertoire.REPERTOIRE_PRIVE].equals(type)){
				if(compteSession == null || !compteSession.getComptesAmis().getAmis().contains(compte)){
					throw new IOException("Erreur d'autorisation");
				}
			}
		}
		
		File file = new File(Config.PATH_SERVER+nom+File.separator+type+File.separator+repertoires);
		ServletOutputStream outputStream = response.getOutputStream();
		InputStream in = new FileInputStream(file);
		byte []b = new byte[in.available()];
		in.read(b);
		in.close();
		response.setContentLength((int) file.length());
		outputStream.write(b);
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}