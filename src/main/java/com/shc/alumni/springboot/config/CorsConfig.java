package com.shc.alumni.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "http://localhost:8080", 
                    "http://192.168.10.81:8080",// Backend itself (for testing)
                    "https://test.payu.in",       // PayU test environment
                    "https://apitest.payu.in",    // PayU API test
                    "https://*.ngrok-free.app"    // Ngrok wildcard (not used in this case but kept)
                )
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Methods")
                .allowCredentials(true)
                .maxAge(3600); // Cache preflight for 1 hour
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                    "/", "/register", "/",    // Allow login & register pages
                    "/css/**", "/js/**", "/images/**", "/assets/**", // Static resources
                    "/webjars/**",                   // WebJars
                    "/paymentSuccess", "/paymentFailure" // Exclude PayU callbacks from interceptor
                );
    }
}