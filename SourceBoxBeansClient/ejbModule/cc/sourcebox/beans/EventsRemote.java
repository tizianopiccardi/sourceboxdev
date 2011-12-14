package cc.sourcebox.beans;
import javax.ejb.Remote;

import cc.sourcebox.dto.EventsDTO;



@Remote
public interface EventsRemote {

	public void init(String alias);
	public boolean somethingNew();
	public EventsDTO get();
	
	
	/*
	public String getS();
	public int getV();*/
}
