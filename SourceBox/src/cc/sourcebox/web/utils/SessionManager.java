package cc.sourcebox.web.utils;

import javax.servlet.http.HttpSession;

import cc.sourcebox.web.exception.SecurityException;

public class SessionManager {

	private final static String BOX_ALIAS = "BOX_";
	private final static String BOX_LASTCHECK = "BOX_L_";
	private final static String USERNAME = "nick";
	private final static String USERID = "userID";
		
	public static boolean isInBox(HttpSession session, String alias) {
		return (session.getAttribute(BOX_ALIAS+alias)!=null);
	}
	
	public static void addBox(HttpSession session, String alias) {
		session.setAttribute(BOX_ALIAS+alias, true);
	}
	
	public static void boxChecked(HttpSession session, String alias) {
		session.setAttribute(BOX_LASTCHECK+alias, System.currentTimeMillis());
	}
	
	public static long getLastCheck(HttpSession session, String alias) {
		try {return (Long)session.getAttribute(BOX_LASTCHECK+alias);
		}catch(Exception e) {return 0;}
	}
	
	public static String getNickname(HttpSession session) {
		Object name = session.getAttribute(USERNAME);
		return (name!=null)?name.toString():"";
	}
	
	public static void setNickname(HttpSession session, String nick) {
		session.setAttribute(USERNAME, nick);
	}
	
	public static int getUserId(HttpSession session) {
		return (Integer)session.getAttribute(USERID);
	}	
	
	public static void setUserId(HttpSession session, int id) {
		session.setAttribute(USERID, id);
	}	
	
	public static void inBoxCheck(HttpSession session, String alias) throws SecurityException  {
		if(!SessionManager.isInBox(session, alias)) throw new SecurityException();
	}
	
}
