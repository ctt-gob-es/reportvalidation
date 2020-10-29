package es.oaw.irapvalidator.datatable;

import java.util.List;

import es.oaw.irapvalidator.filter.Filter;

/**
 * The Class DatatablePagination.
 */
public class DatatablePagination {
	
	/** The ie. */
	private int ie = 0;
	
	/** The query. */
	private String query;
	
	/** The all. */
	private String all;
	
	/** The filter. */
	private Filter filter;
	/**
	 * Draw counter. This is used by DataTables to ensure that the Ajax returns from
	 * server-side processing requests are drawn in sequence by DataTables (Ajax
	 * requests are asynchronous and thus can return out of sequence). This is used
	 * as part of the draw return parameter.
	 */
	private int draw;

	/**
	 * Paging first record indicator. This is the start point in the current data
	 * set (0 index based - i.e. 0 is the first record).
	 */
	private int start;

	/**
	 * Number of records that the table can display in the current draw. It is
	 * expected that the number of records returned will be equal to this number,
	 * unless the server has fewer records to return. Note that this can be -1 to
	 * indicate that all records should be returned (although that negates any
	 * benefits of server-side processing!)
	 */
	private int length;

	/**
	 * Global search criteria.
	 */
	private DatableSearch search;

	/**
	 * Column's ordering criteria.
	 */
	private List<DatatableOrder> order;
	
	/** The selected. */
	private List<String> selected;
	
	/**
	 * Excluded concesions ids.
	 */
	private List<String> excluded;

	/**
	 * Table column's list.
	 */
	private List<DatatableColumn> columns;

	/**
	 * Gets the ie.
	 *
	 * @return the ie
	 */
	public int getIe() {
		return ie;
	}

	/**
	 * Sets the ie.
	 *
	 * @param ie the new ie
	 */
	public void setIe(int ie) {
		this.ie = ie;
	}

	/**
	 * Gets the draw.
	 *
	 * @return the draw
	 */
	public int getDraw() {
		return draw;
	}

	/**
	 * Gets the excluded.
	 *
	 * @return the excluded
	 */
	public List<String> getExcluded() {
		return excluded;
	}

	/**
	 * Sets the excluded.
	 *
	 * @param excluded the excluded to set
	 */
	public void setExcluded(List<String> excluded) {
		this.excluded = excluded;
	}

	/**
	 * Sets the draw.
	 *
	 * @param draw the draw to set
	 */
	public void setDraw(int draw) {
		this.draw = draw;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 *
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
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
	 * Gets the order.
	 *
	 * @return the order
	 */
	public List<DatatableOrder> getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 *
	 * @param order the order to set
	 */
	public void setOrder(List<DatatableOrder> order) {
		this.order = order;
	}

	/**
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public List<DatatableColumn> getColumns() {
		return columns;
	}

	/**
	 * Sets the columns.
	 *
	 * @param columns the columns to set
	 */
	public void setColumns(List<DatatableColumn> columns) {
		this.columns = columns;
	}

	/**
	 * Gets the filter.
	 *
	 * @return the filter
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * Sets the filter.
	 *
	 * @param filter the filter to set
	 */
	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	/**
	 * Gets the query.
	 *
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Sets the query.
	 *
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	public String getAll() {
		return all;
	}

	/**
	 * Sets the all.
	 *
	 * @param all the new all
	 */
	public void setAll(String all) {
		this.all = all;
	}

	/**
	 * Gets the selected.
	 *
	 * @return the selected
	 */
	public List<String> getSelected() {
		return selected;
	}

	/**
	 * Sets the selected.
	 *
	 * @param selected the new selected
	 */
	public void setSelected(List<String> selected) {
		this.selected = selected;
	}

}
