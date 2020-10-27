package es.oaw.irapvalidator.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id usuario. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/** The activo. */
	private int active;

	/** The login. */
	@NotNull(message = "{field.required}")
	@NotEmpty(message = "{field.required}")
	private String username;

	/** The password. */
	@NotNull(message = "{field.required}")
	@NotEmpty(message = "{field.required}")
	private String password;

	/** The rols. */
	// bi-directional many-to-many association to Rol
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "id_user") }, inverseJoinColumns = {
			@JoinColumn(name = "id_role") })
	private List<Role> roles;

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
	 * Gets the active.
	 *
	 * @return the active
	 */
	public int getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(int active) {
		this.active = active;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles the new roles
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}