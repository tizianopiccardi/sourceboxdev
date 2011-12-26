package cc.sourcebox.beans;


import java.sql.Timestamp;
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
import cc.sourcebox.dto.UserInfo;
import cc.sourcebox.entities.Box;
import cc.sourcebox.entities.Revision;

/**
 * Session Bean implementation class BoxBean
 */
@Stateless
@LocalBean
public class BoxInfoBean implements BoxInfoBeanRemote, BoxInfoBeanLocal {

	@PersistenceContext(name="SourceBoxEntities")
	EntityManager em;

	
	@EJB
	UsersManagerBeanRemote usersMgr;
	
	@EJB
	BoxesDAOLocal dao;
	
    /**
     * Default constructor. 
     */
    public BoxInfoBean() {
        // TODO Auto-generated constructor stub
    }


	/*@Override
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

	}*/


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

	/*@Override
	public void edit(String alias, int userID, List<InsertObject> inserts)
			throws BoxNotFoundException {
		System.out.println(inserts);
		
	}*/
/*
	@Override
	public void notifyUpdate(String alias) throws BoxNotFoundException {
		Query query = em.createQuery("SELECT b from Box b where b.alias=:alias");
		query.setParameter("alias", alias);
		List boxList = query.getResultList();
		listCheck(boxList);
		Box box = (Box)boxList.get(0);
		
		box.setSequence(box.getSequence()+1);
		
		//box.setLastevent(new Timestamp(System.currentTimeMillis()));
	}
/*
	@Override
	public long lastEvent(String alias) throws BoxNotFoundException {
		Query query = em.createQuery("SELECT b.lastevent from Box b where b.alias=:alias");
		query.setParameter("alias", alias);
		List boxList = query.getResultList();
		
		listCheck(boxList);
		
		return ((Timestamp)boxList.get(0)).getTime();
	}*/

	/*@Override
	public void notifyUpdate(Box box) throws BoxNotFoundException  {
		System.out.println("BoxBean.notifyUpdate()");
		box.setLastevent(new Timestamp(System.currentTimeMillis()));
		//em.persist(box);
	}*/


	/*private void listCheck(List list) throws BoxNotFoundException {
		if (list.size()<1) throw new BoxNotFoundException();
	}*/

	@Override
	public Box get(String alias) {
		Query query = em.createQuery("SELECT b from Box b where b.alias=:alias");
		query.setParameter("alias", alias);
		return (Box)query.getSingleResult();
	}
/*
	@Override
	public int getSequence(String alias) throws BoxNotFoundException {
		Query query = em.createQuery("SELECT b.sequence from Box b where b.alias=:alias");
		query.setParameter("alias", alias);
		List boxList = query.getResultList();
		
		listCheck(boxList);
		
		return ((Integer)boxList.get(0));
	}*/


	@Override
	public String make(String language, String body, String password,Boolean readonly) {
		return dao.make(language, body, password, readonly);
	}


	@Override
	public Revision get(int userId, String alias, String password)
			throws BoxNotFoundException {
		Revision rev = dao.get(userId, alias, password);
		usersMgr.joinBox(userId,rev.getBox());
		return rev;
		
	}


	@Override
	public List<UserInfo> getUsers(String alias) {
		// TODO Auto-generated method stub
		return usersMgr.getUsers(alias);
	}
	
	/*

	@Override
	public void notifyUpdate(String alias) throws BoxNotFoundException {
		// TODO Auto-generated method stub
		
	}
	*/



}
