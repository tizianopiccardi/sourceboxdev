package cc.sourcebox.web.utils;

import com.google.gson.Gson;

public class Utils {

	public static Gson gson = new Gson();

	public static String encode(Object o) {
		return gson.toJson(o);
	}
	
	
}
