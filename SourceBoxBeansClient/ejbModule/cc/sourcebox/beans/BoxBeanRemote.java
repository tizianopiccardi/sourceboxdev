package cc.sourcebox.beans;
import javax.ejb.Remote;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.entities.Revision;
import cc.sourcebox.entities.User;

@Remote
public interface BoxBeanRemote {

	public String make(String language, String body, String password, Boolean readonly);
	
	public void save(String alias, String body) throws BoxNotFoundException;
	
	public Revision get(int userId, String alias, String password) throws BoxNotFoundException;
	
	public Boolean isPrivate(String alias) throws BoxNotFoundException;
	
	//IN LOCAL USER
	//public void joinBox(int userid);
	
}
