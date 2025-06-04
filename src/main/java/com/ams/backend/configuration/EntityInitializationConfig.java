package com.ams.backend.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import jakarta.persistence.Entity;
import java.util.Map;

@Configuration
public class EntityInitializationConfig implements InitializingBean {
  private static final Logger logger = LoggerFactory.getLogger(EntityInitializationConfig.class);

  @Autowired
  private ApplicationContext applicationContext;

  @Override
  public void afterPropertiesSet() throws Exception {
    logger.info("Checking entity initialization...");

    // Log all beans with @Entity annotation
    Map<String, Object> entityBeans = applicationContext.getBeansWithAnnotation(Entity.class);
    logger.info("Found {} beans with @Entity annotation", entityBeans.size());
    entityBeans.forEach((name, bean) -> {
      logger.info("Entity bean: {} of type {}", name, bean.getClass().getName());
    });

    logger.info("Entity initialization check completed");
  }

  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) {
    logger.info("Application context refreshed in EntityInitializationConfig");
  }
}