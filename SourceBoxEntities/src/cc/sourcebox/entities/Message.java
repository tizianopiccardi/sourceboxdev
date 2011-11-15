package cc.sourcebox.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the messages database table.
 * 
 */
@Entity
@Table(name="messages")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idmessages;

    @Lob()
	private String text;

	private Timestamp time;

	//bi-directional many-to-one association to Box
    @ManyToOne
	@JoinColumn(name="idbox")
	private Box box;

	//bi-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="iduser")
	private User user;

    public Message() {
    }

	public int getIdmessages() {
		return this.idmessages;
	}

	public void setIdmessages(int idmessages) {
		this.idmessages = idmessages;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
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