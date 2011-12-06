package cc.sourcebox.beans;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.entities.Message;

/**
 * Session Bean implementation class ChatBean
 */
@Stateless
@LocalBean
public class ChatBean implements ChatBeanRemote, ChatBeanLocal {

	@PersistenceContext(name="SourceBoxEntities")
	EntityManager em;
	
	@EJB
	BoxBeanLocal boxBean;
	
	@EJB
	UsersManagerBeanLocal usersBean;

	
    /**
     * Default constructor. 
     */
    public ChatBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void send(int userid, String alias, String message) throws BoxNotFoundException {
		
		Message msg = new Message();
		
		msg.setText(message);
		msg.setTime(new Timestamp(System.currentTimeMillis()));

		msg.setUser(usersBean.get(userid));
		msg.setBox(boxBean.get(alias));
		
		em.persist(msg);
		
		boxBean.notifyUpdate(alias);
		
	}
/*?????????????????
	@Override
	public List<Message> get(String alias, long last) throws BoxNotFoundException {

		Query query = em.createQuery("SELECT m from Message m join m.box b where b.alias=:alias and m.time > :time");
		query.setParameter("alias", alias);
		query.setParameter("time", new Timestamp(last));
		return query.getResultList();
		
	}*/

}
