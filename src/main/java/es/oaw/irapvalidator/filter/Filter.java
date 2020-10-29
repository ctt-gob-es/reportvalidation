package es.oaw.irapvalidator.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * Filter Clase abstracta para implementar los filtos de búsqueda en elos
 * formularios. Se especifica la clase hija a la que se mapea el json con la
 * anotación jsonsubtyles. En el json se indica en la propiedad type el valor
 * para que se escoja el objeto al que va el json recibido
 */
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = UserFilter.class, name = "userFilter"),
		@JsonSubTypes.Type(value = FileFilter.class, name = "fileFilter"),
		 })
public abstract class Filter {

}
