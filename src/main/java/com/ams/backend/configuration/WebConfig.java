package com.ams.backend.configuration;

import io.github.bucket4j.Bucket;
import org.springframework.lang.NonNull;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final Bucket bucket;

  public WebConfig(Bucket bucket) {
    this.bucket = bucket;
  }

  @Override
  public void addInterceptors(@NonNull InterceptorRegistry registry) {
    registry.addInterceptor(new RateLimitInterceptor(bucket))
        .addPathPatterns("/api/**"); // Apply to all API endpoints
  }
}