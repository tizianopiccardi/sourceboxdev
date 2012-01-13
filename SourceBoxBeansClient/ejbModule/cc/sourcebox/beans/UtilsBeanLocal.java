package cc.sourcebox.beans;


import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import cc.sourcebox.entities.Operation;

@Local
public interface UtilsBeanLocal {

	/********
	 * The users who have last activity before the returned date are expired
	 * @return
	 */
	public Date getUsersTimeDeadline();
	

	/********
	 * Add the passed seconds
	 * @param deltaSeconds
	 * @return
	 */
	public Date getDateAt(long deltaSeconds);
	
	/*********
	 * Digest the pending operations applying them to the original string
	 * @param original
	 * @param operations
	 * @return
	 */
	public String digest(String original, List<Operation> operations);
	
	/*********
	 * Return a random string of the given length
	 * @param size
	 * @return
	 */
	public String getRandomString(int size);
}
