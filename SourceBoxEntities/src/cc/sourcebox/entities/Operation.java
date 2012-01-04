package cc.sourcebox.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the operations database table.
 * 
 */
@Entity
@Table(name="operations")
public class Operation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idoperation;

	@Column(name="from_char")
	private int fromChar;

	@Column(name="from_line")
	private int fromLine;

    @Lob()
	private String string;

	@Column(name="to_char")
	private int toChar;

	@Column(name="to_line")
	private int toLine;

	//bi-directional many-to-one association to Box
    @ManyToOne
	@JoinColumn(name="boxes_idboxes")
	private Box box;

	//bi-directional many-to-one association to Revision
	@OneToMany(mappedBy="operation")
	private List<Revision> revisions;

    public Operation() {
    }

	public int getIdoperation() {
		return this.idoperation;
	}

	public void setIdoperation(int idoperation) {
		this.idoperation = idoperation;
	}

	public int getFromChar() {
		return this.fromChar;
	}

	public void setFromChar(int fromChar) {
		this.fromChar = fromChar;
	}

	public int getFromLine() {
		return this.fromLine;
	}

	public void setFromLine(int fromLine) {
		this.fromLine = fromLine;
	}

	public String getString() {
		return this.string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public int getToChar() {
		return this.toChar;
	}

	public void setToChar(int toChar) {
		this.toChar = toChar;
	}

	public int getToLine() {
		return this.toLine;
	}

	public void setToLine(int toLine) {
		this.toLine = toLine;
	}

	public Box getBox() {
		return this.box;
	}

	public void setBox(Box box) {
		this.box = box;
	}
	
	public List<Revision> getRevisions() {
		return this.revisions;
	}

	public void setRevisions(List<Revision> revisions) {
		this.revisions = revisions;
	}
	
}