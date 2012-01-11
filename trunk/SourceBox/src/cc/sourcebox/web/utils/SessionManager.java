package cc.sourcebox.web.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import cc.sourcebox.beans.BoxBeanRemote;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.web.exception.SecurityException;

public class SessionManager {

	public static boolean isInBox(HttpSession session, String alias) {
		return (session.getAttribute(JndiPaths.get("BOX_MGR")+"_"+alias)!=null);
	}
	

	public static void setUserInfo(HttpSession session, UserInfo u) {
		session.setAttribute("userinfo", u);
	}
	
	public static UserInfo getUserInfo(HttpSession session) {
		return (UserInfo)session.getAttribute("userinfo");
	}

	
	public static void inBoxCheck(HttpSession session, String alias) throws SecurityException  {
		if(!SessionManager.isInBox(session, alias)) throw new SecurityException();
	}
	
	public static BoxBeanRemote getBox(HttpSession session, String alias, boolean create) throws SecurityException  {
		BoxBeanRemote mgr = null;
		
		/*while(true)
			try {*/
				mgr = SessionManager.get(session, JndiPaths.get("BOX_MGR"), BoxBeanRemote.class, create, alias);
				/*mgr.ping();
				break;
			}
		catch (Exception e) {SessionManager.destroy(session, JndiPaths.get("BOX_MGR"), alias);}
*/
		return mgr;
	}
	
	

	
	static Context ctx = null;
	
	/************
	 * For convenience
	 * @param req
	 * @param jndiName
	 * @param cl
	 * @return
	 */
	public static <T extends Object> T get(HttpSession session, String jndiName, Class<T> cl, boolean create) {
		return get(session, jndiName, cl, create, "");
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
	public static <T extends Object> T get(HttpSession session, String jndiName, Class<T> cl, boolean create, String id) {
		Object bean = session.getAttribute(jndiName+"_"+id);

		if (bean==null && create) {
			try {
				if (ctx == null) ctx = new InitialContext();
				bean = ctx.lookup(jndiName);
			} catch (Exception e) {
				throw new RuntimeException("Error in bean creation... " + jndiName);
			}
			session.setAttribute(jndiName+"_"+id, bean);
		}
		return cl.cast(bean);
		
	}

	
	public static void addDestroyer(HttpSession session, String alias, BoxBeanRemote b) {
		if (session.getAttribute("DESTROYER_"+alias)==null)
			session.setAttribute("DESTROYER_"+alias, new BoxDestroyer(b));
	}
	
	
	
}
