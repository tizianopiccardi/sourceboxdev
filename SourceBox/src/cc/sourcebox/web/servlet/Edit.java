package cc.sourcebox.web.servlet;

import java.util.HashMap;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxBeanRemote;
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

	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {

		String alias = req.getParameter("alias");
		SessionManager.inBoxCheck(session, alias);

		BoxBeanRemote box = SessionManager.getBox(session, alias, false);
		
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

		if (inserts.size()>0)
			box.edit(inserts);

		
	}
	
	
	
}
