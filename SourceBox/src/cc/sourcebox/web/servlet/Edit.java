package cc.sourcebox.web.servlet;

import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@WebServlet(urlPatterns="/edit")
public class Edit extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5621807639847885403L;

	
	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {

		
		
		
	}
	
	
	
}
