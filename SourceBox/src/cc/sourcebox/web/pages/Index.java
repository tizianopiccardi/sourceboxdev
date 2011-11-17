package cc.sourcebox.web.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@WebServlet(urlPatterns="")
public class Index extends HttpServlet {

	private Configuration cfg; 
	
	@Override
	public void init() throws ServletException {
		cfg = new Configuration();
        cfg.setServletContextForTemplateLoading(
                getServletContext(), "WEB-INF/templates");
        
    
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		HashMap<String, Object> params = new HashMap<String, Object>();
		Template t = null;

		
		
		if (req.getParameter("alias")==null)
			t = cfg.getTemplate("index.ftl");
		else {
			params.put("alias", req.getParameter("alias"));
			t = cfg.getTemplate("view.ftl");
		}
        
		
		try {
            t.process(params, out);
        } catch (TemplateException e) {
            throw new ServletException("Error while processing FreeMarker template", e);
        }
		
		
		out.close();
	}
	
}
