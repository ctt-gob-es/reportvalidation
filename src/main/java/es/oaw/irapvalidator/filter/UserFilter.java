package es.oaw.irapvalidator.filter;

import java.io.Serializable;

/**
 * The Class UserFilter.
 */
public class UserFilter extends Filter implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant FIELDNAME_NOMBRE. */
	private static final String FIELDNAME_NAME = "name";

	/** The nombre. */
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
