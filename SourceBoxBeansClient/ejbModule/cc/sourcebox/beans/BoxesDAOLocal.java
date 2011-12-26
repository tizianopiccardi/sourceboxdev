package cc.sourcebox.beans;
import javax.ejb.Local;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.entities.Revision;

@Local
public interface BoxesDAOLocal {
	public String make(String language, String body, String password, Boolean readonly);
	public void save(String alias, String body) throws BoxNotFoundException;
	public Revision get(int userId, String alias, String password) throws BoxNotFoundException;
}
