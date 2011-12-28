package cc.sourcebox.web.servlet;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxInfoBeanRemote;
import cc.sourcebox.beans.BoxManagerRemote;
import cc.sourcebox.beans.UsersDAORemote;
import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.web.utils.SessionManager;
import cc.sourcebox.web.utils.Utils;

import com.google.gson.JsonObject;


@WebServlet(urlPatterns="/edit")
public class Edit extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5621807639847885403L;
/*
	@EJB(mappedName = "SourceBoxLogicEAR/UsersManagerBean/remote")
	private UsersManagerBeanRemote usersMgr;
	*/
	/*@EJB(mappedName = "SourceBoxLogicEAR/BoxBean/remote")
	private BoxBeanRemote boxbean;*/
	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {

		String alias = req.getParameter("alias");
		SessionManager.inBoxCheck(session, alias);
		
		
		//int userID = (Integer)session.getAttribute("userID");

		BoxManagerRemote box = SessionManager.getManager(req, alias, false);
		
		/*********
		 * UPDATE cursor position
		 */
		if (!req.getParameter("c").equals("{}")) {//
			JsonObject cursor = Utils.decodeObject(req.getParameter("c"));
			int line = cursor.get("line").getAsInt();
			int ch = cursor.get("ch").getAsInt();
			box.setCursor(line, ch);
		}
		
		
		List<InsertObject> inserts = Utils.getInsertList(req.getParameter("e"));
		if (inserts.size()>1)
			box.edit(inserts);
			//boxbean.edit(alias, userID, inserts);
		
		
		//usersMgr.heartBeat(SessionManager.getUserInfo(session).getUserid());
		
		
	}
	
	
	
}
