package cc.sourcebox.beans;
import javax.ejb.Local;

import cc.sourcebox.entities.Box;

@Local
public interface UsersManagerBeanLocal {
	public void joinBox(int userid, Box box);
}
