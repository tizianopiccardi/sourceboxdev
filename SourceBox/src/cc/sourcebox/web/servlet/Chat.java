package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxBeanRemote;
import cc.sourcebox.web.utils.SessionManager;

@WebServlet(urlPatterns="/chat")
public class Chat extends SourceBoxServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 551653406115271400L;

	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {

		String alias = req.getParameter("alias");
		String message = req.getParameter("msg");
		SessionManager.inBoxCheck(session, alias);
		
		BoxBeanRemote box = SessionManager.getBox(session, alias, false);
		
		box.send(message);

	}
	
}
