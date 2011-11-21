package cc.sourcebox.web.utils;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Utils {

	private static Gson gson = new Gson();

	public static String encode(Object o) {
		//VALUTA NEW
		return gson.toJson(o);
	}
	
	public static JsonObject decodeObject(String json) {
		return new JsonParser().parse(json).getAsJsonObject();
	}
	
	public static JsonArray decodeArray(String json) {
		return new JsonParser().parse(json).getAsJsonArray();
	}
	
	
	
	
	
	public static int getUserId(HttpSession session) {
		return Integer.valueOf(session.getAttribute("userID").toString());
	}
	
	/*public static void main(String[] args) {
		String a = "	[{\"sq\":71,\"f\":{\"l\":0,\"c\":104},\"t\":{\"l\":0,\"c\":104},\"s\":\"f\"},{\"sq\":72,\"f\":{\"l\":0,\"c\":105},\"t\":{\"l\":0,\"c\":105},\"s\":\"fff\"}]";
		
		Gson gson = new Gson();
		


	    JsonParser parser = new JsonParser();
	    JsonElement array = parser.parse(a);
	    
	    array.isJsonArray();
	    
	    System.out.println(array);
		
	}*/
	
}
