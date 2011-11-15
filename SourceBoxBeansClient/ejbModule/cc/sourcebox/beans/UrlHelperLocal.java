package cc.sourcebox.beans;
import javax.ejb.Local;

@Local
public interface UrlHelperLocal {

    public String idToAlias (int id);

    public int aliasToId(String alias);
	
}
