package es.fundacionbarredo.catastrominero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.fundacionbarredo.catastrominero.entity.Usuario;

/**
 * The Interface UserRepository.
 */
public interface UserRepository  extends JpaRepository <Usuario, Integer>{
	
}
