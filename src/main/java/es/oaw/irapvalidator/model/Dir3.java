package es.oaw.irapvalidator.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the dir3 database table.
 * 
 */
@Entity
@Table(name = "dir3")
@NamedQuery(name = "Dir3.findAll", query = "SELECT d FROM Dir3 d")
public class Dir3 implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The c id ud organica. */
	@Id
	@Column(name = "C_ID_UD_ORGANICA")
	private String cIdUdOrganica;

	/** The c desc prov. */
	@Column(name = "C_DESC_PROV")
	private String cDescProv;

	/** The c dnm ud organica. */
	@Column(name = "C_DNM_UD_ORGANICA")
	private String cDnmUdOrganica;

	/** The c dnm ud organica principalc id ud organica. */
	@Column(name = "C_DNM_UD_ORGANICA_PRINCIPAL")
	private String cDnmUdOrganicaPrincipal;

	/** The c dnm ud organica superior. */
	@Column(name = "C_DNM_UD_ORGANICA_SUPERIOR")
	private String cDnmUdOrganicaSuperior;

	/** The c id amb provincia. */
	@Column(name = "C_ID_AMB_PROVINCIA")
	private String cIdAmbProvincia;

	/** The c id dep ud principal. */
	@Column(name = "C_ID_DEP_UD_PRINCIPAL")
	private String cIdDepUdPrincipal;

	/** The c id dep ud superior. */
	@Column(name = "C_ID_DEP_UD_SUPERIOR")
	private String cIdDepUdSuperior;

	/** The c id nivel admon. */
	@Column(name = "C_ID_NIVEL_ADMON")
	private int cIdNivelAdmon;

	/**
	 * Instantiates a new dir 3.
	 */
	public Dir3() {
	}

	/**
	 * Gets the c id ud organica.
	 *
	 * @return the c id ud organica
	 */
	public String getCIdUdOrganica() {
		return this.cIdUdOrganica;
	}

	/**
	 * Sets the c id ud organica.
	 *
	 * @param cIdUdOrganica the new c id ud organica
	 */
	public void setCIdUdOrganica(String cIdUdOrganica) {
		this.cIdUdOrganica = cIdUdOrganica;
	}

	/**
	 * Gets the c desc prov.
	 *
	 * @return the c desc prov
	 */
	public String getCDescProv() {
		return this.cDescProv;
	}

	/**
	 * Sets the c desc prov.
	 *
	 * @param cDescProv the new c desc prov
	 */
	public void setCDescProv(String cDescProv) {
		this.cDescProv = cDescProv;
	}

	/**
	 * Gets the c dnm ud organica.
	 *
	 * @return the c dnm ud organica
	 */
	public String getCDnmUdOrganica() {
		return this.cDnmUdOrganica;
	}

	/**
	 * Sets the c dnm ud organica.
	 *
	 * @param cDnmUdOrganica the new c dnm ud organica
	 */
	public void setCDnmUdOrganica(String cDnmUdOrganica) {
		this.cDnmUdOrganica = cDnmUdOrganica;
	}

	/**
	 * Gets the c dnm ud organica superior.
	 *
	 * @return the c dnm ud organica superior
	 */
	public String getCDnmUdOrganicaSuperior() {
		return this.cDnmUdOrganicaSuperior;
	}

	/**
	 * Sets the c dnm ud organica superior.
	 *
	 * @param cDnmUdOrganicaSuperior the new c dnm ud organica superior
	 */
	public void setCDnmUdOrganicaSuperior(String cDnmUdOrganicaSuperior) {
		this.cDnmUdOrganicaSuperior = cDnmUdOrganicaSuperior;
	}

	/**
	 * Gets the c id amb provincia.
	 *
	 * @return the c id amb provincia
	 */
	public String getCIdAmbProvincia() {
		return this.cIdAmbProvincia;
	}

	/**
	 * Sets the c id amb provincia.
	 *
	 * @param cIdAmbProvincia the new c id amb provincia
	 */
	public void setCIdAmbProvincia(String cIdAmbProvincia) {
		this.cIdAmbProvincia = cIdAmbProvincia;
	}

	/**
	 * Gets the c id dep ud principal.
	 *
	 * @return the c id dep ud principal
	 */
	public String getCIdDepUdPrincipal() {
		return this.cIdDepUdPrincipal;
	}

	/**
	 * Sets the c id dep ud principal.
	 *
	 * @param cIdDepUdPrincipal the new c id dep ud principal
	 */
	public void setCIdDepUdPrincipal(String cIdDepUdPrincipal) {
		this.cIdDepUdPrincipal = cIdDepUdPrincipal;
	}

	/**
	 * Gets the c id dep ud superior.
	 *
	 * @return the c id dep ud superior
	 */
	public String getCIdDepUdSuperior() {
		return this.cIdDepUdSuperior;
	}

	/**
	 * Sets the c id dep ud superior.
	 *
	 * @param cIdDepUdSuperior the new c id dep ud superior
	 */
	public void setCIdDepUdSuperior(String cIdDepUdSuperior) {
		this.cIdDepUdSuperior = cIdDepUdSuperior;
	}

	/**
	 * Gets the c id nivel admon.
	 *
	 * @return the c id nivel admon
	 */
	public int getCIdNivelAdmon() {
		return this.cIdNivelAdmon;
	}

	/**
	 * Sets the c id nivel admon.
	 *
	 * @param cIdNivelAdmon the new c id nivel admon
	 */
	public void setCIdNivelAdmon(int cIdNivelAdmon) {
		this.cIdNivelAdmon = cIdNivelAdmon;
	}

	/**
	 * Gets the c id ud organica.
	 *
	 * @return the c id ud organica
	 */
	public String getcIdUdOrganica() {
		return cIdUdOrganica;
	}

	/**
	 * Sets the c id ud organica.
	 *
	 * @param cIdUdOrganica the new c id ud organica
	 */
	public void setcIdUdOrganica(String cIdUdOrganica) {
		this.cIdUdOrganica = cIdUdOrganica;
	}

	/**
	 * Gets the c desc prov.
	 *
	 * @return the c desc prov
	 */
	public String getcDescProv() {
		return cDescProv;
	}

	/**
	 * Sets the c desc prov.
	 *
	 * @param cDescProv the new c desc prov
	 */
	public void setcDescProv(String cDescProv) {
		this.cDescProv = cDescProv;
	}

	/**
	 * Gets the c dnm ud organica.
	 *
	 * @return the c dnm ud organica
	 */
	public String getcDnmUdOrganica() {
		return cDnmUdOrganica;
	}

	/**
	 * Sets the c dnm ud organica.
	 *
	 * @param cDnmUdOrganica the new c dnm ud organica
	 */
	public void setcDnmUdOrganica(String cDnmUdOrganica) {
		this.cDnmUdOrganica = cDnmUdOrganica;
	}

	/**
	 * Gets the c dnm ud organica principal.
	 *
	 * @return the c dnm ud organica principal
	 */
	public String getcDnmUdOrganicaPrincipal() {
		return cDnmUdOrganicaPrincipal;
	}

	/**
	 * Sets the c dnm ud organica principal.
	 *
	 * @param cDnmUdOrganicaPrincipal the new c dnm ud organica principal
	 */
	public void setcDnmUdOrganicaPrincipal(String cDnmUdOrganicaPrincipal) {
		this.cDnmUdOrganicaPrincipal = cDnmUdOrganicaPrincipal;
	}

	/**
	 * Gets the c dnm ud organica superior.
	 *
	 * @return the c dnm ud organica superior
	 */
	public String getcDnmUdOrganicaSuperior() {
		return cDnmUdOrganicaSuperior;
	}

	/**
	 * Sets the c dnm ud organica superior.
	 *
	 * @param cDnmUdOrganicaSuperior the new c dnm ud organica superior
	 */
	public void setcDnmUdOrganicaSuperior(String cDnmUdOrganicaSuperior) {
		this.cDnmUdOrganicaSuperior = cDnmUdOrganicaSuperior;
	}

	/**
	 * Gets the c id amb provincia.
	 *
	 * @return the c id amb provincia
	 */
	public String getcIdAmbProvincia() {
		return cIdAmbProvincia;
	}

	/**
	 * Sets the c id amb provincia.
	 *
	 * @param cIdAmbProvincia the new c id amb provincia
	 */
	public void setcIdAmbProvincia(String cIdAmbProvincia) {
		this.cIdAmbProvincia = cIdAmbProvincia;
	}

	/**
	 * Gets the c id dep ud principal.
	 *
	 * @return the c id dep ud principal
	 */
	public String getcIdDepUdPrincipal() {
		return cIdDepUdPrincipal;
	}

	/**
	 * Sets the c id dep ud principal.
	 *
	 * @param cIdDepUdPrincipal the new c id dep ud principal
	 */
	public void setcIdDepUdPrincipal(String cIdDepUdPrincipal) {
		this.cIdDepUdPrincipal = cIdDepUdPrincipal;
	}

	/**
	 * Gets the c id dep ud superior.
	 *
	 * @return the c id dep ud superior
	 */
	public String getcIdDepUdSuperior() {
		return cIdDepUdSuperior;
	}

	/**
	 * Sets the c id dep ud superior.
	 *
	 * @param cIdDepUdSuperior the new c id dep ud superior
	 */
	public void setcIdDepUdSuperior(String cIdDepUdSuperior) {
		this.cIdDepUdSuperior = cIdDepUdSuperior;
	}

	/**
	 * Gets the c id nivel admon.
	 *
	 * @return the c id nivel admon
	 */
	public int getcIdNivelAdmon() {
		return cIdNivelAdmon;
	}

	/**
	 * Sets the c id nivel admon.
	 *
	 * @param cIdNivelAdmon the new c id nivel admon
	 */
	public void setcIdNivelAdmon(int cIdNivelAdmon) {
		this.cIdNivelAdmon = cIdNivelAdmon;
	}

}