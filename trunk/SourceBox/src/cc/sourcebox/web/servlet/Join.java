package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.UsersManagerBeanRemote;

@WebServlet(urlPatterns="/join")
public class Join extends SourceBoxServlet {

	private static final long serialVersionUID = 8233025717568950848L;

	@EJB(mappedName = "SourceBoxLogicEAR/UsersManagerBean/remote")
	private UsersManagerBeanRemote usersMgr;

	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {
		
		String nick = req.getParameter("nick");
		
		/*******
		 * Save the username and the generated user id
		 */
		session.setAttribute("nick", nick);
		session.setAttribute("userID", usersMgr.join(nick));
		
		
	}


	
}