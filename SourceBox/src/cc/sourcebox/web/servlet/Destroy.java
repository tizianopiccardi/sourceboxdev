package cc.sourcebox.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.sourcebox.beans.BoxInfoBeanRemote;

@WebServlet(urlPatterns="/destroy")
public class Destroy extends HttpServlet {

	private static final long serialVersionUID = -1030475469127579398L;

	@EJB(mappedName = "SourceBoxLogicEAR/BoxInfoBean/remote")
	private BoxInfoBeanRemote bbr;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {


		String alias = req.getParameter("alias");
		String key = req.getParameter("key");
		
		bbr.destroy(alias, key);
		
		PrintWriter out = resp.getWriter();

		resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		resp.setHeader("Location", "./");

		resp.setContentType("text/html");

		
		
		
	}
	
	
}


