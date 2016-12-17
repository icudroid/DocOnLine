package net.dkahn.web.dwrs;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;

public class Session {
	public void setAttribute(String att,String value){
		HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
		request.getSession().setAttribute(att, value);
	}
}
