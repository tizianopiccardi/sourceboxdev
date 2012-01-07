package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Local;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.entities.Box;
import cc.sourcebox.entities.Message;
import cc.sourcebox.entities.Operation;
import cc.sourcebox.entities.Revision;
import cc.sourcebox.entities.User;

@Local
public interface BoxesDAOLocal {
	public String make(String language, String body, String password, Boolean readonly);
	//public void save(String alias, String body) throws BoxNotFoundException;
	public Revision get(int userId, String alias, String password) throws BoxNotFoundException;
	public List<Message> getChatHistory(String alias, int n);
	
	public void sendChat(Message msg);
	
	public Box get(String alias);
	
	public List<InsertObject> edit(String alias, List<InsertObject> inserts);
	public List<Operation> getOperations(String alias, int from);
	
	public void save(String alias);
	
	public void destroy (String alias, String key);
	
	/*
	public User getUser(int id);
	public Box getBox(int id);*/
}
