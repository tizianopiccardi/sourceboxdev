package cc.sourcebox.web.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
		return gson.toJson(o);
	}
	
	public static JsonObject decodeObject(String json) {
		return parser.parse(json).getAsJsonObject();
	}
	
	public static JsonArray decodeArray(String json) {
		return parser.parse(json).getAsJsonArray();
	}

	/*
	public static int getUserId(HttpSession session) {
		return Integer.valueOf(session.getAttribute("userID").toString());
	}*/
	
	
	public static List<InsertObject> getInsertList(String json) {
		JsonArray jList = decodeArray(json);
		
		ArrayList<InsertObject> inserts = new ArrayList<InsertObject>(jList.size());
		
		for (int i = 0; i < jList.size(); i++) {
			JsonObject ins = jList.get(i).getAsJsonObject();
			
			InsertObject insert = new InsertObject();
			insert.setSequence(ins.get("sq").getAsInt());
			insert.setText(ins.get("s").getAsString());
			
			insert.setFromLine(ins.get("f").getAsJsonObject().get("l").getAsInt());
			insert.setFromChar(ins.get("f").getAsJsonObject().get("c").getAsInt());
			insert.setToLine(ins.get("t").getAsJsonObject().get("l").getAsInt());
			insert.setToChar(ins.get("t").getAsJsonObject().get("c").getAsInt());
			inserts.add(insert);
		}
		
		return inserts;
	}
	
	

	
	/*
	
	public static Timestamp currentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	*/
	
	
	
	/*public static void main(String[] args) {
		String a = "[{\"sq\":21,\"f\":{\"l\":2,\"c\":13},\"t\":{\"l\":2,\"c\":13},\"s\":\"aa\"},{\"sq\":22,\"f\":{\"l\":2,\"c\":15},\"t\":{\"l\":2,\"c\":15},\"s\":\"aa\"},{\"sq\":23,\"f\":{\"l\":2,\"c\":17},\"t\":{\"l\":2,\"c\":17},\"s\":\"a\"},{\"sq\":24,\"f\":{\"l\":2,\"c\":18},\"t\":{\"l\":2,\"c\":18},\"s\":\"a\"},{\"sq\":25,\"f\":{\"l\":2,\"c\":19},\"t\":{\"l\":2,\"c\":19},\"s\":\"aa\"},{\"sq\":26,\"f\":{\"l\":2,\"c\":21},\"t\":{\"l\":2,\"c\":21},\"s\":\"a\"},{\"sq\":27,\"f\":{\"l\":2,\"c\":22},\"t\":{\"l\":2,\"c\":22},\"s\":\"a\"},{\"sq\":28,\"f\":{\"l\":2,\"c\":23},\"t\":{\"l\":2,\"c\":23},\"s\":\"a\"}]";
		
		new InsertObject();
		
	    long c = System.currentTimeMillis();
	    getInsertList(a);
	    System.out.println(System.currentTimeMillis()-c);
		
	}*/
	
}
