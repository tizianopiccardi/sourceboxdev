package cc.sourcebox.beans;
import javax.ejb.Local;

import cc.sourcebox.entities.Box;
import cc.sourcebox.entities.User;

@Local
public interface UsersManagerBeanLocal {
	public void joinBox(int userid, Box box);
	public User get(int userid);
}
