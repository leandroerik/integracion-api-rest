package com.example.consumir_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ConsumirApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumirApiApplication.class, args);
	}

}
