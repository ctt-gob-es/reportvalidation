package es.fundacionbarredo.catastrominero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.fundacionbarredo.catastrominero.entity.Rol;

/**
 * The Interface RoleRepository.
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Rol, Integer> {
    
	/**
	 * Find by nombre.
	 *
	 * @param role the role
	 * @return the rol
	 */
	Rol findByNombre(String role);

}