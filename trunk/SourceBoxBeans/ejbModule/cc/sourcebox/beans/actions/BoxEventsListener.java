package cc.sourcebox.beans.actions;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import cc.sourcebox.dto.Action;
import cc.sourcebox.dto.ChatMessage;
import cc.sourcebox.dto.CursorsDTO;
import cc.sourcebox.dto.EventsDTO;
import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.dto.UserInfo;

public class BoxEventsListener implements MessageListener{
	EventsDTO ec;

	public BoxEventsListener(EventsDTO ec) {
		this.ec = ec;
	}

	@Override
	public void onMessage(Message msg) {
		try {
			//System.out.println("BoxEventsListener.onMessage()");
			//ec.hasEvent = true;
			//if (1==1)return;
			ObjectMessage message = (ObjectMessage)msg;

			synchronized (ec) {

				if (message.getObject() instanceof ChatMessage) {
					ec.add((ChatMessage)message.getObject());
				} else
				if (message.getObject() instanceof InsertObject) {
					ec.add((InsertObject)message.getObject());
				} else
				if (message.getObject() instanceof CursorsDTO) {
					ec.add((CursorsDTO)message.getObject());
				}
				if (message.getObject() instanceof UserInfo) {
					ec.add((UserInfo)message.getObject());
				}
				if (message.getObject() instanceof Action) {
					ec.add((Action)message.getObject());
				}

				//ec.hasEvent = true;
				//System.out.println(ec.hasEvent);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
