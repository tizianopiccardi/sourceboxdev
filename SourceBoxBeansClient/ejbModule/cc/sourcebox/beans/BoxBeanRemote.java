package cc.sourcebox.beans;
import javax.ejb.Remote;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.entities.Revision;

@Remote
public interface BoxBeanRemote {

	public String make(String language, String body, String password, Boolean readonly);
	
	public void save(String alias, String body) throws BoxNotFoundException;
	
	public Revision get(String alias, String password) throws BoxNotFoundException;
	
	public Boolean isPrivate(String alias) throws BoxNotFoundException;
	
	
}
