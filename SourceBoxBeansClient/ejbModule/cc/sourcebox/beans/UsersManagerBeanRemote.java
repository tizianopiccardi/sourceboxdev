package cc.sourcebox.beans;
import javax.ejb.Remote;

@Remote
public interface UsersManagerBeanRemote {

	public int join(String name);
	
	public void heartBeat(int userid);
	
}
