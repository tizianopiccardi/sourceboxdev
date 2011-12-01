package cc.sourcebox.web.utils;

import javax.servlet.http.HttpSession;

public class SessionManager {

	private final static String SESSION_BOX_ALIAS = "BOX_";
	private final static String SESSION_BOX_LASTCHECK = "BOX_L_";
		
	
	
	public static void addBox(HttpSession session, String alias) {
		session.setAttribute(SESSION_BOX_ALIAS+alias, true);
	}
	
	public static void boxChecked(HttpSession session, String alias) {
		session.setAttribute(SESSION_BOX_LASTCHECK+alias, System.currentTimeMillis());
	}
	
	public static long getLastCheck(HttpSession session, String alias) {
		try {return (Long)session.getAttribute(SESSION_BOX_LASTCHECK+alias);
		}catch(Exception e) {return 0;}
	}
	
}
