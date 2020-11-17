package es.oaw.irapvalidator;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * The Class IrapvalidatorApplication.
 */
@SpringBootApplication
public class IrapvalidatorApplication {

	/** The context. */
	private static ConfigurableApplicationContext context;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		context = SpringApplication.run(IrapvalidatorApplication.class, args);
	}

	/**
	 * Multipart resolver.
	 *
	 * @return the commons multipart resolver
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(100 * 2014 * 2014);
		multipartResolver.setDefaultEncoding("UTF-8");
		multipartResolver.setPreserveFilename(true);
		return multipartResolver;
	}

	/**
	 * Servlet container.
	 *
	 * @return the tomcat servlet web server factory
	 */
	@Bean
	public TomcatServletWebServerFactory servletContainer() {

		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();

		Connector ajpConnector = new Connector("AJP/1.3");
		ajpConnector.setPort(8091);
		ajpConnector.setSecure(false);
		ajpConnector.setAllowTrace(false);
		ajpConnector.setScheme("http");
		((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecretRequired(false);
		tomcat.addAdditionalTomcatConnectors(ajpConnector);

		return tomcat;
	}
}
