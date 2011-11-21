package cc.sourcebox.beans;

import java.sql.Timestamp;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cc.sourcebox.entities.Box;
import cc.sourcebox.entities.Inbox;
import cc.sourcebox.entities.User;

/**
 * Session Bean implementation class UsersManagerBean
 */
@Stateless
@LocalBean
public class UsersManagerBean implements UsersManagerBeanRemote, UsersManagerBeanLocal {

	@PersistenceContext(name="SourceBoxEntities")
	EntityManager em;
	

	@Override
	public int join(String name) {
		User user = new User();
		
		user.setName(name);
		user.setLastActivity(new Timestamp(System.currentTimeMillis()));
		
		em.persist(user);
		
		return user.getIduser();
		
	}


	@Override
	public void heartBeat(int userid) {
		User user = em.find(User.class, userid);
	    if (user != null) {
	      user.setLastActivity(new Timestamp(System.currentTimeMillis()));
	    }
	}




	@Override
	public void joinBox(int userid, Box box) {
		User user = em.find(User.class, userid);

	    if (user != null) {
	    	Inbox joinDiscussion = new Inbox();
	    	joinDiscussion.setCursorColumn(0);
	    	joinDiscussion.setCursorLine(0);
	    	joinDiscussion.setSince(new Timestamp(System.currentTimeMillis()));
	    	joinDiscussion.setUser(user);
	    	joinDiscussion.setBox(box);
	    	
	    	em.persist(joinDiscussion);

	    }
	}

}
