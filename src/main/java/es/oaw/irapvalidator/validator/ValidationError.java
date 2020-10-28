package es.oaw.irapvalidator.validator;

import java.io.Serializable;

/**
 * The Class ValidationError.
 */
public class ValidationError implements Serializable, Comparable<ValidationError> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4559722023409078582L;

	/** The sheet. */
	private String sheet;

	/** The cell. */
	private String cell;

	/** The message. */
	private String message;

	/**
	 * Instantiates a new validation error.
	 *
	 * @param sheet   the sheet
	 * @param cell    the cell
	 * @param message the message
	 */
	public ValidationError(String sheet, String cell, String message) {
		super();
		this.sheet = sheet;
		this.cell = cell;
		this.message = message;
	}

	/**
	 * Gets the sheet.
	 *
	 * @return the sheet
	 */
	public String getSheet() {
		return sheet;
	}

	/**
	 * Sets the sheet.
	 *
	 * @param sheet the new sheet
	 */
	public void setSheet(String sheet) {
		this.sheet = sheet;
	}

	/**
	 * Gets the cell.
	 *
	 * @return the cell
	 */
	public String getCell() {
		return cell;
	}

	/**
	 * Sets the cell.
	 *
	 * @param cell the new cell
	 */
	public void setCell(String cell) {
		this.cell = cell;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ValidationError [sheet=" + sheet + ", cell=" + cell + ", message=" + message + "]";
	}

	/**
	 * Compare to.
	 *
	 * @param o the o
	 * @return the int
	 */
	@Override
	public int compareTo(ValidationError o) {

		return this.getSheet().compareToIgnoreCase(o.getSheet());
	}

}
