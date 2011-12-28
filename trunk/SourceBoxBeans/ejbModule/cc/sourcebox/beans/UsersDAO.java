package cc.sourcebox.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.entities.Box;
import cc.sourcebox.entities.Inbox;
import cc.sourcebox.entities.User;

/**
 * Session Bean implementation class UsersManagerBean
 */
@Stateless
@LocalBean
public class UsersDAO implements UsersDAORemote, UsersDAOLocal {

	@PersistenceContext(name="SourceBoxEntities")
	EntityManager em;
	
	@EJB
	private UtilsBeanLocal utils;
/*	@EJB
	private BoxInfoBeanRemote boxBean;*/

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

		Query inboxQuery = em.createQuery("SELECT u.iduser from Inbox i join i.user u join i.box b where u.iduser = :userid and b.idboxes = :idbox");
		inboxQuery.setParameter("userid", userID);
		inboxQuery.setParameter("idbox", box.getIdboxes());
		if (inboxQuery.getResultList().size()>0) return;
		
		
		User user = em.find(User.class, userID);
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
	public void setCursorPos(String boxAlias, int userID, int line, int ch) throws BoxNotFoundException {


		Query inboxQuery = em.createQuery("SELECT i from Inbox i join i.box b join i.user u where u.iduser=:iduser and b.alias=:alias");

		inboxQuery.setParameter("iduser", userID);
		inboxQuery.setParameter("alias", boxAlias);

		inboxQuery.setMaxResults(1);

			
		Inbox inBox = (Inbox)inboxQuery.getSingleResult();
		
		inBox.setCursorLine(line);
		inBox.setCursorColumn(ch);


		//boxBean.notifyUpdate(boxAlias);
		
	}


	@Override
	public List<UserInfo> getUsers(String alias) {
		Date limit = utils.getUsersTimeDeadline();

		Query inboxQuery = em.createQuery("SELECT i from Inbox i join i.box b join i.user u where b.alias=:alias and u.lastActivity > :limit");
		inboxQuery.setParameter("alias", alias);
		inboxQuery.setParameter("limit", limit);
		
		List<UserInfo> cursors = new ArrayList<UserInfo>();
		
		List<Object> iList = inboxQuery.getResultList();
		for (int i = 0; i < iList.size(); i++) {
			Inbox inBox = (Inbox)iList.get(i);
			UserInfo cp = new UserInfo(inBox.getUser().getIduser(), 
										inBox.getUser().getName(),
										inBox.getCursorLine(),
										inBox.getCursorColumn()
										);
			cursors.add(cp);
		}
		
		return cursors;
	}


	@Override
	public User get(int userid) {
		return em.find(User.class, userid);
	}

}
