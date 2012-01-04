package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxManagerRemote;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.web.utils.SessionManager;

@WebServlet(urlPatterns="/chat")
public class Chat extends SourceBoxServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 551653406115271400L;

	/*@EJB(mappedName = "SourceBoxLogicEAR/ChatBean/remote")
	private ChatBeanRemote chatBean;*/
	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {


		String alias = req.getParameter("alias");
		SessionManager.inBoxCheck(session, alias);
		
		String message = req.getParameter("msg");
		
		/*int userid = SessionManager.getUserId(session);
		String uname = SessionManager.getNickname(session);*/
		UserInfo user = SessionManager.getUserInfo(session);
		
		BoxManagerRemote box = SessionManager.getManager(session, alias, false);
		
		
		box.send(message);
		//chatBean.send(userid, alias, message);
		
		
	}
	
}
