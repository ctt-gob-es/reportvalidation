package es.oaw.irapvalidator;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * The Class RefererRedirectionAuthenticationSuccessHandler.
 */
public class RefererRedirectionAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	/**
	 * Instantiates a new referer redirection authentication success handler.
	 */
	public RefererRedirectionAuthenticationSuccessHandler() {
		super();
		setUseReferer(true);
	}
}
