package es.oaw.irapvalidator.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * Filter Abstract class to implement search filters in the forms. Specify the
 * child class to which the json is mapped with the {@link JsonSubTypes}
 * annotation. In the json is indicated in the property type the value to choose
 * the object to which the json received goes
 */
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = UserFilter.class, name = "userFilter"),
		@JsonSubTypes.Type(value = FileFilter.class, name = "fileFilter"), })
public abstract class Filter {

}
