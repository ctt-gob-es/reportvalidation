package es.oaw.irapvalidator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.oaw.irapvalidator.model.Dir3;
import es.oaw.irapvalidator.repository.Dir3Repository;

/**
 * The Class Dir3Service.
 */
@Service("dir3Service")
public class Dir3Service {

	/** The dir 3 repository. */
	@Autowired
	private Dir3Repository dir3Repository;

	/**
	 * Exists dir 3.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean existsDir3(final String id) {
		return dir3Repository.findById(id).isPresent();
	}

	/**
	 * Gets the dir 3.
	 *
	 * @param id the id
	 * @return the dir 3
	 */
	public Dir3 getDir3(final String id) {
		return dir3Repository.findById(id).get();
	}

}
