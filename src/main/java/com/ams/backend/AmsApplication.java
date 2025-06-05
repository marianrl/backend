package com.ams.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.ams.backend.entity")
@EnableJpaRepositories("com.ams.backend.repository")
public class AmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(AmsApplication.class, args);
	}
}
