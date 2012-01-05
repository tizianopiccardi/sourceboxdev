package cc.sourcebox.beans;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.RandomStringUtils;

import cc.sourcebox.entities.Operation;
import cc.sourcebox.testing.Tester;

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
		/*StringBuilder buffer = new StringBuilder(original);
		for (int i = 0; i < operations.size(); i++) {
			
			String [] lines = buffer.toString().split("\n");
			
			Operation o = operations.get(i);
			int fl = o.getFromLine();
			int fc = o.getFromChar();
			int tl = o.getToLine();
			int tc = o.getToChar();
			
			//if (tl>=lines.length) tl = lines.length-1;
			//if (tc>=lines[tl].length()) tc = lines[tl].length()-1;

			lines[fl] = lines[fl].substring(0, fc) 
							+ o.getString() 
							+ lines[tl].substring(tc, lines[tl].length());
			
			for (int j = fl+1; j <= tl; j++) 
				lines[j]=null;
			
			
			buffer.setLength(0);
			for (int j = 0; j < lines.length; j++)
				if (lines[j]!=null) 
					buffer.append(lines[j]).append((j==lines.length-1)?"":"\n");

		}
		
		
		return buffer.toString();*/
		return Tester.digest(original, operations);
	}


}
