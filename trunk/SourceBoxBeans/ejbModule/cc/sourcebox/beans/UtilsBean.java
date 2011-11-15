package cc.sourcebox.beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Session Bean implementation class UtilsBean
 */
@Stateless
@LocalBean
public class UtilsBean implements UtilsBeanRemote, UtilsBeanLocal {

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


}
