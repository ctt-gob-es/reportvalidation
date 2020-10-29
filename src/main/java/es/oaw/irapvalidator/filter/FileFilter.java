package es.oaw.irapvalidator.filter;

import java.io.Serializable;

/**
 * Filter to Concesion search.
 * 
 * This class is used to pass filter fields from view.
 * 
 * Every field on form has an {@link FilterField} implementacion asigned. The
 * value name of filterfield is setting on setter to prevent override name of field thas is the path to de field of search in the entity.
 * 
 */
public class FileFilter extends Filter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant FIELDNAME_NOMBRE. */
	private static final String FIELDNAME_NOMBRE = "nombre";
	
	/** The nombre. */
	private FilterGenericField nombre;

	/**
	 * @return the nombre
	 */
	public FilterGenericField getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(FilterGenericField nombre) {
		nombre.setName(FIELDNAME_NOMBRE);
		this.nombre = nombre;
	}

}
