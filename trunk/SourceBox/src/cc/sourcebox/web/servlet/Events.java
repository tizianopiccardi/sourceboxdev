package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxManagerRemote;
import cc.sourcebox.web.exception.SecurityException;
import cc.sourcebox.web.utils.SessionManager;

@WebServlet(urlPatterns="/events")
public class Events extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 92355191175095283L;


	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {


		String alias = req.getParameter("alias");
		if (!SessionManager.isInBox(session, alias)) throw new SecurityException();

		BoxManagerRemote box = SessionManager.getManager(session, alias, false);

		box.heartBeat();
		for (int i = 0; i < 15; i++) {

			if (box.somethingNew()) {
				output.put("events", box.getEvents());
				break;
			}
			
			
			Thread.sleep(2000);
		}
		
		
	}
	
}
