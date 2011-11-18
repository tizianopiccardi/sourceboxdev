package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns="/join")
public class Join extends SourceBoxServlet {

	private static final long serialVersionUID = 8233025717568950848L;


	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {
		String nick = req.getParameter("nick");
		session.setAttribute("nick", nick);
	}


	
}
