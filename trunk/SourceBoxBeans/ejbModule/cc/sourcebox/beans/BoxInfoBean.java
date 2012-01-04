package cc.sourcebox.beans;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.dto.ChatMessage;
import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.entities.Message;
import cc.sourcebox.entities.Revision;

/**
 * Session Bean implementation class BoxBean
 */
@Stateless
@LocalBean
public class BoxInfoBean implements BoxInfoBeanRemote, BoxInfoBeanLocal {

	@PersistenceContext(name="SourceBoxEntities")
	EntityManager em;

	
	@EJB
	UsersDAORemote usersDAO;
	
	@EJB
	BoxesDAOLocal boxDAO;
	
    /**
     * Default constructor. 
     */
    public BoxInfoBean() {
        // TODO Auto-generated constructor stub
    }


	/*@Override
	public void save(String alias, String body) throws BoxNotFoundException {

		Query query = em.createQuery("SELECT max(r.rev), b from Revision r inner join r.box b where b.alias=:alias");
		query.setParameter("alias", alias);
		
		List boxList = query.getResultList();
		if (boxList.size()<1) throw new BoxNotFoundException();
		
			
		Object[] data = (Object[]) boxList.get(0);
		
		int revNumber = (Integer)data[0]+1;
		
		Revision rev = new Revision();
		rev.setRev(revNumber);
		rev.setSource(body);
		rev.setBox((Box)data[1]);


		em.persist(rev);

	}*/


	@Override
	public Boolean isPrivate(String alias) throws BoxNotFoundException {
		Query boxQuery = em.createQuery("SELECT b.password from Box b where b.alias=:alias" );

		boxQuery.setParameter("alias", alias);
		
		try {
			String pwd = boxQuery.getSingleResult().toString();
			return (!pwd.equals(""));
		} catch (Exception e) {
			throw new BoxNotFoundException();
		}
		
	}



	@Override
	public String make(String language, String body, String password,Boolean readonly) {
		return boxDAO.make(language, body, password, readonly);
	}


	@Override
	public Revision get(int userId, String alias, String password)
			throws BoxNotFoundException {
		Revision rev = boxDAO.get(userId, alias, password);
		usersDAO.joinBox(userId,rev.getBox());
		return rev;
		
	}


	@Override
	public List<UserInfo> getUsers(String alias) {
		// TODO Auto-generated method stub
		return usersDAO.getUsers(alias);
	}


	@Override
	public List<ChatMessage> getChatHistory(String alias) {
		List<Message> chatHis = boxDAO.getChatHistory(alias, 15);
		
		List<ChatMessage> wrappedMsg = new ArrayList<ChatMessage>();
		for (int i = chatHis.size()-1; i >= 0; i--) 
			if (chatHis.get(i).getUser()!=null) {
				ChatMessage c = new ChatMessage(chatHis.get(i).getUser().getIduser(), chatHis.get(i).getText());
				wrappedMsg.add(c);
			}
		
		return wrappedMsg;
	}


	@Override
	public void sendChat(int userid, String alias, String message) {

		Message msg = new Message();
		
		msg.setText(message);
		msg.setTime(new Timestamp(System.currentTimeMillis()));

		msg.setUser(usersDAO.get(userid));
		msg.setBox(boxDAO.get(alias));
		
		boxDAO.sendChat(msg);

	}


	@Override
	public int userJoin(String nick) {
		return usersDAO.join(nick);
	}


	@Override
	public List<InsertObject> edit(int uid, String alias, List<InsertObject> inserts) {
		return boxDAO.edit(alias, inserts);
	}


	@Override
	public List<InsertObject> getOperations(int from) {
		return null;
	}


	@Override
	public void save(String alias) {
		boxDAO.save(alias);
	}




}
