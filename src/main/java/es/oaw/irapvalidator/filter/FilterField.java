package es.oaw.irapvalidator.filter;

/**
 * The Class FilterField.
 * 
 * Abstract class to filter fields in search forms.
 */
public abstract class FilterField {

	/**
	 * Gets the name of the field. This name matches exactly as path of entity field.
	 * 
	 * Admit multiple levels.
	 * 
	 * Examples:
	 *  - entity.field
	 *  - entity.field.field
	 *
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * Gets the value of filter.
	 *
	 * @return the value
	 */
	public abstract Object getValue();

	/**
	 * Gets the operator of filter. Admits:
	 * 
	 * - < less
	 * - > - greather
	 * - : - equal
	 *
	 * @return the operator
	 */
	public abstract String getOperator();

}
