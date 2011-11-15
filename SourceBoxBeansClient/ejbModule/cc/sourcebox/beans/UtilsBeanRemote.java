package cc.sourcebox.beans;
import javax.ejb.Remote;

@Remote
public interface UtilsBeanRemote {

	public String getRandomString(int size);
	
}
