package es.oaw.irapvalidator.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the configuracion database table.
 * 
 */
@Entity
@NamedQuery(name = "Configuration.findAll", query = "SELECT c FROM Configuration c")
public class Configuration implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id configuracion. */
	@Id
	@GeneratedValue
	private Integer id;

	/** The key. */
	@Column(name="\"key\"")
	private String key;

	/** The value. */
	@Column(name="\"value\"")
	private String value;

	/**
	 * Instantiates a new configuracion.
	 */
	public Configuration() {
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}