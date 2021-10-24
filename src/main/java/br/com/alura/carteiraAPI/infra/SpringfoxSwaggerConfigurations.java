package br.com.alura.carteiraAPI.infra;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringfoxSwaggerConfigurations {
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());
    }

	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "API Carteira de investimentos", 
	      "API de uma carteira de investimentos", 
	      "API TOS", 
	      "Terms of service", 
	      new Contact("Guilherme Henrique", "https://github.com/gui-lirasilva", "guilira.dev@gmail.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}
}
