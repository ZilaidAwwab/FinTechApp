package com.zilaidawwab.fintechapp;

import io.github.cdimascio.dotenv.Dotenv;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "FinTech App",
				description = "Backend REST API for FinTech App",
				version = "v1.0",
				contact = @Contact(
						name = "Zilaid Awwab",
						email = "zilaidawwab786@gmail.com",
						url = "https://github.com/ZilaidAwwab/FinTechApp"
				),
				license = @License(
						name = "FinTech Application",
						url = "https://github.com/ZilaidAwwab/FinTechApp"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Java FinTech Application Backend API Documentation",
				url = "https://github.com/ZilaidAwwab/FinTechApp"
		)
)
public class FinTechAppApplication {

	public static void main(String[] args) {
		// loading .env content into application context
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(FinTechAppApplication.class, args);
	}

}
