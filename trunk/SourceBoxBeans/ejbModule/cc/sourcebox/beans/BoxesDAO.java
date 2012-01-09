package cc.sourcebox.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.beans.query.Queries;
import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.entities.Box;
import cc.sourcebox.entities.Message;
import cc.sourcebox.entities.Operation;
import cc.sourcebox.entities.Revision;

/**
 * Session Bean implementation class BoxesDAO
 */
@Stateless
public class BoxesDAO implements BoxesDAOLocal {

	@PersistenceContext(name="SourceBoxEntities")
	EntityManager em;
	
	@EJB
	UrlHelperLocal urlHelper;
	
	@EJB
	UtilsBeanLocal utils;
	
    public BoxesDAO() {}
    
	@Override
	public String make(String language, String body, String password, Boolean readonly) {

		
		Box box = new Box();
		
		/********
		 * Temporary alias because the field does not allow NULL value
		 * The alias is computed starting from the id generated from the database
		 */
		box.setAlias(""+System.currentTimeMillis());
		
		/*******
		 * Box content
		 */
		box.setLanguage(language);
		box.setReadonly((readonly)?1:0);
		box.setPassword((password==null)?"":password);
		
		/******
		 * First revision
		 */
		Revision rev = new Revision();
		rev.setSource(body);
		rev.setBox(box);
		
		Operation dummyOperation = new Operation();
		dummyOperation.setBox(box);
		dummyOperation.setFromChar(0);
		dummyOperation.setFromLine(0);
		dummyOperation.setToChar(0);
		dummyOperation.setToLine(0);
		dummyOperation.setString("");
		rev.setOperation(dummyOperation);
		

		List<Revision> rl = new ArrayList<Revision>(1);
		rl.add(rev);
		
		box.setRevisions(rl);
		
		box.setDestroykey(utils.getRandomString(8));
		
		/*******
		 * Save to db
		 */
		em.persist(box);
		

		/********
		 * Now the db has generated a box id 
		 */
		box.setAlias(urlHelper.idToAlias(box.getIdboxes()));
		
		
		return box.getAlias()+":"+box.getDestroykey();
	}
	
	@Override
	public Box get(String alias) {
		Query query = em.createQuery(Queries.get("BOXES_SIMPLEGET"));
		query.setParameter("alias", alias);
		return (Box)query.getSingleResult();
	}
/*
	@Override
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
		
	}
	*/
	@Override
	public Revision get(int userId, String alias, String password) throws BoxNotFoundException {

		Query boxQuery = em.createQuery(Queries.get("BOXES_GET"));
		
		/************
		 * I need only 1 revision. The revisions are ordered by rev number (desc)
		 */
		boxQuery.setMaxResults(1);
		boxQuery.setParameter("alias", alias);
		boxQuery.setParameter("iduser", userId);
		boxQuery.setParameter("pwd", (password==null)?"":password);
		try {	
			
			//Revision lastRev = (Revision)boxQuery.getSingleResult();
			Object[] revAndBox = (Object[])boxQuery.getSingleResult();
			
			Box b = (Box)revAndBox[1];
			
			b.setLastvisit(new Timestamp(System.currentTimeMillis()));
			
			return (Revision)revAndBox[0];
		}
		catch (Exception e) {
			throw new BoxNotFoundException();
		}
		
		
	}

	@Override
	public List<Message> getChatHistory(String alias, int n) {
		
		Query query = em.createQuery(Queries.get("BOXES_CHAT"));
		query.setParameter("alias", alias);
		query.setMaxResults(n);
		
		List<Message> chatHis = query.getResultList();
		
		return chatHis;
	}

	@Override
	public void sendChat(Message msg) {
		em.persist(msg);
	}

	@Override
	public List<InsertObject> edit(String alias, List<InsertObject> inserts) {

		//User user = usersDao.get(uid);
		Box box = get(alias);
		if (box.getReadonly()>0) throw new RuntimeException("Box is read-only");
		for (int i = 0; i < inserts.size()	; i++) {
			InsertObject tmp = inserts.get(i);
			Operation op = new Operation();
			//op.setUser(user);
			op.setBox(box);
			op.setFromLine(tmp.getFromLine());
			op.setFromChar(tmp.getFromChar());
			op.setToLine(tmp.getToLine());
			op.setToChar(tmp.getToChar());
			op.setString(tmp.getText());
			em.persist(op);
			inserts.get(i).setSq(op.getIdoperation());
		}
		
		return inserts;
		
	}

	@Override
	public List<Operation> getOperations(String alias, int from) {
		
		Query newOperationsQuery = em.createQuery(Queries.get("BOXES_OPERATIONS"));
		newOperationsQuery.setParameter("lastid", from);
		newOperationsQuery.setParameter("alias", alias);
		return newOperationsQuery.getResultList();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(String alias) {

		Query lastRevisionQuery = em.createQuery(Queries.get("BOXES_SAVE"));
		lastRevisionQuery.setParameter("alias", alias);
		
		Revision lastRev = (Revision)lastRevisionQuery.getSingleResult();
		
		int lastDigestId = lastRev.getOperation().getIdoperation();
		String revisionText = lastRev.getSource();

		List<Operation> operations = getOperations(alias, lastDigestId);
		
		if (operations.size()<1) return;

		Revision rev = new Revision();
		rev.setRev(lastRev.getRev()+1);
		
		rev.setOperation(operations.get(operations.size()-1));
		
		rev.setSource(utils.digest(revisionText, operations));
		rev.setBox(lastRev.getBox());
		
		em.persist(rev);
		
		removeOperations(alias, rev.getOperation().getIdoperation());

	}
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	private void removeOperations(String alias, int upTo) {
		Query deleteQuery = em.createQuery(Queries.get("BOXES_REMOPERATIONS"));
		deleteQuery.setParameter("alias", alias);
		deleteQuery.setParameter("id", upTo);
		deleteQuery.executeUpdate();

	}

	@Override
	public void destroy(String alias, String key) {
		
		Query deleteQuery = em.createQuery(Queries.get("BOXES_DESTROY"));
		deleteQuery.setParameter("alias", alias);
		deleteQuery.setParameter("key", key);
		deleteQuery.executeUpdate();
		System.out.println("DESTROY: " + alias);
	}

	@Override
	public Revision getRevision(String alias, Integer revision) {
		
		Query revisionQuery = em.createQuery("select r from Revision r where r.box.alias=:alias and r.rev=:id");
		revisionQuery.setParameter("alias", alias);
		
		if (revision==null)
			revision = getMaxRevision(alias);
		revisionQuery.setParameter("id", revision);
		
		
		revisionQuery.setMaxResults(1);
		
		List<Revision> rev = revisionQuery.getResultList();
		if (rev.size()<1) throw new RuntimeException("Revision not available");
		return rev.get(0);
	}


	private int getMaxRevision(String alias) {
		Query maxRevisionQuery = em.createQuery("select max(r.rev) from Revision r where r.box.alias=:alias");
		maxRevisionQuery.setParameter("alias", alias);
		maxRevisionQuery.setMaxResults(1);
		return (Integer)maxRevisionQuery.getSingleResult();
	}
	

}
