package cc.sourcebox.dto;

public class RevisionDTO {

	private int id;
	private String text;
	
	public RevisionDTO() {}
	
	public RevisionDTO(int i, String t) {
		id=i; text = t;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return id+"\n"+text;
	}
	
	
}
