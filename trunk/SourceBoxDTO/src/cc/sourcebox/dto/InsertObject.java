package cc.sourcebox.dto;

import java.io.Serializable;

public class InsertObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int uid,fromLine,fromChar,toLine,toChar, sq;
	String text;
	
	public InsertObject() {}
	
	public InsertObject(int s, int u, int fl, int fc, int tl, int tc, String txt) {
		sq=s;
		uid = u;
		fromLine = fl;
		fromChar = fc;
		toLine = tl;
		toChar = tc;
		text = txt;
	}
	

	public int getSq() {
		return sq;
	}

	public void setSq(int sq) {
		this.sq = sq;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getFromLine() {
		return fromLine;
	}
	public void setFromLine(int fromLine) {
		this.fromLine = fromLine;
	}
	public int getFromChar() {
		return fromChar;
	}
	public void setFromChar(int fromChar) {
		this.fromChar = fromChar;
	}
	public int getToLine() {
		return toLine;
	}
	public void setToLine(int toLine) {
		this.toLine = toLine;
	}
	public int getToChar() {
		return toChar;
	}
	public void setToChar(int toChar) {
		this.toChar = toChar;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "\n{USR: " + getUid() +
				"\nFL: " + getFromLine() + 
				"\nFC: " + getFromChar() + 
				"\nTL: " + getToLine() + 
				"\nTC: " + getToChar()+
				"\nText: " + getText()+"}\n----------";
	}


	
	
}
