package com.example.employee_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Izinkan akses dari mana saja (frontend HTML)
        config.addAllowedOriginPattern("*");

        // Izinkan semua method HTTP
        config.addAllowedMethod("*");

        // Izinkan semua header termasuk Authorization
        config.addAllowedHeader("*");

        // Izinkan credentials (cookie/session)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}