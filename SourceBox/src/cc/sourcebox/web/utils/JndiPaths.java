package cc.sourcebox.web.utils;

import java.util.Hashtable;

public class JndiPaths {

	private static Hashtable<String, String> values = new Hashtable<String, String>();

	private JndiPaths() {}
	public static String get(String key) {
		return values.get(key);
	}

	static {
		values.put("BOX_MGR", "SourceBoxLogicEAR/BoxBean/remote");
	}
	
	
	
}
