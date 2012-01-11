package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxBeanRemote;
import cc.sourcebox.web.utils.SessionManager;

@WebServlet(urlPatterns="/save")
public class Save extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2045546592011857067L;


	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {


		String alias = req.getParameter("alias");
		SessionManager.inBoxCheck(session, alias);

		BoxBeanRemote box = SessionManager.getBox(session, alias, false);
		
		box.save();
		
		
	}
	
	
}
