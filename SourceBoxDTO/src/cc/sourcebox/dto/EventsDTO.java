package cc.sourcebox.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class EventsDTO implements Serializable, Cloneable {

	private static final long serialVersionUID = -5755747751489416876L;

	List<ChatMessage> msg = new ArrayList<ChatMessage>();

	List<InsertObject> op = new ArrayList<InsertObject>();

	Hashtable<Integer, CursorsDTO> cursors = new Hashtable<Integer, CursorsDTO>();

	List<UserInfo> users = new ArrayList<UserInfo>();

	List<Action> actions = new ArrayList<Action>();

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public List<UserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}

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

	public void add(UserInfo u) {
		users.add(u);
	}

	public void add(CursorsDTO c) {
		cursors.put(c.getUid(), c);
	}
	
	public void add(Action c) {
		actions.add(c);
	}

	public boolean hasEvents() {
		return (msg.size() + op.size() + cursors.size() + users.size() + actions.size()) > 0;
	}

	public Hashtable<Integer, CursorsDTO> getCursors() {
		return cursors;
	}

	public void setCursors(Hashtable<Integer, CursorsDTO> cursors) {
		this.cursors = cursors;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		EventsDTO e = new EventsDTO();
		e.setMsg(new ArrayList<ChatMessage>(msg));
		e.setOp(new ArrayList<InsertObject>(op));
		e.setCursors(new Hashtable<Integer, CursorsDTO>(cursors));
		e.setUsers(new ArrayList<UserInfo>(users));
		e.setActions(new ArrayList<Action>(actions));
		return e;
	}

	public EventsDTO extract() throws CloneNotSupportedException {
		EventsDTO e = null;
		synchronized (this) {
			e = (EventsDTO) this.clone();
			msg.clear();
			op.clear();
			cursors.clear();
			users.clear();
			actions.clear();
		}

		return e;
	}

}
