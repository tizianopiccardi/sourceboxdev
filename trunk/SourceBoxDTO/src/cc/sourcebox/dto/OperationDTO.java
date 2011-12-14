package cc.sourcebox.dto;

import java.io.Serializable;

public class OperationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1601205664356510647L;

	String txt;
	int fl, fc, tl, tc;
	public String getTxt() {
		return txt;
	}
	
	
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public int getFl() {
		return fl;
	}
	public void setFl(int fl) {
		this.fl = fl;
	}
	public int getFc() {
		return fc;
	}
	public void setFc(int fc) {
		this.fc = fc;
	}
	public int getTl() {
		return tl;
	}
	public void setTl(int tl) {
		this.tl = tl;
	}
	public int getTc() {
		return tc;
	}
	public void setTc(int tc) {
		this.tc = tc;
	}
	
	
}
