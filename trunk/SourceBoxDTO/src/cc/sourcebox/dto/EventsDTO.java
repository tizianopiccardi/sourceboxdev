package cc.sourcebox.dto;

import java.util.ArrayList;
import java.util.List;

public class EventsDTO {

	List<ChatMessage> msg = new ArrayList<ChatMessage>();

	List<InsertObject> op = new ArrayList<InsertObject>();

	public List<ChatMessage> getMsg() {
		return msg;
	}

	public void setMsg(List<ChatMessage> msg) {
		this.msg = msg;
	}

	public List<InsertObject> getOp() {
		return op;
	}

	public void setOp(List<InsertObject> op) {
		this.op = op;
	}
	
	public void add(ChatMessage chat) {
		msg.add(chat);
	}

	
	public void add(InsertObject oper) {
		op.add(oper);
	}
}
