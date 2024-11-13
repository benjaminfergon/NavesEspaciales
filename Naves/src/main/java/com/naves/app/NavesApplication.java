package com.naves.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // Habilitar el uso de caché
public class NavesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NavesApplication.class, args);
	}

}
