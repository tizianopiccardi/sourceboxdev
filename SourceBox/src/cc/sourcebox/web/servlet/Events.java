package cc.sourcebox.web.servlet;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.UsersManagerBeanRemote;
import cc.sourcebox.dto.UserInfo;

@WebServlet(urlPatterns="/events")
public class Events extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 92355191175095283L;

	@EJB(mappedName = "SourceBoxLogicEAR/UsersManagerBean/remote")
	private UsersManagerBeanRemote usersMgr;
	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {

		Thread.sleep(50000);

		List<UserInfo> users = usersMgr.getUsers(req.getParameter("alias"));
		output.put("users", users);
		
		
		
		
	}
	
}
