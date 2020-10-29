package es.oaw.irapvalidator.datatable;

import java.util.List;

/**
 * The Class DatatablePage.
 */
public class DatatablePage {

	/** The content. */
	private List content;

	/** The records total. */
	private Long recordsTotal;

	/** The records filtered. */
	private Long recordsFiltered;

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public List getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the content to set
	 */
	public void setContent(List content) {
		this.content = content;
	}

	/**
	 * Gets the records total.
	 *
	 * @return the recordsTotal
	 */
	public Long getRecordsTotal() {
		return recordsTotal;
	}

	/**
	 * Sets the records total.
	 *
	 * @param recordsTotal the recordsTotal to set
	 */
	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	/**
	 * Gets the records filtered.
	 *
	 * @return the recordsFiltered
	 */
	public Long getRecordsFiltered() {
		return recordsFiltered;
	}

	/**
	 * Sets the records filtered.
	 *
	 * @param recordsFiltered the recordsFiltered to set
	 */
	public void setRecordsFiltered(Long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

}
