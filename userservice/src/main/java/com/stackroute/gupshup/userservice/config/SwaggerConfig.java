package com.stackroute.gupshup.userservice.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Autowired
	MessageSource messageSource;
	
	@Bean
	public Docket productApi() {
				return new Docket(DocumentationType.SWAGGER_2)
						.select()
						.apis(RequestHandlerSelectors.basePackage("com.stackroute.gupshup.userservice.controller"))
						.paths(regex("/.*"))
						.build()
						.apiInfo(metaData());
	}
	
	private ApiInfo metaData() {
		
		/*Locale locale = LocaleContextHolder.getLocale();
		String message1 = messageSource.getMessage ("error.user.alreadyregistered", null, locale );
		String message2 = messageSource.getMessage ("error.user.alreadyregistered", null, locale );
		*/
		ApiInfo apiInfo = new ApiInfo(
				"Spring Boot USER REST API",
				"Spring Boot REST API for USER",
				"1.0",
				"Terms of service",
				new Contact("Stack Route", "https://stackroute.in/", "charu.bhatt@stackroute.in, randeep.kaur@stackroute.in"),
				"Apache License Version 2.0",
				"https://www.apache.org/licenses/LICENSE-2.0");
		return apiInfo;
	}

}