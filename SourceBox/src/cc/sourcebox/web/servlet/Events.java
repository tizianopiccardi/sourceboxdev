package cc.sourcebox.web.servlet;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxBeanRemote;
import cc.sourcebox.beans.ChatBeanRemote;
import cc.sourcebox.beans.BoxManagerRemote;
import cc.sourcebox.beans.UsersManagerBeanRemote;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.web.exception.SecurityException;
import cc.sourcebox.web.utils.SessionManager;

@WebServlet(urlPatterns="/events")
public class Events extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 92355191175095283L;

/*	@EJB(mappedName = "SourceBoxLogicEAR/UsersManagerBean/remote")
	private UsersManagerBeanRemote usersMgr;
	@EJB(mappedName = "SourceBoxLogicEAR/BoxBean/remote")
	private BoxBeanRemote boxbean;
	@EJB(mappedName = "SourceBoxLogicEAR/ChatBean/remote")
	private ChatBeanRemote chatBean;
	*/
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {


		//String alias = req.getParameter("alias");
		//if (!SessionManager.isInBox(session, alias)) throw new SecurityException();

		//EventsRemote events = SessionManager.get(req, "SourceBoxLogicEAR/Events/remote", EventsRemote.class, false);
		BoxManagerRemote box = SessionManager.get(req, "SourceBoxLogicEAR/Events/remote", BoxManagerRemote.class, true);
		box.init("bla");

		for (int i = 0; i < 10; i++) {
			System.out.println("POLL"  +box.somethingNew());
			if (box.somethingNew()) {
				output.put("event", box
						.getEvents());
				break;
			}
			
			
			Thread.sleep(2000);
		}
		
		
	}
	
}
