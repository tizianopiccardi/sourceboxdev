package cc.sourcebox.beans;
import javax.ejb.Local;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.entities.Box;

@Local
public interface BoxInfoBeanLocal {

	public Box get(String alias);
	//public void notifyUpdate(String alias)  throws BoxNotFoundException;
	
}
