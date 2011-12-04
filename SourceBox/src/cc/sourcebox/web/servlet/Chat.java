package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.ChatBeanRemote;
import cc.sourcebox.web.utils.SessionManager;

@WebServlet(urlPatterns="/chat")
public class Chat extends SourceBoxServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 551653406115271400L;

	@EJB(mappedName = "SourceBoxLogicEAR/ChatBean/remote")
	private ChatBeanRemote chatBean;
	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {


		String alias = req.getParameter("alias");
		SessionManager.inBoxCheck(session, alias);
		
		String message = req.getParameter("msg");
		int userid = SessionManager.getUserId(session);
		
		chatBean.send(userid, alias, message);
		
		
	}
	
}
