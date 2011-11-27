package cc.sourcebox.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.ResourceAdapter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.entities.Inbox;

/**
 * Message-Driven Bean implementation class for: CreanerJob
 *
 */
@MessageDriven(activationConfig =
{@ActivationConfigProperty(propertyName = "cronTrigger", propertyValue = "0,30 * * ? * *")})
@ResourceAdapter("quartz-ra.rar")
public class CreanerJob implements Job {


	@PersistenceContext(name="SourceBoxEntities")
	EntityManager em;
	@EJB
	private UtilsBeanLocal utils;

	/**
     * @see Job#execute(JobExecutionContext)
     */
    public void execute(JobExecutionContext arg0) {
    	cleanUser();
    }
    
    private void cleanUser() {

		Query deleteQuery = em.createQuery("delete from User u where u.lastActivity < :limit");
		deleteQuery.setParameter("limit", utils.getUsersTimeDeadline());
		deleteQuery.executeUpdate();
		
    }

}