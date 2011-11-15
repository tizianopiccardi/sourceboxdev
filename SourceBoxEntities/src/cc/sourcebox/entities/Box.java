package cc.sourcebox.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the boxes database table.
 * 
 */
@Entity
@Table(name="boxes")
public class Box implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idboxes;

	private String alias;

	private Timestamp creation;

	private String language;

	private Timestamp lastvisit;

	private String password;

	private int readonly;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="box")
	private List<Message> messages;

	//bi-directional many-to-one association to Operation
	@OneToMany(mappedBy="box")
	private List<Operation> operations;

	//bi-directional many-to-one association to Revision
	@OneToMany(mappedBy="box", cascade={CascadeType.ALL})
	private List<Revision> revisions;

    public Box() {
    }

	public int getIdboxes() {
		return this.idboxes;
	}

	public void setIdboxes(int idboxes) {
		this.idboxes = idboxes;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Timestamp getCreation() {
		return this.creation;
	}

	public void setCreation(Timestamp creation) {
		this.creation = creation;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Timestamp getLastvisit() {
		return this.lastvisit;
	}

	public void setLastvisit(Timestamp lastvisit) {
		this.lastvisit = lastvisit;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getReadonly() {
		return this.readonly;
	}

	public void setReadonly(int readonly) {
		this.readonly = readonly;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public List<Operation> getOperations() {
		return this.operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
	
	public List<Revision> getRevisions() {
		return this.revisions;
	}

	public void setRevisions(List<Revision> revisions) {
		this.revisions = revisions;
	}
	
}