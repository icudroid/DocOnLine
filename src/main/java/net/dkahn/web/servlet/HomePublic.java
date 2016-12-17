package net.dkahn.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dkahn.metier.Compte;
import net.dkahn.metier.Repertoire;
import net.dkahn.service.compte.CompteManager;
import net.dkahn.service.repertoire.RepertoireManager;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class HomePublic  extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   
	    /* (non-Java-doc)
		 * @see javax.servlet.http.HttpServlet#HttpServlet()
		 */
		public HomePublic() {
			super();
		}   	
		
		/* (non-Java-doc)
		 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String infopath = request.getPathInfo();
			StringBuffer publicPseudo = new StringBuffer();
			
			for (int i = 1; i < infopath.length(); i++) {
				publicPseudo.append(infopath.charAt(i));
			}
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			CompteManager manager = (CompteManager) context.getBean("compteManager");
			
			Compte comptePublic = manager.getCompteByPseudo(publicPseudo.toString());
			
			if(comptePublic != null){
				Compte c  = (Compte) request.getSession().getAttribute("comptePublic");
				if(c!=null){
					if(!c.getPseudo().equals(comptePublic.getPseudo())){
						request.getSession().setAttribute("comptePublic", comptePublic);
						request.getSession().setAttribute("folderPublic", manager.getPublicFolder(comptePublic.getId().toString()).getId());
					}
				}else{
					request.getSession().setAttribute("comptePublic", comptePublic);
					request.getSession().setAttribute("folderPublic", manager.getPublicFolder(comptePublic.getId().toString()).getId());
				}
				
				request.getRequestDispatcher("/homePublic.do").forward(request, response);
			}else{
				response.getWriter().write("Le pseudo n'existe pas");
			}
			
		}  	
		
		/* (non-Java-doc)
		 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doGet(request, response);
		}   	  	    
	}