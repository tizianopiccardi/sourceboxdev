package cc.sourcebox.beans.query;

import java.util.Hashtable;

public class Queries {

	private static Hashtable<String, String> q = new Hashtable<String, String>();

	private Queries() {
	}

	public static String get(String n) {
		return q.get(n);
	}

	static {
		q.put("CLEANER_GETUSER",
				"select u, b.alias from User u join u.inbox i join i.box b where u.lastActivity < :limit order by b.alias");
		q.put("CLEANER_DELUSER",
				"delete from User u where u.lastActivity < :limit");

		q.put("BOXES_GET",
				"SELECT r,b from Revision r join r.box b where (b.alias=:alias and b.password=:pwd)"
						+ "OR b.idboxes = (select b.idboxes from Inbox i join i.user u join i.box b where u.iduser = :iduser and b.alias=:alias)"
						+ " order by r.rev desc");

		q.put("BOXES_SIMPLEGET", "SELECT b from Box b where b.alias=:alias");

		q.put("BOXES_CHAT",
				"SELECT m from Message m join m.box b where b.alias=:alias order by m.idmessages desc");

		q.put("BOXES_OPERATIONS",
				"SELECT o from Operation o join o.box b where o.idoperation>:lastid and b.alias=:alias order by o.idoperation");

		q.put("BOXES_LASTREV",
				"select r from Revision r where r.idrevision=(SELECT max(r.idrevision) from Revision r join r.box b where b.alias=:alias )");

		q.put("BOXES_DESTROY",
				"delete from Box b where b.alias=:alias and b.destroykey=:key");

		q.put("BOXES_REMOPERATIONS",
				"delete from Operation o where o.idoperation < :id and o.box = (select b from Box b where b.alias=:alias)");

		q.put("BOXES_REVISIONS",
				"select r from Revision r where r.box.alias=:alias and r.rev=:id");
		q.put("BOXES_LASTREVISION",
				"select max(r.rev) from Revision r where r.box.alias=:alias");

		q.put("BOXES_PRIVATE",
				"SELECT b.password from Box b where b.alias=:alias");

		q.put("USER_JOINBOX",
				"SELECT u.iduser from Inbox i join i.user u join i.box b where u.iduser = :userid and b.idboxes = :idbox");

		q.put("USER_SETCURSOR",
				"SELECT i from Inbox i join i.box b join i.user u where u.iduser=:iduser and b.alias=:alias");

		q.put("USER_GETIN",
				"SELECT i from Inbox i join i.box b join i.user u where b.alias=:alias and u.lastActivity > :limit");

	}

}
