package es.oaw.irapvalidator.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import es.oaw.irapvalidator.model.Role;

/**
 * The Class UsuarioDTO.
 */
public class UserDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2566597506016623695L;

	/** The id usuario. */
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

	/** The password anterior. */
	private String prevoiusPassword;

	/** The password repetir. */
	@NotNull(message = "{field.required}")
	@NotEmpty(message = "{field.required}")
	private String repeatPassword;

	/** The nombre. */
	@NotNull(message = "{field.required}")
	@NotEmpty(message = "{field.required}")
	private String name;

	/** The apellidos. */
	private String surname;

	/** The email. */
	private String email;

	/** The ura. */
	private String ura;

	/** The rols. */
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
	 * Gets the prevoius password.
	 *
	 * @return the prevoius password
	 */
	public String getPrevoiusPassword() {
		return prevoiusPassword;
	}

	/**
	 * Sets the prevoius password.
	 *
	 * @param prevoiusPassword the new prevoius password
	 */
	public void setPrevoiusPassword(String prevoiusPassword) {
		this.prevoiusPassword = prevoiusPassword;
	}

	/**
	 * Gets the repeat password.
	 *
	 * @return the repeat password
	 */
	public String getRepeatPassword() {
		return repeatPassword;
	}

	/**
	 * Sets the repeat password.
	 *
	 * @param repeatPassword the new repeat password
	 */
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
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
	 * Gets the surname.
	 *
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets the surname.
	 *
	 * @param surname the new surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
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

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the ura.
	 *
	 * @return the ura
	 */
	public String getUra() {
		return ura;
	}

	/**
	 * Sets the ura.
	 *
	 * @param ura the new ura
	 */
	public void setUra(String ura) {
		this.ura = ura;
	}

}
