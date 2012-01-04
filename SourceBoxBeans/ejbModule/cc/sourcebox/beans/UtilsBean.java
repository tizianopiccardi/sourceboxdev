package cc.sourcebox.beans;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.RandomStringUtils;

import cc.sourcebox.entities.Operation;

/**
 * Session Bean implementation class UtilsBean
 */
@Stateless
@LocalBean
public class UtilsBean implements UtilsBeanRemote, UtilsBeanLocal {

	public static final long _3Minutes = 1000*60*3;
	
    /**
     * Default constructor. 
     */
    public UtilsBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public String getRandomString(int size) {
		return RandomStringUtils.random(size, true, true);
	}

	@Override
	public Date getUsersTimeDeadline() {
		Calendar cal = Calendar.getInstance(); 
		cal.setTimeInMillis(System.currentTimeMillis()-_3Minutes);
		return cal.getTime();
	}

	@Override
	public String digest(String original, List<Operation> operations) {

		return "";
	}


}
