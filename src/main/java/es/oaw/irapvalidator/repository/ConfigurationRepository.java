package es.oaw.irapvalidator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.oaw.irapvalidator.model.Configuration;

/**
 * The Interface ConfigurationRepository.
 */
@Repository("configurationRepository")
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
	
	/**
	 * Find by key.
	 *
	 * @param key the key
	 * @return the optional
	 */
	Optional<Configuration> findByKey(final String key);

}
