package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Remote;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.entities.Box;
import cc.sourcebox.entities.User;

@Remote
public interface UsersDAORemote {

	public int join(String name);
	
	public void setCursorPos(String boxAlias, int userID, int line, int ch) throws BoxNotFoundException;
	
	public void heartBeat(int userid);
	
	public List<UserInfo> getUsers(String alias);
	
	public void joinBox(int userid, Box box);
	
	public User get(int userid);
}
