package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxBeanRemote;
import cc.sourcebox.dto.RevisionDTO;
import cc.sourcebox.web.utils.SessionManager;

@WebServlet(urlPatterns="/revision")
public class Revision extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4507802720895436281L;

	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {
		

		String alias = req.getParameter("alias");
		SessionManager.inBoxCheck(session, alias);

		BoxBeanRemote box = SessionManager.getBox(session, alias, false);
		
		String rev = req.getParameter("revision");

		RevisionDTO revision = box.getRevision((rev==null)?null:Integer.valueOf(rev));
		
		if (revision!=null)
			output.put("revision", revision);
		else
			output.put("limit", true);
		
	}
	
}
