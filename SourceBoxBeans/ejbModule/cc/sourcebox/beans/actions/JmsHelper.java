package cc.sourcebox.beans.actions;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import cc.sourcebox.dto.EventsDTO;

public class JmsHelper {

	static final String url = "tcp://localhost:61616";
	public static ConnectionFactory factory = new ActiveMQConnectionFactory(url);
	

	MessageProducer producer;
	Session session;
	Connection connection;
	MessageConsumer consumer;
	
	public JmsHelper(String alias, EventsDTO buffer) throws JMSException {
		
		connection = factory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic(alias);
		consumer = session.createConsumer(topic);
		consumer.setMessageListener(new BoxEventsListener(buffer));
		producer = session.createProducer(topic);

		connection.start();

	}
	
	public <T extends Serializable> void send( T msg) throws JMSException{
		Message wrappedMsg = session.createObjectMessage(msg);
		producer.send(wrappedMsg);
	}

	
	public void closeAll(){
		try {
			producer.close();
			consumer.close();
			session.close();
			connection.close();
		} catch (JMSException e) {e.printStackTrace();}

		
	}
	
}
