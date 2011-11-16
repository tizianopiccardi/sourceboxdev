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

	HttpSession session;
	HashMap<String, Object> output = new HashMap<String, Object>();
	PrintWriter out;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		out = resp.getWriter();
		/************
		 * Session start
		 */
		session = req.getSession(true);
		
		try {
			process(req);

			output.put("success", true);
		} catch (Exception e) {
			output.clear();
			output.put("success", false);
			output.put("type", e.getClass().getSimpleName());
			output.put("message", e.getMessage());
		}
		
		out.print(Utils.gson.toJson(output));
		out.close();
		
	}
	
	
	public void process(HttpServletRequest req) throws Exception {}
	
	
}
