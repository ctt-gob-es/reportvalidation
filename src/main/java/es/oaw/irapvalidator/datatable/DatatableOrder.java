package es.oaw.irapvalidator.datatable;

/**
 * The Class Order.
 */
public class DatatableOrder {

	/** The Constant ASC. */
	public static final String ASC = "asc";

	/** The Constant DESC. */
	public static final String DESC = "desc";

	/**
	 * Column to which ordering should be applied. This is an index reference to the
	 * columns array of information that is also submitted to the server.
	 */
	private int column;

	/**
	 * Ordering direction for this column. It will be asc or desc to indicate
	 * ascending ordering or descending ordering, respectively.
	 */
	private String dir;

	/**
	 * Gets the column.
	 *
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Sets the column.
	 *
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Gets the dir.
	 *
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * Sets the dir.
	 *
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

}
