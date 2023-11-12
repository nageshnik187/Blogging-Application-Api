package com.blog.config;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;

import com.blog.BloggingApplication;

import io.jsonwebtoken.lang.Collections;
import springfox.documentation.spi.service.contexts.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER="Authorization";
	
	public ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header"); 
	}
	
	private List<springfox.documentation.spi.service.contexts.SecurityContext> securityContexts(){
		
		return Arrays.asList(springfox.documentation.spi.service.contexts.SecurityContext.builder().securityReferences(sf()).build());
	}
	
	
	private List<SecurityReference> sf() {
		
		AuthorizationScope scope = new springfox.documentation.service.AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("JWT	", new AuthorizationScope[] {scope})); 
	}
	
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(null)
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	private ApiInfo getInfo() {
		return new ApiInfo("BloggingApplication", "This Project is Developed By Nagesh Nikavade", "1.0", "Terms & Conditions", 
				new Contact("Nagesh Nikavade", "https://nageshblogapplication", "nagesh.nikavade@gmail.com"), 
				"Licence Of API Public", "API Licence URL", java.util.Collections.EMPTY_LIST);
	}
	
	
	
}
