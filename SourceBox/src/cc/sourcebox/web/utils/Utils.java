package cc.sourcebox.web.utils;

import com.google.gson.Gson;

public class Utils {

	private static Gson gson = new Gson();

	public static String encode(Object o) {
		//VALUTA NEW
		return gson.toJson(o);
	}
	
	
}
