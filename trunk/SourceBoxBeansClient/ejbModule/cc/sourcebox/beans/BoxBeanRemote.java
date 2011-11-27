package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Remote;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.entities.Revision;
import cc.sourcebox.entities.User;

@Remote
public interface BoxBeanRemote {

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
	 * Save the editor content creating a new revision
	 * @param alias
	 * @param body
	 * @throws BoxNotFoundException
	 */
	public void save(String alias, String body) throws BoxNotFoundException;
	
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
	
	public void edit(String alias, int userID, List<InsertObject> inserts) throws BoxNotFoundException;
	
}