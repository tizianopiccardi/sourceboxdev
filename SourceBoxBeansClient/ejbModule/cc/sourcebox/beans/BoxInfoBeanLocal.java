package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Local;

import cc.sourcebox.dto.InsertObject;

@Local
public interface BoxInfoBeanLocal {

	/**********
	 *  Interface to DAO layer called only by local beans
	 * @param userid
	 * @param alias
	 * @param message
	 */
	public void sendChat(int userid, String alias, String message);
	
	/**********
	 * Interface to DAO layer called only by local beans
	 * @param uid
	 * @param alias
	 * @param inserts
	 * @return
	 */
	public List<InsertObject> edit(int uid, String alias, List<InsertObject> inserts);
	
	
	public void save(String alias);
}
