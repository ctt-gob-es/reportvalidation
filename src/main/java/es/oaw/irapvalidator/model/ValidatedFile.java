package es.oaw.irapvalidator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * The Class ValidatedFile.
 */
@Entity
@Table(name = "files")
public class ValidatedFile {

	/** The id. */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	/** The name. */
	private String name;

	/** The type. */
	private String type;

	/** The data. */
	@Lob
	private byte[] data;

	/**
	 * Instantiates a new validated file.
	 */
	public ValidatedFile() {
	}

	/**
	 * Instantiates a new validated file.
	 *
	 * @param name the name
	 * @param type the type
	 * @param data the data
	 */
	public ValidatedFile(String name, String type, byte[] data) {
		this.name = name;
		this.type = type;
		this.data = data;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

}