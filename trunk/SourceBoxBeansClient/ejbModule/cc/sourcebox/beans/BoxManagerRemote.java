package cc.sourcebox.beans;
import javax.ejb.Remote;

import cc.sourcebox.dto.EventsDTO;



@Remote
public interface BoxManagerRemote {

	public void init(String alias);
	public boolean somethingNew();
	public EventsDTO getEvents();
	
	
	/*
	public String getS();
	public int getV();*/
}
