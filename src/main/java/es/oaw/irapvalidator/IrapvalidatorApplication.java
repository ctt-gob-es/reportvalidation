package es.oaw.irapvalidator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import es.oaw.irapvalidator.storage.StorageProperties;
import es.oaw.irapvalidator.storage.StorageService;


/**
 * The Class IrapvalidatorApplication.
 */
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class IrapvalidatorApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(IrapvalidatorApplication.class, args);
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


	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
