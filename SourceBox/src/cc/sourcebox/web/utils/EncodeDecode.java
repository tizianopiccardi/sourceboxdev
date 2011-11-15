package cc.sourcebox.web.utils;

import com.google.gson.Gson;

public class EncodeDecode {

	private static EncodeDecode ed = null;
	
	private static Gson gson = null;
	
	private EncodeDecode() {}
	
	public static EncodeDecode get() {
		if (ed==null) { ed = new EncodeDecode(); gson = new Gson();}
		return ed;
	}
	
	
	public String encode(Object obj) {
		return gson.toJson(obj);
	}
	
	
}
