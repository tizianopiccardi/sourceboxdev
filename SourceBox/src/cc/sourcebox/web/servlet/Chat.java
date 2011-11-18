package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns="/chat")
public class Chat extends SourceBoxServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 551653406115271400L;

	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {


		
		
		
	}
	
}
