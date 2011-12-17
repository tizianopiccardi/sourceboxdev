package cc.sourcebox.beans;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.CacheConfig;

import cc.sourcebox.beans.actions.JmsHelper;
import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.beans.exceptions.ChatErrorException;
import cc.sourcebox.dto.ChatMessage;
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
	JmsHelper jmsTopic;
	UserInfo user;
	String alias;

	@EJB
	ChatBeanRemote chat;
	
	@EJB/*(mappedName = "SourceBoxLogicEAR/UsersManagerBean/remote")*/
	UsersManagerBeanRemote usersBean;
	
	@Override
	public void init(UserInfo user, String alias) {

		try {
			
			System.out.println("INIT EVENT BEAN ON: " + alias);
			
			jmsTopic = new JmsHelper(alias, events);

			this.user = user;
			this.alias = alias;
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
			//chat.send(msg.getUserid(), msg.getUser(), msg.getText());
			
			
			jmsTopic.send(new ChatMessage(user, msg));
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
			usersBean.setCursorPos(this.alias, user.getUserid(), l, c);
			jmsTopic.send(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void edit(List<InsertObject> inserts) {
		// TODO Auto-generated method stub
		
		usersBean.heartBeat(user.getUserid());
	}

}
