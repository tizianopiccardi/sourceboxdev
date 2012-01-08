package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxTasksBeanRemote;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.web.utils.SessionManager;

@WebServlet(urlPatterns="/boxenv")
public class BoxEnvironmentInfo extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1172396791580431424L;

	@EJB(mappedName = "SourceBoxLogicEAR/BoxTasksBean/remote")
	private BoxTasksBeanRemote boxbean;

	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {


		String alias = req.getParameter("alias");
		
		output.put("isPrivate", boxbean.isPrivate(alias));
		output.put("inbox", SessionManager.isInBox(session, alias));
		
		UserInfo user = SessionManager.getUserInfo(session);
		output.put("loggedAs", (user==null)?"":user.getUsername());
		
		output.put("userId", (user==null)?0:user.getUserid());
		
	}
	


}
