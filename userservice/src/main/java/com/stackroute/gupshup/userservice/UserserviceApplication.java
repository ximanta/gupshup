package com.stackroute.gupshup.userservice;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.stackroute.gupshup.userservice.consumer.UserConsumer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories
@EnableWebMvc
public class UserserviceApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(UserserviceApplication.class, args);
		Environment environment = applicationContext.getEnvironment();
		applicationContext.getBean(UserConsumer.class).subscribeUserActivity(environment.getProperty("userconsumer.user-topic"));
	}
	
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.US);
	    return slr;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	
    @Bean
    public MessageSource messageSource() {
    	ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:/messages/messages");
        //source.setUseCodeAsDefaultMessage(true);
        return source;
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());

	}
}
