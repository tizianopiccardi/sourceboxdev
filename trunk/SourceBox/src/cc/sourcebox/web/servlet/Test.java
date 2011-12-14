package cc.sourcebox.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/test")
public class Test extends HttpServlet {

	private static final long serialVersionUID = -6335997819859633278L;

	private int i = 0;	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		out.print(i++);
		
		/*
		XRemote x = SessionManager.get(req, "SourceBoxLogicEAR/X/remote", XRemote.class,"1");
		out.print(x.get());
		*/
		out.close();
		
	}
	
}
/*public class Test extends SourceBoxServlet {

	private static final long serialVersionUID = -6335997819859633278L;

	@Override
	public void process(HttpServletRequest req, HttpSession session,
			HashMap<String, Object> output) throws Exception {


		output.put(""+System.currentTimeMillis(), "ok");
	}
	
}
*/