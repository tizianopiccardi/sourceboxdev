package cc.sourcebox.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int iduser;

	@Column(name="last_activity")
	private Timestamp lastActivity;

	private String name;

	//bi-directional many-to-one association to Inbox
	@OneToMany(mappedBy="user", cascade={CascadeType.ALL})
	private List<Inbox> inbox;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="user")
	private List<Message> messages;

    public User() {
    }

	public int getIduser() {
		return this.iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public Timestamp getLastActivity() {
		return this.lastActivity;
	}

	public void setLastActivity(Timestamp lastActivity) {
		this.lastActivity = lastActivity;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Inbox> getInbox() {
		return this.inbox;
	}

	public void setInbox(List<Inbox> inbox) {
		this.inbox = inbox;
	}
	
	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
}