package cc.sourcebox.beans;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cc.sourcebox.beans.exceptions.BoxNotFoundException;
import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.entities.Box;
import cc.sourcebox.entities.Revision;

/**
 * Session Bean implementation class BoxBean
 */
@Stateless
@LocalBean
public class BoxBean implements BoxBeanRemote, BoxBeanLocal {

	@PersistenceContext(name="SourceBoxEntities")
	EntityManager em;
	
	@EJB
	UrlHelperLocal urlHelper;
	
	@EJB
	UsersManagerBeanLocal usersMgr;
	
	
    /**
     * Default constructor. 
     */
    public BoxBean() {
        // TODO Auto-generated constructor stub
    }

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
	public void save(String alias, String body) throws BoxNotFoundException {

		Query query = em.createQuery("SELECT max(r.rev), b from Revision r inner join r.box b where b.alias=:alias");
		query.setParameter("alias", alias);
		
		if (query.getResultList().size()<1) throw new BoxNotFoundException();
		
			
		Object[] data = (Object[]) query.getResultList().get(0);
		
		int revNumber = (Integer)data[0]+1;
		
		Revision rev = new Revision();
		rev.setRev(revNumber);
		rev.setSource(body);
		rev.setBox((Box)data[1]);


		em.persist(rev);

	}

	@Override
	public Revision get(int userId, String alias, String password) throws BoxNotFoundException {
		String query = "SELECT r from Revision r join r.box b where (b.alias=:alias and b.password=:pwd)" +
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
			
			Revision lastRev = (Revision)boxQuery.getSingleResult();
			usersMgr.joinBox(userId, lastRev.getBox());
			return lastRev;
		}
		catch (Exception e) {
			throw new BoxNotFoundException();
		}
		
		
	}

	@Override
	public Boolean isPrivate(String alias) throws BoxNotFoundException {
		Query boxQuery = em.createQuery("SELECT b.password from Box b where b.alias=:alias" );

		boxQuery.setParameter("alias", alias);
		
		try {
			String pwd = boxQuery.getSingleResult().toString();
			return (!pwd.equals(""));
		} catch (Exception e) {
			throw new BoxNotFoundException();
		}
		
	}

	@Override
	public void edit(String alias, int userID, List<InsertObject> inserts)
			throws BoxNotFoundException {
		System.out.println(inserts);
		
	}


	
	
	

}
