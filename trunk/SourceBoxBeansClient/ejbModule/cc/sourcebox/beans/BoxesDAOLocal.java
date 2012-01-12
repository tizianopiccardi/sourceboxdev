package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Local;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.entities.Box;
import cc.sourcebox.entities.Message;
import cc.sourcebox.entities.Operation;
import cc.sourcebox.entities.Revision;

@Local
public interface BoxesDAOLocal {
	
	/*********
	 * Create the box
	 * @param language
	 * @param body
	 * @param password
	 * @param readonly
	 * @return
	 */
	public String make(String language, String body, String password, Boolean readonly);

	/*********
	 * Retrieve the box
	 * @param userId
	 * @param alias
	 * @param password
	 * @return
	 * @throws BoxNotFoundException
	 */
	public Revision get(int userId, String alias, String password) throws BoxNotFoundException;
	
	/*********
	 * Retrieve the last n messages
	 * @param alias
	 * @param n
	 * @return
	 */
	public List<Message> getChatHistory(String alias, int n);
	
	/**********
	 * Store the message
	 * @param msg
	 */
	public void sendChat(Message msg);
	
	/*********
	 * Return the Box
	 * @param alias
	 * @return
	 */
	public Box get(String alias);
	
	/********
	 * Store the edit and return the same list with the id field filled (by the db)
	 * @param alias
	 * @param inserts
	 * @return
	 */
	public List<InsertObject> edit(String alias, List<InsertObject> inserts);
	
	/*********
	 * Return the list of the opertation starting from n
	 * @param alias
	 * @param from
	 * @return
	 */
	public List<Operation> getOperations(String alias, int from);
	
	/********
	 * Store a new revision
	 * @param alias
	 */
	public void save(String alias);
	
	/********
	 * Delete the box record
	 * @param alias
	 * @param key
	 */
	public void destroy (String alias, String key);
	
	
	/*********
	 * Get a specific revision
	 * @param alias
	 * @param revision
	 * @return
	 */
	public Revision getRevision (String alias, Integer revision);

	
	/***********
	 * Check if the password is not null
	 * @param alias
	 * @return
	 * @throws BoxNotFoundException
	 */
	public Boolean isPrivate(String alias) throws BoxNotFoundException;
}
