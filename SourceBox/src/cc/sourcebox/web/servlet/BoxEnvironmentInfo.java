package cc.sourcebox.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.sourcebox.beans.BoxBeanRemote;
import cc.sourcebox.beans.UserEnvRemote;
import cc.sourcebox.web.utils.Utils;

@WebServlet(urlPatterns="/boxenv")
public class BoxEnvironmentInfo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1172396791580431424L;

	@EJB(mappedName = "SourceBoxLogicEAR/BoxBean/remote")
	private BoxBeanRemote boxbean;
	
	@EJB(mappedName="SourceBoxLogicEAR/UserEnv/remote")
	private UserEnvRemote userEnv;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		//String property = req.getParameter("property");

		HashMap<String, Object> respData = new HashMap<String, Object>();

		String alias = req.getParameter("alias");
		try {
			
			respData.put("private", boxbean.isPrivate(alias));
			respData.put("inbox", userEnv.isInBox(alias));
			respData.put("loggedAs", userEnv.getName());
			
			respData.put("success", true);
		} catch (Exception e) {
			respData.put("success", false);
			System.err.println(alias + " : " + e.getClass());
		}
		
		out.print(Utils.gson.toJson(respData));
		
		
/*		try {
			if (property.equals("private")) {
				String alias = req.getParameter("alias");
				respData.put("private", boxbean.isPrivate(alias));
				/**********
				 * If the box is protected by a password but I have already joined it
				 *//*
				respData.put("inbox", userEnv.isInBox(alias));
			}

			respData.put("success", true);
		} catch (Exception e) {
			respData.put("success", false);
		}

		out.print(Utils.gson.toJson(respData));
		
*/
		out.close();
	}

}
