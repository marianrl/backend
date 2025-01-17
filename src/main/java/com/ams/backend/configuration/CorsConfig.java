package com.ams.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000"); // Origen de tu frontend
        corsConfiguration.addAllowedHeader("*"); // Permitir todos los encabezados
        corsConfiguration.addAllowedMethod("*"); // Permitir todos los métodos HTTP (GET, POST, PUT, DELETE)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // Aplicar a todos los endpoints

        return new CorsFilter(source);
    }
}
