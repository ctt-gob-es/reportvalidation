package es.oaw.irapvalidator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.oaw.irapvalidator.model.Dir3;

/**
 * The Interface Dir3Repository.
 */
@Repository("dir3Repository")
public interface Dir3Repository extends JpaRepository<Dir3, String> {

}
