package es.oaw.irapvalidator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.oaw.irapvalidator.model.Role;

/**
 * The Interface RoleRepository.
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
	/**
	 * Find by nombre.
	 *
	 * @param role the role
	 * @return the rol
	 */
	Role findByName(String role);

}