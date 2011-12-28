package cc.sourcebox.beans;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

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
	//BoxesDAOLocal boxDao;
	
	@Override
	public void init(UserInfo user, String alias) {
		
		if (jmsTopic==null)
		try {
			
			System.out.println("INIT EVENT BEAN ON: " + alias);
			
			
			jmsTopic = new JmsHelper(alias, events);

			this.user = user;
			this.alias = alias;
			
			jmsTopic.send(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean somethingNew() {
		//System.out.println(events.hasEvent);
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
			
			//events.clean();
			
			//events.hasEvent = false;
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
	
	@PreDestroy
	public void remove() {
		jmsTopic.closeAll();
	}

	@Override
	public void setCursor(int l, int c) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
		usersDao.heartBeat(user.getUserid());
	}

}
