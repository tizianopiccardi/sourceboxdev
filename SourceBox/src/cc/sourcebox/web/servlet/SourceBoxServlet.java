package cc.sourcebox.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cc.sourcebox.web.utils.Utils;

public class SourceBoxServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5257979831768209196L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		/************
		 * Session start
		 */
		HttpSession session = req.getSession(true);
		HashMap<String, Object> output = new HashMap<String, Object>();
		try {
			
			process(req, session, output);

			output.put("success", true);
		} catch (Exception e) {
			output.clear();
			output.put("success", false);
			output.put("type", e.getClass().getSimpleName());
			output.put("message", e.getMessage());
			//e.printStackTrace();
		}
		
		out.print(Utils.encode(output));
		out.close();
		
	}
	
	
	public void process(HttpServletRequest req, HttpSession session, HashMap<String, Object> output) throws Exception {}

	
}
