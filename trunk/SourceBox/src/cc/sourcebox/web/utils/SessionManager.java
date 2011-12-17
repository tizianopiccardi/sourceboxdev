package cc.sourcebox.web.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import sun.awt.windows.ThemeReader;

import cc.sourcebox.beans.BoxManagerRemote;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.web.exception.SecurityException;

public class SessionManager {

	//private final static String BOX_ALIAS = "BOX_";
	//private final static String BOX_LASTCHECK = "BOX_L_";
	//private final static String USERNAME = "nick";
	//private final static String USERID = "userID";
	
	//private final static String BOXMGR_JNDI = "SourceBoxLogicEAR/BoxManager/remote";
	
	//private final static String BOXSEQUENCE = "sequence_";
		
	public static boolean isInBox(HttpSession session, String alias) {
		return (session.getAttribute(JndiPaths.get("BOX_MGR")+"_"+alias)!=null);
	}
	
	/*public static void addBox(HttpSession session, String alias) {
		session.setAttribute(BOX_ALIAS+alias, true);
	}*/
	
	/*public static void boxChecked(HttpSession session, String alias) {
		session.setAttribute(BOX_LASTCHECK+alias, System.currentTimeMillis());
	}
	
	public static long getLastCheck(HttpSession session, String alias) {
		try {return (Long)session.getAttribute(BOX_LASTCHECK+alias);
		}catch(Exception e) {return 0;}
	}*/
	
	/*public static int getSequence(HttpSession session, String alias) {
		Object seq = session.getAttribute(BOXSEQUENCE+alias);
		return (seq!=null)?(Integer)seq:0;
	}
	
	public static void setSequence(HttpSession session, String alias, int val) {
		session.setAttribute(BOXSEQUENCE+alias, val);
	}
	*/
	
	/*
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
	}	*/
	
	public static void setUserInfo(HttpSession session, UserInfo u) {
		session.setAttribute("userinfo", u);
	}
	
	public static UserInfo getUserInfo(HttpSession session) {
		return (UserInfo)session.getAttribute("userinfo");
	}

	
	public static void inBoxCheck(HttpSession session, String alias) throws SecurityException  {
		if(!SessionManager.isInBox(session, alias)) throw new SecurityException();
	}
	
	public static BoxManagerRemote getManager(HttpServletRequest req, String alias, boolean create) throws SecurityException  {
		return SessionManager.get(req, JndiPaths.get("BOX_MGR"), BoxManagerRemote.class, create, alias);
	}
	
	

	
	static Context ctx = null;
	
	/************
	 * For convenience
	 * @param req
	 * @param jndiName
	 * @param cl
	 * @return
	 */
	public static <T extends Object> T get(HttpServletRequest req, String jndiName, Class<T> cl, boolean create) {
		return get(req, jndiName, cl, create, "");
	}
	
	/***********
	 * Returns the bean mapped at the given jndi path. It allows (using id) to store and 
	 * retrieve more bean of the same type (STATEFUL!) 
	 * @param req
	 * @param jndiName
	 * @param cl
	 * @param id
	 * @return
	 */
	public static <T extends Object> T get(HttpServletRequest req, String jndiName, Class<T> cl, boolean create, String id) {
		Object bean = req.getSession().getAttribute(jndiName+"_"+id);
		
		//Provo ad usare il bean per assicurarmi che sia funzionante
		try {
			bean.toString();
		}catch(Exception e) {bean=null;}

		if (bean==null && create) {
			try {
				if (ctx == null) ctx = new InitialContext();
				bean = ctx.lookup(jndiName);
			} catch (Exception e) {
				throw new RuntimeException("Error in bean creation... " + jndiName);
			}
			req.getSession().setAttribute(jndiName+"_"+id, bean);
		}
		return cl.cast(bean);
		
	}
	
	
	
	
	
	
	
	
	
}
