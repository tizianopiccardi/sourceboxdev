package cc.sourcebox.beans;
import javax.ejb.Remote;

@Remote
public interface UserEnvRemote {


	
	public void openBox(String alias);
	public void setName(String name);
	public String getName();
	public boolean isInBox(String alias);
	
	
	//public int getSequence();
	
}
