package com.ams.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class AmsApplication {
	private static final Logger logger = LoggerFactory.getLogger(AmsApplication.class);

	public static void main(String[] args) {
		try {
			logger.info("Starting AMS Application...");
			SpringApplication app = new SpringApplication(AmsApplication.class);
			app.setLogStartupInfo(true);
			ConfigurableApplicationContext context = app.run(args);
			logger.info("AMS Application started successfully!");
		} catch (Exception e) {
			logger.error("Failed to start AMS Application. Error: {}", e.getMessage(), e);
			throw e;
		}
	}

	@EventListener
	public void onApplicationStarted(ApplicationStartedEvent event) {
		logger.info("Application started event received");
	}
}
