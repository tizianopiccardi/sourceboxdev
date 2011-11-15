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

import cc.sourcebox.beans.UserEnvRemote;
import cc.sourcebox.web.utils.Utils;

@WebServlet(urlPatterns="/join")
public class Join extends HttpServlet {


	@EJB(mappedName="SourceBoxLogicEAR/UserEnv/remote")
	private UserEnvRemote userEnv;
	
	private static final long serialVersionUID = -2640921971396804806L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {


		PrintWriter out = resp.getWriter();
		HashMap<String, Object> respData = new HashMap<String, Object>();

		String nick = req.getParameter("nick");
		try {
			userEnv.setName(nick);
			
			//respData.put("seq", userEnv.getSequence());
			
			respData.put("name", userEnv.getName());
			
			respData.put("success", true);
		} catch (Exception e) {
			respData.put("success", false);
		}
		
		out.print(Utils.gson.toJson(respData));
		out.close();
		
		
		
	}
	
	
}
