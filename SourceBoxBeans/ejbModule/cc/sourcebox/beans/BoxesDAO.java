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

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
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
	
	//@EJB
	//UsersDAORemote usersDao;
	
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

		List<Revision> rl = new ArrayList<Revision>(1);
		rl.add(rev);
		
		box.setRevisions(rl);
		
		
		/*******
		 * Save to db
		 */
		em.persist(box);
		
		
		/********
		 * Now the db has generated a box id 
		 */
		box.setAlias(urlHelper.idToAlias(box.getIdboxes()));
		
		
		return box.getAlias();
	}
	
	@Override
	public Box get(String alias) {
		Query query = em.createQuery("SELECT b from Box b where b.alias=:alias");
		query.setParameter("alias", alias);
		return (Box)query.getSingleResult();
	}

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
	
	@Override
	public Revision get(int userId, String alias, String password) throws BoxNotFoundException {
		String query = "SELECT r,b from Revision r join r.box b where (b.alias=:alias and b.password=:pwd)" +
				"OR b.idboxes = (select b.idboxes from Inbox i join i.user u join i.box b where u.iduser = :iduser and b.alias=:alias)" +
				" order by r.rev desc";
		Query boxQuery = em.createQuery(query);
		
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
		
		Query query = em.createQuery("SELECT m from Message m join m.box b where b.alias=:alias order by m.idmessages desc");
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
	public List<InsertObject> edit(int uid, String alias, List<InsertObject> inserts) {

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
	public List<Operation> getOperations(int from) {
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(String alias) {
		Query query = em.createQuery("SELECT r.operation from Revision r join r.box b where b.alias=:alias order by r.rev desc");
		query.setMaxResults(1);
		/*Query query = em.createQuery("SELECT o from Operation o join o.box b where b.alias = :alias");
		query.setParameter("alias", alias);*/

	}


	
	


}
