package com.ams.backend.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

  @Bean
  public Bucket createBucket() {
    // Allow 300 requests per minute (5 requests per second)
    // This is a more reasonable limit for a small-to-medium application
    // while still protecting against abuse
    Bandwidth limit = Bandwidth.classic(300, Refill.intervally(300, Duration.ofMinutes(1)));

    // Add a burst capacity of additional 100 requests
    Bandwidth burst = Bandwidth.classic(100, Refill.intervally(100, Duration.ofMinutes(5)));

    return Bucket.builder()
        .addLimit(limit)
        .addLimit(burst)
        .build();
  }
}