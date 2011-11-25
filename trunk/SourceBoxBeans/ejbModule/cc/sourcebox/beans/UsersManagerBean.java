package cc.sourcebox.beans;

import java.sql.Timestamp;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.entities.Box;
import cc.sourcebox.entities.Inbox;
import cc.sourcebox.entities.Revision;
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
	public void heartBeat(int userID) {
		User user = em.find(User.class, userID);
	    if (user != null) {
	      user.setLastActivity(new Timestamp(System.currentTimeMillis()));
	    }
	}




	@Override
	public void joinBox(int userID, Box box) {
		User user = em.find(User.class, userID);
		/*Query inboxQuery = em.createQuery("SELECT u from Inbox i join i.user u join i.box b where u.iduser = :userid and b.idboxes = :idbox");
		inboxQuery.setParameter("userid", userID);
		inboxQuery.setParameter("idbox", box.getIdboxes());
		inboxQuery.setMaxResults(1);
		
		System.out.println("PREFETCH");

		Object result = inboxQuery.getSingleResult();
		System.out.println("PostFETCH");
		System.out.println("res "+result);
		
		User user = (User)inboxQuery.getSingleResult();
		System.out.println(user);*/
		
	    if (user != null) {
	    	Inbox joinDiscussion = new Inbox();
	    	joinDiscussion.setCursorColumn(0);
	    	joinDiscussion.setCursorLine(0);
	    	joinDiscussion.setSince(new Timestamp(System.currentTimeMillis()));
	    	joinDiscussion.setUser(user);
	    	joinDiscussion.setBox(box);
	    	try {
	    		em.persist(joinDiscussion);
	    	}catch (Exception e) {}

	    }
	}


	@Override
	public void setCursorPos(String boxAlias, int userID, int line, int ch) {
		//User user = em.find(User.class, userID);
		
		Query inboxQuery = em.createQuery("SELECT i from Inbox i join i.box b join i.user u where u.iduser=:iduser and b.alias=:alias");
		

		inboxQuery.setMaxResults(1);
		inboxQuery.setParameter("iduser", userID);
		inboxQuery.setParameter("alias", boxAlias);

			
		Inbox inBox = (Inbox)inboxQuery.getSingleResult();
		
		inBox.setCursorLine(line);
		inBox.setCursorColumn(ch);


		
	}

}
