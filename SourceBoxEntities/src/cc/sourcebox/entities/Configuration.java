package cc.sourcebox.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the configuration database table.
 * 
 */
@Entity
public class Configuration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idconfiguration;

	private String k;

	private String v;

    public Configuration() {
    }

	public int getIdconfiguration() {
		return this.idconfiguration;
	}

	public void setIdconfiguration(int idconfiguration) {
		this.idconfiguration = idconfiguration;
	}

	public String getK() {
		return this.k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getV() {
		return this.v;
	}

	public void setV(String v) {
		this.v = v;
	}

}