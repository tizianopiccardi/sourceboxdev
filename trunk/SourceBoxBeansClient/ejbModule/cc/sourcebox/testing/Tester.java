package cc.sourcebox.testing;

import java.util.HashMap;
import java.util.Properties;

import javax.naming.InitialContext;

import com.google.gson.Gson;

import cc.sourcebox.beans.BoxBeanRemote;
import cc.sourcebox.entities.Revision;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {


			Properties properties = new Properties();

			properties.setProperty("java.naming.factory.initial",
					"org.jnp.interfaces.NamingContextFactory");
			properties.setProperty("java.naming.provider.url",
					"jnp://localhost:1099");
			properties.setProperty("java.naming.factory.url.pkgs",
					"org.jboss.naming:org.jnp.interfaces");
			InitialContext ctx = new InitialContext(properties);

			
			
			
			
			
			
			
			BoxBeanRemote b = (BoxBeanRemote) ctx
					.lookup("SourceBoxLogicEAR/BoxBean/remote");

			Revision rev = (Revision)b.get(0,"xp",null);

			
			Gson gson= new Gson();
			
			HashMap<String, Object> boxInfo = new HashMap<String, Object>();
			
			boxInfo.put("alias", rev.getBox().getAlias());
			boxInfo.put("code", rev.getSource());
			boxInfo.put("language", rev.getBox().getLanguage());
			boxInfo.put("readonly", rev.getBox().getReadonly());
			boxInfo.put("revision", rev.getRev());
			
			System.out.println(gson.toJson(boxInfo));


	}

}
