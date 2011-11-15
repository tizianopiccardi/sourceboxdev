package cc.sourcebox.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.sourcebox.beans.BoxBeanRemote;
import cc.sourcebox.web.utils.EncodeDecode;

@WebServlet(urlPatterns = "/store")
public class Store extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	@EJB(mappedName = "SourceBoxLogicEAR/BoxBean/remote")
	private BoxBeanRemote bbr;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();

		HashMap<String, Object> result = new HashMap<String, Object>();

		String captcha = "" + req.getSession().getAttribute("captcha");

		if (/*!captcha.equals(req.getParameter("captcha"))*/false) {
			System.err.println("Captcha error: " + captcha + "!="
					+ req.getParameter("captcha"));

			result.put("success", false);
			result.put("message", "Wrong security code...");

		} else {

			String language = req.getParameter("language").toString();
			String code = req.getParameter("code").toString();
			String password = (req.getParameter("is_private").equals("true")) ? req
					.getParameter("password") : null;	
					
			boolean readonly = req.getParameter("readonly").equals("true");

			String alias = bbr.make(language, code, password, readonly);

			System.out.println("Language: " + language);
			System.out.println("Code: " + code);
			System.out.println("Password: " + password);
			System.out.println("ReadOnly: " + readonly);

			System.out.println("BOX ALIAS: " + alias);

			
			URL reconstructedURL = new URL(req.getScheme(),
					req.getServerName(),
					req.getServerPort(),
					req.getContextPath()+"/@"+alias);
			
			result.put("url", reconstructedURL);
			result.put("success", true);
			result.put("alias", alias);
		}
		out.print(EncodeDecode.get().encode(result));

		out.close();
	}

}
