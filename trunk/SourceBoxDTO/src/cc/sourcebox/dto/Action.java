package cc.sourcebox.dto;

import java.io.Serializable;

public class Action implements Serializable {
	private String name = null;
	private Object params = null;
	public Action(String name, Object params){ 
        this.name = name;
        this.params = params;
    }
	public Action(String name){ 
        this.name = name;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getParams() {
		return params;
	}
	public void setParams(Object params) {
		this.params = params;
	}

	
	
}