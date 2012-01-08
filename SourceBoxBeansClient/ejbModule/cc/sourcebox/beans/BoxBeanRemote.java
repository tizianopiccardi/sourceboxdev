package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Remove;

import cc.sourcebox.beans.exceptions.ChatErrorException;
import cc.sourcebox.dto.EventsDTO;
import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.dto.UserInfo;



@Remote
public interface BoxBeanRemote {

	/************
	 * Initial configuration (and jms topic subscription)
	 * @param user
	 * @param alias
	 */
	public void init(UserInfo user, String alias);
	
	/************
	 * Check for elements in the events buffer
	 * @return
	 */
	public boolean somethingNew();
	
	/************
	 * Retrive the events buffer
	 * @return
	 */
	public EventsDTO getEvents();
	
	/************
	 * Send a text message (chat)
	 * @param msg
	 * @throws ChatErrorException
	 */
	public void send(String msg) throws ChatErrorException;
	
	/************
	 * Update the cursor position 
	 * @param l
	 * @param c
	 */
	public void setCursor(int l, int c);
	
	
	/************
	 * Send a text change
	 * @param inserts
	 */
	public void edit(List<InsertObject> inserts);
	
	/************
	 * This method is called to maintain the user alive in the database.
	 * If for some reason the remove() is not called but this method has not been 
	 * called for n minutes, the database is anyway cleaned
	 */	
	public void heartBeat();
	
	
	/************
	 * Save the current state of the text
	 */
	public void save();
	
	/************
	 * Destroy the bean
	 */
	@Remove
	public void remove();
	
	//public void ping();

}
