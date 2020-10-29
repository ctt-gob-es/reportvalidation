package es.oaw.irapvalidator.datatable;

/**
 * The Class DatableSearch.
 */
public class DatableSearch {
	/**
	 * Search value. To be applied to all columns which have searchable as true.
	 */
	private String value;

	/**
	 * true if the filter should be treated as a regular expression for advanced
	 * searching, false otherwise.
	 */
	private boolean regex; // not used

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Checks if is regex.
	 *
	 * @return the regex
	 */
	public boolean isRegex() {
		return regex;
	}

	/**
	 * Sets the regex.
	 *
	 * @param regex the regex to set
	 */
	public void setRegex(boolean regex) {
		this.regex = regex;
	}

}
