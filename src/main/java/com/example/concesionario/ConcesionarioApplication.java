package com.example.concesionario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class ConcesionarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcesionarioApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Concesionario API").version("0.0.1").description("API de concesionario"));
	}

}
