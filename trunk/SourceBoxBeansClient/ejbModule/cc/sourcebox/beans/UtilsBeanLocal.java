package cc.sourcebox.beans;


import java.util.Date;

import javax.ejb.Local;

@Local
public interface UtilsBeanLocal {

	public Date getUsersTimeDeadline();
	
}
