package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.UsersManagerBeanRemote;
import cc.sourcebox.web.utils.Utils;

import com.google.gson.JsonObject;


@WebServlet(urlPatterns="/edit")
public class Edit extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5621807639847885403L;

	@EJB(mappedName = "SourceBoxLogicEAR/UsersManagerBean/remote")
	private UsersManagerBeanRemote usersMgr;
	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {

		int userID = (Integer)session.getAttribute("userID");	
		String alias = req.getParameter("alias");
		
		/*********
		 * UPDATE cursor position
		 */
		JsonObject cursor = Utils.decodeObject(req.getParameter("c"));
		int line = cursor.get("line").getAsInt();
		int ch = cursor.get("ch").getAsInt();
		usersMgr.setCursorPos(alias, userID, line, ch);
		
		
		
		usersMgr.heartBeat(Utils.getUserId(session));
		
		
	}
	
	
	
}
