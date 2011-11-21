package cc.sourcebox.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the inbox database table.
 * 
 */
@Entity
public class Inbox implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idinbox;

	@Column(name="cursor_column")
	private int cursorColumn;

	@Column(name="cursor_line")
	private int cursorLine;

    @Temporal( TemporalType.TIMESTAMP)
	private Date since;

	//bi-directional many-to-one association to Box
    @ManyToOne
	@JoinColumn(name="boxes_idboxes")
	private Box box;

	//bi-directional many-to-one association to User
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="users_iduser")
	private User user;

    public Inbox() {
    }

	public int getIdinbox() {
		return this.idinbox;
	}

	public void setIdinbox(int idinbox) {
		this.idinbox = idinbox;
	}

	public int getCursorColumn() {
		return this.cursorColumn;
	}

	public void setCursorColumn(int cursorColumn) {
		this.cursorColumn = cursorColumn;
	}

	public int getCursorLine() {
		return this.cursorLine;
	}

	public void setCursorLine(int cursorLine) {
		this.cursorLine = cursorLine;
	}

	public Date getSince() {
		return this.since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public Box getBox() {
		return this.box;
	}

	public void setBox(Box box) {
		this.box = box;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}