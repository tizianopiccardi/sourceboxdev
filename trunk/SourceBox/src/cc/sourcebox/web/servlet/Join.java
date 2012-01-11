package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxTasksBeanRemote;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.web.utils.SessionManager;

@WebServlet(urlPatterns="/join")
public class Join extends SourceBoxServlet {

	private static final long serialVersionUID = 8233025717568950848L;

	@EJB(mappedName = "SourceBoxLogicEAR/BoxTasksBean/remote")
	private BoxTasksBeanRemote boxbean;

	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {
		
		String nick = req.getParameter("nick");
		session.setMaxInactiveInterval(180);
		
		/*******
		 * Save the username and the generated user id
		 */

		int uid = boxbean.userJoin(nick);
		SessionManager.setUserInfo(session, new UserInfo(uid, nick));

		output.put("userId", uid);
		output.put("nick", nick);
		
	}


	
}
