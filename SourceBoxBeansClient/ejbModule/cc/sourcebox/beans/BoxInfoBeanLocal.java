package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Local;

import cc.sourcebox.dto.InsertObject;

@Local
public interface BoxInfoBeanLocal {

	//public Box get(String alias);
	//public void notifyUpdate(String alias)  throws BoxNotFoundException;
	public void sendChat(int userid, String alias, String message);
	
	public List<InsertObject> edit(int uid, String alias, List<InsertObject> inserts);
	
}
