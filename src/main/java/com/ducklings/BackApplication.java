package com.ducklings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class BackApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}
}
