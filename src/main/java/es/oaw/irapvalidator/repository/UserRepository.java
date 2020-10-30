package es.oaw.irapvalidator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.oaw.irapvalidator.model.User;

/**
 * The Interface UserRepository.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

}
