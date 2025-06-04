package com.ams.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Map;

@SpringBootApplication
public class AmsApplication {
	private static final Logger logger = LoggerFactory.getLogger(AmsApplication.class);

	@Autowired
	private ApplicationContext applicationContext;

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

	@Bean
	public InitializingBean logBeanInitialization() {
		return () -> {
			logger.info("Checking bean initialization...");

			// Log all repositories
			Map<String, JpaRepository> repositories = applicationContext.getBeansOfType(JpaRepository.class);
			logger.info("Found {} JPA repositories", repositories.size());
			repositories.forEach((name, repo) -> logger.info("Repository: {}", name));

			// Log all beans with @Repository annotation
			Map<String, Object> repoBeans = applicationContext.getBeansWithAnnotation(Repository.class);
			logger.info("Found {} beans with @Repository annotation", repoBeans.size());
			repoBeans.forEach((name, bean) -> logger.info("Repository bean: {}", name));

			logger.info("Bean initialization check completed");
		};
	}

	@EventListener
	public void onApplicationStarted(ApplicationStartedEvent event) {
		logger.info("Application started event received");
	}
}
