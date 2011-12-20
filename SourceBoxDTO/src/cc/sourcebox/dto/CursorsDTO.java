package cc.sourcebox.dto;

import java.io.Serializable;

public class CursorsDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5534921142643680586L;
	private int uid, line, ch;

	public CursorsDTO() {
	}
	
	public CursorsDTO(int u, int l, int c) {
		uid = u;line = l; ch = c;
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

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	
}
