package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Remote;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.dto.ChatMessage;
import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.entities.Revision;

@Remote
public interface BoxTasksBeanRemote {

	/************
	 * Create a new BOX. If the password is null or "" the box is public
	 * @param language
	 * @param body
	 * @param password
	 * @param readonly
	 * @return
	 */
	public String make(String language, String body, String password, Boolean readonly);
	
	
	
	/************
	 * Retrieve the last revision and set the user as logged
	 * @param userId
	 * @param alias
	 * @param password
	 * @return
	 * @throws BoxNotFoundException
	 */
	public Revision get(int userId, String alias, String password) throws BoxNotFoundException;

	
	/***********
	 * Check if the password is not null
	 * @param alias
	 * @return
	 * @throws BoxNotFoundException
	 */
	public Boolean isPrivate(String alias) throws BoxNotFoundException;
	
	//public void edit(String alias, int userID, List<InsertObject> inserts) throws BoxNotFoundException;
	
	//public void notifyUpdate(String alias) throws BoxNotFoundException ;

	//public long lastEvent(String alias) throws BoxNotFoundException ;
	
	//public int getSequence(String alias) throws BoxNotFoundException ;
	
	
	public List<InsertObject> getOperations(String alias, int from) ;
	
	public List<UserInfo> getUsers(String alias);
	
	public List<ChatMessage> getChatHistory(String alias);
	
	public int userJoin(String nick);
	
	public void destroy (String alias, String key);
	
	
	
	
}
