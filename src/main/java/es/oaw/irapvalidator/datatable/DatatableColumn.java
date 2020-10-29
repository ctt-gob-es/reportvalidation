package es.oaw.irapvalidator.datatable;

/**
 * The Class DatatableColumn.
 */
public class DatatableColumn {
	/**
	 * Column's data source.
	 */
	private String data;

	/**
	 * Column's name.
	 */
	private String name;

	/**
	 * Flag to indicate if this column is searchable (true) or not (false).
	 */
	private boolean searchable;

	/**
	 * Flag to indicate if this column is orderable (true) or not (false).
	 */
	private boolean orderable;

	/**
	 * Search criteria to apply to this specific column.
	 */
	private DatableSearch search;
	
	private boolean visible;

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks if is searchable.
	 *
	 * @return the searchable
	 */
	public boolean isSearchable() {
		return searchable;
	}

	/**
	 * Sets the searchable.
	 *
	 * @param searchable the searchable to set
	 */
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	/**
	 * Checks if is orderable.
	 *
	 * @return the orderable
	 */
	public boolean isOrderable() {
		return orderable;
	}

	/**
	 * Sets the orderable.
	 *
	 * @param orderable the orderable to set
	 */
	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}

	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	public DatableSearch getSearch() {
		return search;
	}

	/**
	 * Sets the search.
	 *
	 * @param search the search to set
	 */
	public void setSearch(DatableSearch search) {
		this.search = search;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
