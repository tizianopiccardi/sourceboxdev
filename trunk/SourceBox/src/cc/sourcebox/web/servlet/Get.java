package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxTasksBeanRemote;
import cc.sourcebox.beans.BoxBeanRemote;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.entities.Revision;
import cc.sourcebox.web.utils.SessionManager;

@WebServlet(urlPatterns="/get")
public class Get extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1147497135400155556L;
	
	@EJB(mappedName = "SourceBoxLogicEAR/BoxTasksBean/remote")
	private BoxTasksBeanRemote boxbean;

	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {
		
		String alias = req.getParameter("alias");
		String password = req.getParameter("pass");
		
		UserInfo user = SessionManager.getUserInfo(session);
		Revision rev = boxbean.get(user.getUserid(), alias, password );

		BoxBeanRemote box = SessionManager.getBox(session, alias, true);
		box.init(user, alias);
		
		SessionManager.addDestroyer(session, alias, box);

		output.put("alias", alias);
		output.put("code", rev.getSource());
		output.put("language", rev.getBox().getLanguage());
		output.put("readonly", rev.getBox().getReadonly());
		output.put("revision", rev.getRev());	
		output.put("users", boxbean.getUsers(alias));
		output.put("chat", boxbean.getChatHistory(alias));
		output.put("operations", boxbean.getOperations(alias, rev.getOperation().getIdoperation()));
		
	}
	
	
}
