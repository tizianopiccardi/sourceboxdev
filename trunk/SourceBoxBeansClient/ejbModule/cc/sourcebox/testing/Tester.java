package cc.sourcebox.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;

import com.google.gson.Gson;

import cc.sourcebox.beans.BoxInfoBeanRemote;
import cc.sourcebox.beans.UtilsBeanLocal;
import cc.sourcebox.beans.UtilsBeanRemote;
import cc.sourcebox.entities.Operation;
import cc.sourcebox.entities.Revision;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {

/*
			Properties properties = new Properties();

			properties.setProperty("java.naming.factory.initial",
					"org.jnp.interfaces.NamingContextFactory");
			properties.setProperty("java.naming.provider.url",
					"jnp://localhost:1099");
			properties.setProperty("java.naming.factory.url.pkgs",
					"org.jboss.naming:org.jnp.interfaces");
			InitialContext ctx = new InitialContext(properties);

			*/
			
			//UtilsBeanRemote u = (UtilsBeanRemote) ctx.lookup("SourceBoxLogicEAR/UtilsBean/remote");
			
			
			
			String string = "test";
			
			List<Operation> o = new ArrayList<Operation>();
			
			
			Operation op = new Operation();
			op.setString("a");
			op.setFromLine(0);
			op.setFromChar(4);
			op.setToLine(0);
			op.setToChar(4);
			
			
			o.add(op);
			
			System.out.println(Tester.digest(string, o));
			
			
			/*
			BoxInfoBeanRemote b = (BoxInfoBeanRemote) ctx
					.lookup("SourceBoxLogicEAR/BoxInfoBean/remote");

			Revision rev = (Revision)b.get(0,"xp",null);

			
			Gson gson= new Gson();
			
			HashMap<String, Object> boxInfo = new HashMap<String, Object>();
			
			boxInfo.put("alias", rev.getBox().getAlias());
			boxInfo.put("code", rev.getSource());
			boxInfo.put("language", rev.getBox().getLanguage());
			boxInfo.put("readonly", rev.getBox().getReadonly());
			boxInfo.put("revision", rev.getRev());
			
			System.out.println(gson.toJson(boxInfo));*/


	}
	
	public static String digest(String original, List<Operation> operations) {
		
System.out.println(operations);
		StringBuilder buffer = new StringBuilder(original);
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
		
		
		return buffer.toString();
	}

}
