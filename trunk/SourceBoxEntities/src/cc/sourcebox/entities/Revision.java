package cc.sourcebox.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the revisions database table.
 * 
 */
@Entity
@Table(name="revisions")
public class Revision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idrevision;

	private int rev;

    @Lob()
	private String source;

	//bi-directional many-to-one association to Box
    @ManyToOne
	@JoinColumn(name="idbox")
	private Box box;

	//bi-directional many-to-one association to Operation
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="lastoperation")
	private Operation operation;

    public Revision() {
    }

	public int getIdrevision() {
		return this.idrevision;
	}

	public void setIdrevision(int idrevision) {
		this.idrevision = idrevision;
	}

	public int getRev() {
		return this.rev;
	}

	public void setRev(int rev) {
		this.rev = rev;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Box getBox() {
		return this.box;
	}

	public void setBox(Box box) {
		this.box = box;
	}
	
	public Operation getOperation() {
		return this.operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
}