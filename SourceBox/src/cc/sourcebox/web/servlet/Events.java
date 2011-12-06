package cc.sourcebox.web.servlet;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxBeanRemote;
import cc.sourcebox.beans.ChatBeanRemote;
import cc.sourcebox.beans.UsersManagerBeanRemote;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.web.exception.SecurityException;
import cc.sourcebox.web.utils.SessionKeys;
import cc.sourcebox.web.utils.SessionManager;

@WebServlet(urlPatterns="/events")
public class Events extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 92355191175095283L;

	@EJB(mappedName = "SourceBoxLogicEAR/UsersManagerBean/remote")
	private UsersManagerBeanRemote usersMgr;
	@EJB(mappedName = "SourceBoxLogicEAR/BoxBean/remote")
	private BoxBeanRemote boxbean;
	@EJB(mappedName = "SourceBoxLogicEAR/ChatBean/remote")
	private ChatBeanRemote chatBean;
	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {


		String alias = req.getParameter("alias");
		if (!SessionManager.isInBox(session, alias)) throw new SecurityException();
		
		//long lastBoxCheck = SessionManager.getLastCheck(session, alias);
		
		int localSequence = SessionManager.getSequence(session, alias);
		for (int i = 0; i < 10; i++) {
			
			int boxSequence = boxbean.getSequence(alias);
			
			if (boxSequence>localSequence) {
				
				List<UserInfo> users = usersMgr.getUsers(req.getParameter("alias"));
				output.put("users", users);
				
				//output.put("chat", chatBean.get(alias, lastBoxCheck));
				
				SessionManager.setSequence(session, alias, boxSequence);
				break;
			}
			
			
			Thread.sleep(2000);
		}
		//WAIT IF ERROR
		//else Thread.sleep(2000);
		
	
		
		
		
	}
	
}
