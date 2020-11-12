package es.oaw.irapvalidator;

import java.io.Serializable;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import es.oaw.irapvalidator.model.Configuration;
import es.oaw.irapvalidator.repository.ConfigurationRepository;

/**
 * The Class ConfigurationPermissionEvaluator.
 * 
 * Custom permision to evaluate custom configuration o allow anonymous user to
 * certain paths
 */
@Component
public class ConfigurationPermissionEvaluator implements PermissionEvaluator {

	/** The configuration repository. */
	@Autowired
	private ConfigurationRepository configurationRepository;

	/** The acces config. */
	private Configuration accesConfig = null;

	/**
	 * Evaluate permissions.
	 *
	 * @param authentication the authentication
	 * @return true, if successful
	 */
	private boolean evaluatePermissions(Authentication authentication) {
		accesConfig = configurationRepository.findByKey("anonymous.access").get();
		if (accesConfig != null && Boolean.TRUE.toString().equalsIgnoreCase(accesConfig.getValue())) {
			return true;
		} else {
			return !"anonymousUser".equals(authentication.getPrincipal());
		}
	}

	/**
	 * Checks for permission.
	 *
	 * @param authentication     the authentication
	 * @param targetDomainObject the target domain object
	 * @param permission         the permission
	 * @return true, if successful
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		try {
			return evaluatePermissions(authentication);
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Checks for permission.
	 *
	 * @param authentication the authentication
	 * @param targetId       the target id
	 * @param targetType     the target type
	 * @param permission     the permission
	 * @return true, if successful
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		try {
			return evaluatePermissions(authentication);

		} catch (NoSuchElementException e) {
			return false;
		}
	}

}
