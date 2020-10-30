package es.oaw.irapvalidator.filter;

import java.io.Serializable;

/**
 * The Class FileFilter.
 * 
 * Every field on form has an {@link FilterField} implementation assigned. The
 * value name of filterfield is setting on setter to prevent override name of
 * field thats is the path to the field of search in the entity.
 */
public class FileFilter extends Filter implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant FIELDNAME_NAME. */
	private static final String FIELDNAME_NAME = "name";

	/** The name. */
	private FilterGenericField name;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public FilterGenericField getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(FilterGenericField name) {
		name.setName(FIELDNAME_NAME);
		this.name = name;
	}

}
