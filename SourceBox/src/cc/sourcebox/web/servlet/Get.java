package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxInfoBeanRemote;
import cc.sourcebox.beans.BoxManagerRemote;
import cc.sourcebox.beans.UsersDAORemote;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.entities.Revision;
import cc.sourcebox.web.utils.SessionManager;

@WebServlet(urlPatterns="/get")
public class Get extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1147497135400155556L;
	
	@EJB(mappedName = "SourceBoxLogicEAR/BoxInfoBean/remote")
	private BoxInfoBeanRemote boxbean;
	
	/*@EJB(mappedName = "SourceBoxLogicEAR/UsersManagerBean/remote")
	private UsersManagerBeanRemote users;*/

	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {
		
		String alias = req.getParameter("alias");
		String password = req.getParameter("pass");
		
		UserInfo user = SessionManager.getUserInfo(session);
		Revision rev = boxbean.get(user.getUserid(), alias, password );


		//SessionManager.addBox(session, alias);
		
		//mgr.init(alias);
		
		//BoxManagerRemote mgr = SessionManager.get(req, JndiPaths.get("BOX_MGR"), BoxManagerRemote.class, true, alias);
		BoxManagerRemote box = SessionManager.getManager(req, alias, true);
		box.init(user, alias);
		
		
		//SessionManager.setSequence(session, alias, rev.getBox().getSequence());
		//session.setAttribute(SessionKeys.get("SESSION_BOX_ALIAS",alias), true);
		//session.setAttribute(SessionKeys.get("SESSION_BOX_LASTCHECK",alias), System.currentTimeMillis());

		output.put("alias", alias);
		output.put("code", rev.getSource());
		output.put("language", rev.getBox().getLanguage());
		output.put("readonly", rev.getBox().getReadonly());
		output.put("revision", rev.getRev());	
		output.put("users", boxbean.getUsers(alias));
		output.put("chat", boxbean.getChatHistory(alias));
	}
	
/*
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			doGet(req, resp);
	}
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//req.getSession();
		
		PrintWriter out = resp.getWriter();
		HashMap<String, Object> boxInfo = new HashMap<String, Object>();
		
		try {			
			
			String alias = req.getParameter("id");
			String password = req.getParameter("pw");
			
			Revision rev = (Revision)boxbean.get(alias, password);
			
			boxInfo.put("success", true);
			boxInfo.put("alias", rev.getBox().getAlias());
			boxInfo.put("code", rev.getSource());
			boxInfo.put("language", rev.getBox().getLanguage());
			boxInfo.put("readonly", rev.getBox().getReadonly());
			boxInfo.put("revision", rev.getRev());	
			
		} catch (Exception e) {
			boxInfo.put("success", false);
			boxInfo.put("error", e.getClass().getSimpleName());
		}
		
		
		out.print(Utils.gson.toJson(boxInfo));
		
		out.close();
		
	}*/
	
}
