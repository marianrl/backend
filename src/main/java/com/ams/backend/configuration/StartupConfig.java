package com.ams.backend.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class StartupConfig {
  private static final Logger logger = LoggerFactory.getLogger(StartupConfig.class);

  @Autowired
  private Environment environment;

  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) {
    logger.info("Application context refreshed");
    logger.info("Active profiles: {}", String.join(", ", environment.getActiveProfiles()));
    logger.info("Default profiles: {}", String.join(", ", environment.getDefaultProfiles()));
  }
}