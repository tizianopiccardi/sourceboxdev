package cc.sourcebox.beans;

import java.util.HashSet;
import java.util.UUID;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class UserEnv
 */
@Stateful
@LocalBean
public class UserEnv implements UserEnvRemote, UserEnvLocal {

	private String userid;
    private String username = "";
    private HashSet<String> joinedBoxes = new HashSet<String>();
   // private int sequence = 0;
	
    /**
     * Default constructor. 
     */
    public UserEnv() {
        userid = UUID.randomUUID().toString();
    }

    
    
	@Override
	public void openBox(String alias) {
		joinedBoxes.add(alias);
	}

	@Override
	public void setName(String name) {
		System.out.println(name);
		username = name;
	}

	@Override
	public String getName() {
		return username;
	}



	@Override
	public boolean isInBox(String alias) {
		return joinedBoxes.contains(alias);
	}

/*

	@Override
	public int getSequence() {
		return sequence++;
	}*/



}
