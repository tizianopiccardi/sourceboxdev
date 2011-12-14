package cc.sourcebox.beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.jboss.ejb3.annotation.CacheConfig;

import cc.sourcebox.dto.ChatMessage;
import cc.sourcebox.dto.EventsDTO;

/**
 * Session Bean implementation class Events
 */
@Stateful
@LocalBean
//@CacheConfig(/* removalTimeoutSeconds=10, */maxSize = 0, idleTimeoutSeconds = 0)
// !!NON PASSIVARE
public class Events implements EventsRemote, EventsLocal {

	static final String url = "tcp://localhost:61616";
	static ConnectionFactory factory = new ActiveMQConnectionFactory(url);

	EventsDTO events = new EventsDTO();


	
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
		return events.getMsg().size()>0 || events.getOp().size()>0;
	}

	private class EventsListener implements MessageListener {
		EventsDTO ec;

		public EventsListener(EventsDTO ec) {
			this.ec = ec;
		}

		@Override
		public void onMessage(Message msg) {
			try {
				
				//System.out.println("Events.EventsListener.onMessage()");
				String payload = ((TextMessage) msg).getText();
				//if (payload.startsWith(""))
				//msg.getJMSExpiration();
				ChatMessage c = new ChatMessage("xxx", payload);
				events.add(c);
				//System.out.println(events);
				//System.out.println(events.getMsg());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public EventsDTO get() {
		EventsDTO response = null;
		synchronized (events) {

			response = events;
			
			//events = new EventsDTO();
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
