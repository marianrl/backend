package com.ams.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AmsApplication {
	private static final Logger logger = LoggerFactory.getLogger(AmsApplication.class);

	public static void main(String[] args) {
		try {
			logger.info("Starting AMS Application...");
			ConfigurableApplicationContext context = SpringApplication.run(AmsApplication.class, args);
			logger.info("AMS Application started successfully!");
		} catch (Exception e) {
			logger.error("Failed to start AMS Application", e);
			throw e;
		}
	}
}
