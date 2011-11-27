package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Remote;

import cc.sourcebox.dto.UserInfo;

@Remote
public interface UsersManagerBeanRemote {

	public int join(String name);
	
	public void setCursorPos(String boxAlias, int userID, int line, int ch);
	
	public void heartBeat(int userid);
	
	public List<UserInfo> getUsers(String alias);
	
}
