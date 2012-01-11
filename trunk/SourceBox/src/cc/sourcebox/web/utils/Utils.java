package cc.sourcebox.web.utils;

import java.util.ArrayList;
import java.util.List;

import cc.sourcebox.dto.InsertObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Utils {

	private static Gson gson = new Gson();
	private static JsonParser parser = new JsonParser();

	public static String encode(Object o) {
		//VALUTA NEW
		return gson.toJson(o);//to JSON
	}
	
	public static JsonObject decodeObject(String json) {
		return parser.parse(json).getAsJsonObject();
	}
	
	public static JsonArray decodeArray(String json) {
		return parser.parse(json).getAsJsonArray();
	}


	
	public static List<InsertObject> getInsertList(String json) {
		JsonArray jList = decodeArray(json);
		
		ArrayList<InsertObject> inserts = new ArrayList<InsertObject>(jList.size());
		
		for (int i = 0; i < jList.size(); i++) {
			JsonObject ins = jList.get(i).getAsJsonObject();
			
			InsertObject insert = new InsertObject();
			insert.setUid(ins.get("u").getAsInt());
			insert.setText(ins.get("s").getAsString());
			insert.setSq(ins.get("sq").getAsInt());
			
			insert.setFromLine(ins.get("f").getAsJsonObject().get("l").getAsInt());
			insert.setFromChar(ins.get("f").getAsJsonObject().get("c").getAsInt());
			insert.setToLine(ins.get("t").getAsJsonObject().get("l").getAsInt());
			insert.setToChar(ins.get("t").getAsJsonObject().get("c").getAsInt());
			inserts.add(insert);
		}
		
		return inserts;
	}
	

	
}
