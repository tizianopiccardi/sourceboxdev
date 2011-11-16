package cc.sourcebox.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(urlPatterns="/join")
public class Join extends SourceBoxServlet {

	private static final long serialVersionUID = 8233025717568950848L;

	@Override
	public void process(HttpServletRequest req) throws Exception {
		String nick = req.getParameter("nick");
		session.setAttribute("nick", nick);
	}

//	@EJB(mappedName="SourceBoxLogicEAR/UserEnv/remote")
//	private UserEnvRemote userEnv;
	
/*	private static final long serialVersionUID = -2640921971396804806L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession(true);

		PrintWriter out = resp.getWriter();
		HashMap<String, Object> respData = new HashMap<String, Object>();

		String nick = req.getParameter("nick");
		try {
			//userEnv.setName(nick);
			session.setAttribute("name", nick);
			//respData.put("seq", userEnv.getSequence());
			
			//respData.put("name", userEnv.getName());
			
			respData.put("success", true);
		} catch (Exception e) {
			respData.put("success", false);
		}
		
		out.print(Utils.gson.toJson(respData));
		out.close();
		
		
		
	}
	*/
	
}
