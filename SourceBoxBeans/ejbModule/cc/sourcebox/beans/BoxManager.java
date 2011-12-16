package cc.sourcebox.beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import cc.sourcebox.dto.ChatMessage;
import cc.sourcebox.dto.EventsDTO;
import cc.sourcebox.dto.InsertObject;

/**
 * Session Bean implementation class Events
 */
@Stateful
@LocalBean
//@CacheConfig(/* removalTimeoutSeconds=10, */maxSize = 0, idleTimeoutSeconds = 0)
// !!NON PASSIVARE
public class BoxManager implements BoxManagerRemote, BoxManagerLocal {

	static final String url = "tcp://localhost:61616";
	static ConnectionFactory factory = new ActiveMQConnectionFactory(url);

	EventsDTO events = new EventsDTO();


	boolean hasEvent = false;
	
	//MessageProducer producer;
	MessageConsumer consumer;
	Session session = null;
	
	@Override
	public void init(String alias) {

		if (session != null)
			return;

		try {
			
			System.out.println("INIT EVENT BEAN ON: " + alias);
			Connection connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(alias);
			consumer = session.createConsumer(topic);

			consumer.setMessageListener(new EventsListener(events));

			connection.start();

			//producer = session.createProducer(topic);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean somethingNew() {
		return hasEvent;
	}

	private class EventsListener implements MessageListener {
		EventsDTO ec;

		public EventsListener(EventsDTO ec) {
			this.ec = ec;
		}

		@Override
		public void onMessage(Message msg) {
			try {
				
				ObjectMessage message = (ObjectMessage)msg;

				synchronized (events) {

					if (message.getObject() instanceof ChatMessage) {
						events.add((ChatMessage)message.getObject());
					} else
					if (message.getObject() instanceof InsertObject) {
						events.add((InsertObject)message.getObject());
					}
					hasEvent = true;
				}

			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public EventsDTO getEvents() {
		EventsDTO response = null;
		synchronized (events) {

			response = events;
			
			events = new EventsDTO();
			
			hasEvent = false;
		}
		
		
		return response;
	}
/*
	String s="";
	@Override
	public String getS() {
		s+=".";
		return s;
	}

	int v = 0;
	@Override
	public int getV() {
		// TODO Auto-generated method stub
		return ++v;
	}*/

}
