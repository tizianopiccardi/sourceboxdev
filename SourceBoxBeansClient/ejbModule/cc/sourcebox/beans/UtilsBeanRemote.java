package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Remote;

import cc.sourcebox.entities.Operation;

@Remote
public interface UtilsBeanRemote {

	public String getRandomString(int size);
	//public String digest(String original, List<Operation> operations);
}
