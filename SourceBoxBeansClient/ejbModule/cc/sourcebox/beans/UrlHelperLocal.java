package cc.sourcebox.beans;
import javax.ejb.Local;

@Local
public interface UrlHelperLocal {

	/***********
	 * Convert a decimal to a base62 string
	 * @param id
	 * @return
	 */
    public String idToAlias (int id);

    /***********
     * Convert from a base62 string to decimal
     * @param alias
     * @return
     */
    public int aliasToId(String alias);
	
}
