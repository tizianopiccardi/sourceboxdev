package cc.sourcebox.web.servlet;

import java.sql.Timestamp;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxBeanRemote;
import cc.sourcebox.entities.Revision;
import cc.sourcebox.web.utils.SessionKeys;
import cc.sourcebox.web.utils.SessionManager;
import cc.sourcebox.web.utils.Utils;

@WebServlet(urlPatterns="/get")
public class Get extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1147497135400155556L;
	
	@EJB(mappedName = "SourceBoxLogicEAR/BoxBean/remote")
	private BoxBeanRemote boxbean;
	

	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {
		
		String alias = req.getParameter("alias");
		String password = req.getParameter("pass");
		
		
		Revision rev = (Revision)boxbean.get(Utils.getUserId(session), alias, password );
		
		//QUICK CHECK
		SessionManager.addBox(session, alias);
		//session.setAttribute(SessionKeys.get("SESSION_BOX_ALIAS",alias), true);
		//session.setAttribute(SessionKeys.get("SESSION_BOX_LASTCHECK",alias), System.currentTimeMillis());

		output.put("alias", rev.getBox().getAlias());
		output.put("code", rev.getSource());
		output.put("language", rev.getBox().getLanguage());
		output.put("readonly", rev.getBox().getReadonly());
		output.put("revision", rev.getRev());	
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
