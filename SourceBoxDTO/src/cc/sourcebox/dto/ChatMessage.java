package cc.sourcebox.dto;

import java.io.Serializable;

public class ChatMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2695034597281170856L;
	
	String user, text;

	public ChatMessage() {
		// TODO Auto-generated constructor stub
	}
	public ChatMessage(String u, String t) {
		user = u; text = t;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
