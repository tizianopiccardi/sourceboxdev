package cc.sourcebox.beans;
import java.util.List;

import javax.ejb.Remote;

import cc.sourcebox.beans.exceptions.ChatErrorException;
import cc.sourcebox.dto.ChatMessage;
import cc.sourcebox.dto.EventsDTO;
import cc.sourcebox.dto.InsertObject;
import cc.sourcebox.dto.UserInfo;



@Remote
public interface BoxManagerRemote {

	public void init(UserInfo user, String alias);
	public boolean somethingNew();
	public EventsDTO getEvents();
	
	public void send(String msg) throws ChatErrorException;
	public void setCursor(int l, int c);
	
	public void edit(List<InsertObject> inserts);
	/*
	public String getS();
	public int getV();*/
}
