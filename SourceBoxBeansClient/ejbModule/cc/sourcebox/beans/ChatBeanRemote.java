package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Remote;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.entities.Message;

@Remote
public interface ChatBeanRemote {

	public void send(int userid, String alias, String message) throws BoxNotFoundException;
	
	//public List<Message> get(String alias, long last) throws BoxNotFoundException;
	
	
}
