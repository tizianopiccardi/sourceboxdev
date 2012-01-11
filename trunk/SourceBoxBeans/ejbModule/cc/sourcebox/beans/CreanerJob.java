package cc.sourcebox.beans;

import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.ResourceAdapter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import cc.sourcebox.beans.actions.JmsHelper;
import cc.sourcebox.beans.query.Queries;
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.entities.User;

/**
 * Message-Driven Bean implementation class for: CreanerJob
 * 
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "cronTrigger", propertyValue = "1 * * ? * *") })
@ResourceAdapter("quartz-ra.rar")
public class CreanerJob implements Job {

	@PersistenceContext(name = "SourceBoxEntities")
	EntityManager em;
	@EJB
	private UtilsBeanLocal utils;

	/**
	 * @see Job#execute(JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) {
		cleanUser();
		cleanBoxes();
	}

	private void cleanBoxes() {

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private void cleanUser() {
		Query userQuery = em.createQuery(Queries.get("CLEANER_GETUSER"));
		userQuery.setParameter("limit", utils.getUsersTimeDeadline());

		List users = userQuery.getResultList();

		try {
			Connection connection = JmsHelper.factory.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			MessageProducer p = session.createProducer(null);

			String last = "";
			Topic dest = null;
			for (int i = 0; i < users.size(); i++) {
				Object[] result = (Object[]) users.get(i);
				User u = (User) result[0];
				String alias = result[1].toString();

				if (!alias.equals(last)) {
					dest = session.createTopic(alias);
					last = alias;
				}

				UserInfo usr = new UserInfo(u.getIduser(), u.getName(), 0, 0);
				usr.setAdd(false);

				p.send(dest, session.createObjectMessage(usr));

			}

			connection.close();

			Query deleteQuery = em.createQuery(Queries.get("CLEANER_DELUSER"));
			deleteQuery.setParameter("limit", utils.getUsersTimeDeadline());
			deleteQuery.executeUpdate();

		} catch (JMSException e) {
			e.printStackTrace();
			throw new RuntimeException("User clean failed");
		}

	}

}
