package es.oaw.irapvalidator.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * The persistent class for the rol database table.
 * 
 */
@Entity
@Table(name = "role")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id rol. */
	@Id
	private Integer id;

	/** The nombre. */
	private String name;

	/** The usuarios. */
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private List<User> users;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users the new users
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

}