package cc.sourcebox.dto;

import java.util.ArrayList;
import java.util.List;

public class EventsDTO {

	List<ChatMessage> msg = new ArrayList<ChatMessage>();

	List<OperationDTO> op = new ArrayList<OperationDTO>();

	public List<ChatMessage> getMsg() {
		return msg;
	}

	public void setMsg(List<ChatMessage> msg) {
		this.msg = msg;
	}

	public List<OperationDTO> getOp() {
		return op;
	}

	public void setOp(List<OperationDTO> op) {
		this.op = op;
	}
	
	public void add(ChatMessage chat) {
		msg.add(chat);
	}

	
	public void add(OperationDTO oper) {
		op.add(oper);
	}
}
