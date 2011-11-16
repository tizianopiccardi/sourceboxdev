package cc.sourcebox.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(urlPatterns="/test")
public class Test extends SourceBoxServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6335997819859633278L;

	@Override
	public void process(HttpServletRequest req) throws Exception {

		output.put("ciao", "ok");
		throw new RuntimeException();
		
	}
	
}
