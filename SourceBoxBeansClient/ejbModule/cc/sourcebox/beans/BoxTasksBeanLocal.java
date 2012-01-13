package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Local;

import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.dto.RevisionDTO;

@Local
public interface BoxTasksBeanLocal {

	/**********
	 *  Interface to DAO layer called only by local beans
	 *  Store the chat message
	 * @param userid
	 * @param alias
	 * @param message
	 */
	public void sendChat(int userid, String alias, String message);
	
	/**********
	 * Interface to DAO layer called only by local beans
	 * Store the edit
	 * @param uid
	 * @param alias
	 * @param inserts
	 * @return
	 */
	public List<InsertObject> edit(int uid, String alias, List<InsertObject> inserts);
	
	
	/*********
	 * Digests the pending operations and create a new revision
	 * @param alias
	 */
	public void save(String alias);

	/********
	 * Retrieve the revision from the dao
	 * @param alias
	 * @param revision
	 * @return
	 */
	public RevisionDTO getRevision (String alias, Integer revision);
	
	/*******
	 * Prevents the box deletion
	 * @param alias
	 */
	public void boxHeartBeat(String alias);

}
