package cc.sourcebox.web.servlet;

import java.net.URL;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxInfoBeanRemote;

@WebServlet(urlPatterns = "/store")
public class Store extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1457195029910148809L;

	@EJB(mappedName = "SourceBoxLogicEAR/BoxInfoBean/remote")
	private BoxInfoBeanRemote bbr;

	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {
		/*************
		 * Captcha check
		 */
		String captcha = "" + session.getAttribute("captcha");
		if (!captcha.equals(req.getParameter("captcha")))
			throw new RuntimeException("Wrong security code...");
		/*************/

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

		URL reconstructedURL = new URL(req.getScheme(), req.getServerName(),
				req.getServerPort(), req.getContextPath() + "/@" + alias);

		output.put("url", reconstructedURL);
		output.put("alias", alias);
	}

}
