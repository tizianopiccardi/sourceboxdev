package cc.sourcebox.beans;
import javax.ejb.Remote;

@Remote
public interface UsersManagerBeanRemote {

	public int join(String name);
	
	public void setCursorPos(String boxAlias, int userID, int line, int ch);
	
	public void heartBeat(int userid);
	
}
