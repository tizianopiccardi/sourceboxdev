package cc.sourcebox.dto;

import java.io.Serializable;

public class ChatMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2695034597281170856L;
	
	int uid;
	
	String text;

	public ChatMessage() {}
	public ChatMessage(int u, String t) {
		uid = u; text = t; 
	}
	


	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
