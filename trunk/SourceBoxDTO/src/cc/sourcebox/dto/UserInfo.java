package cc.sourcebox.dto;

import java.io.Serializable;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int userid, line, ch;
	String username;
	boolean add = true;

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public UserInfo() {}
	
	public UserInfo(int u, String un) {
		userid = u; username=un;
	}
	
	public UserInfo(int u, String un, int l, int c) {
		userid = u; line = l; ch = c; username=un;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getCh() {
		return ch;
	}

	public void setCh(int ch) {
		this.ch = ch;
	}
	
	
	
}
