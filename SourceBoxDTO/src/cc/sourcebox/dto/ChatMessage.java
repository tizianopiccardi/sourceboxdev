package cc.sourcebox.dto;

import java.io.Serializable;

public class ChatMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2695034597281170856L;
	
	//int userid;
	UserInfo user;
	
	String text;

	public ChatMessage() {
		// TODO Auto-generated constructor stub
	}
	public ChatMessage(UserInfo u, String t) {
		user = u; text = t; 
	}
	


	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
