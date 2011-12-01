package cc.sourcebox.web.servlet;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxBeanRemote;
import cc.sourcebox.beans.UsersManagerBeanRemote;
import cc.sourcebox.dto.UserInfo;
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
	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {

		String alias = req.getParameter("alias");
		long lastBoxCheck = SessionManager.getLastCheck(session, alias);
		
		for (int i = 0; i < 10; i++) {
			
			if (boxbean.lastEvent(alias)>lastBoxCheck) {
				
				List<UserInfo> users = usersMgr.getUsers(req.getParameter("alias"));
				output.put("users", users);
				
				
				SessionManager.boxChecked(session, alias);
				break;
			}
			
			
			Thread.sleep(2000);
		}
		
		
	
		
		
		
	}
	
}
