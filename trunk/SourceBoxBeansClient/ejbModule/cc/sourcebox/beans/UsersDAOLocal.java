package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Local;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.entities.Box;
import cc.sourcebox.entities.User;

@Local
public interface UsersDAOLocal {
	
	/*******
	 * Store the user join (in the application)
	 * @param name
	 * @return
	 */
	public int join(String name);
	
	/*******
	 * Store the cursor status
	 * @param boxAlias
	 * @param userID
	 * @param line
	 * @param ch
	 * @throws BoxNotFoundException
	 */
	public void setCursorPos(String boxAlias, int userID, int line, int ch) throws BoxNotFoundException;
	
	/*******
	 * Update the field "last activity" in order to maintain the user alive
	 * @param userid
	 */
	public void heartBeat(int userid);
	
	/*******
	 * Return the users in the box
	 * @param alias
	 * @return
	 */
	public List<UserInfo> getUsers(String alias);
	
	/*******
	 * Add a user join
	 * @param userid
	 * @param box
	 */
	public void joinBox(int userid, Box box);
	
	/*******
	 * Get the user data
	 * @param userid
	 * @return
	 */
	public User get(int userid);
}
