package cc.sourcebox.beans.query;

import java.util.Hashtable;

public class Queries {


	private static Hashtable<String, String> q=new Hashtable<String, String>();
	
	private Queries() {}
	public static String get(String n) {
		return q.get(n);
	}
	
	
	static {
		q.put("CLEANER_GETUSER", "select u, b.alias from User u join u.inbox i join i.box b where u.lastActivity < :limit order by b.alias");
		q.put("CLEANER_DELUSER", "delete from User u where u.lastActivity < :limit");

		
		q.put("BOXES_GET", "SELECT r,b from Revision r join r.box b where (b.alias=:alias and b.password=:pwd)" +
							"OR b.idboxes = (select b.idboxes from Inbox i join i.user u join i.box b where u.iduser = :iduser and b.alias=:alias)" +
							" order by r.rev desc");

		
	}
	
	
	
	
	
	/*private static Properties prop = null;
	public static String get(String q) {
		if (prop==null) {
			prop = new Properties();
			try {
				prop.load(new FileInputStream("queries.prop"));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return prop.getProperty(q);
	}
	*/
	
}
