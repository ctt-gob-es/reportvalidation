package es.oaw.irapvalidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * The Class MethodSecurityConfig.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
	
	@Autowired
	ConfigurationPermissionEvaluator configurationPermissionEvaluator;
	
	 /**
 	 * Creates the expression handler.
 	 *
 	 * @return the method security expression handler
 	 */
 	@Override
	    protected MethodSecurityExpressionHandler createExpressionHandler() {
	        DefaultMethodSecurityExpressionHandler expressionHandler = 
	          new DefaultMethodSecurityExpressionHandler();
	        expressionHandler.setPermissionEvaluator(configurationPermissionEvaluator);
	        return expressionHandler;
	    }

}
