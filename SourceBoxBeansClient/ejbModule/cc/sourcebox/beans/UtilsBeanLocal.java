package cc.sourcebox.beans;


import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import cc.sourcebox.entities.Operation;

@Local
public interface UtilsBeanLocal {

	public Date getUsersTimeDeadline();
	
	public String digest(String original, List<Operation> operations);
	public String getRandomString(int size);
}
