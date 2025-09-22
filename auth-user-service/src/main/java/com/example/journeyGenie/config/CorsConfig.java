//package com.example.journeyGenie.config;
//
//import com.example.journeyGenie.util.AppEnv;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("*") // More flexible than allowedOrigins
//                .allowedOrigins(
//                        "http://localhost:5173",
//                        "http://localhost:3000",
//                        "https://journey-genie-nu.vercel.app", // Add your exact Vercel URL
//                        AppEnv.getFrontendUrl()
//                )
//                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS")
//                .allowCredentials(true)
//                .allowedHeaders("*")
//                .exposedHeaders("Set-Cookie", "Authorization")
//                .maxAge(3600); // Cache preflight for 1 hour
//    }
//}