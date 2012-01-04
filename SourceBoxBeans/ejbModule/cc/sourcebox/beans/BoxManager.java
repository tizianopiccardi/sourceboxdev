package cc.sourcebox.beans;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.jms.JMSException;

import org.jboss.ejb3.annotation.CacheConfig;

import cc.sourcebox.beans.actions.JmsHelper;
import cc.sourcebox.beans.exceptions.ChatErrorException;
import cc.sourcebox.dto.ChatMessage;
import cc.sourcebox.dto.CursorsDTO;
import cc.sourcebox.dto.EventsDTO;
import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.dto.UserInfo;

/**
 * Session Bean implementation class Events
 */
@Stateful
@LocalBean
@CacheConfig(removalTimeoutSeconds=180)

public class BoxManager implements BoxManagerRemote, BoxManagerLocal {

	
	

	EventsDTO events = new EventsDTO();
	JmsHelper jmsTopic = null;
	UserInfo user;
	String alias;

	
	@EJB
	UsersDAORemote usersDao;
	
	@EJB
	BoxInfoBeanLocal boxHelper;

	@Override
	public void init(UserInfo user, String alias) {
		
		if (jmsTopic==null)
		try {
			
			jmsTopic = new JmsHelper(alias, events);

			this.user = user;
			this.alias = alias;
			
			jmsTopic.send(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//private int checksCounter = 0;
	@Override
	public boolean somethingNew() {
	//	if (++checksCounter%10==0) usersDao.heartBeat(user.getUserid());
		return events.hasEvents();
	}



	@Override
	public EventsDTO getEvents() {
		EventsDTO response = null;
		synchronized (events) {

			try {
				response = events.extract();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		return response;
	}

	@Override
	public void send(String msg) throws ChatErrorException {
		try {
			boxHelper.sendChat(user.getUserid(), alias, msg);
			jmsTopic.send(new ChatMessage(user.getUserid(), msg));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ChatErrorException();
		}

	}
	
	@Override
	public void remove() {
		System.out.println("User timeout: " + user.getUsername() + " (BOX "+alias+")");
		jmsTopic.closeAll();
	}
	

	@Override
	public void setCursor(int l, int c) {
		user.setCh(c); user.setLine(l);
		try {
			usersDao.setCursorPos(this.alias, user.getUserid(), l, c);
			jmsTopic.send(new CursorsDTO(user.getUserid(), l, c));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void edit(List<InsertObject> inserts) {

		/********
		 * Add the operation to the database and get back the list with the generated sequence (from db)
		 */
		inserts = boxHelper.edit(user.getUserid(), alias, inserts);
		for (InsertObject i : inserts) 
			try {
				jmsTopic.send(i);
			} catch (JMSException e) {
				e.printStackTrace();
			}

	}

	@Override
	public void heartBeat() {
		usersDao.heartBeat(user.getUserid());
	}

	@Override
	public void save() {
		//Thread.sleep(1000);
		
	}

}
