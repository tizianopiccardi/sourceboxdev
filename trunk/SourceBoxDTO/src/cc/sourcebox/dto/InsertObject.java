package cc.sourcebox.dto;

import java.io.Serializable;

public class InsertObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int sequence,fromLine,fromChar,toLine,toChar;
	String text;
	
	public InsertObject() {}
	
	public InsertObject(int s, int fl, int fc, int tl, int tc, String txt) {
		sequence = s;
		fromLine = fl;
		fromChar = fc;
		toLine = tl;
		toChar = tc;
		text = txt;
	}
	
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
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
		return "\n{SQ: " + getSequence() +
				"\nFL: " + getFromLine() + 
				"\nFC: " + getFromChar() + 
				"\nTL: " + getToLine() + 
				"\nTC: " + getToChar()+
				"\nText: " + getText()+"}\n----------";
	}


	
	
}
