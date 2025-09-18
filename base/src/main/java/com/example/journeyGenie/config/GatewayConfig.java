package com.example.journeyGenie.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("tour-service", r -> r.path(
                        "/tour/**",
                                "/api/blog/**",
                                "/api/plan/**",
                                "/api/route/**"
                        )
                        .uri("http://localhost:8081"))
                .route("day-service", r -> r.path(
                        "/day/**"
                        )
                        .uri("http://localhost:8082"))
                .route("photo-service", r -> r.path(
                                "/photo/**",
                                "/api/image/**"
                        )
                        .uri("http://localhost:8083"))
                .route("activity-service", r -> r.path(
                                "/activity/**"
                        )
                        .uri("http://localhost:8084"))
                .route("landmark-detection-service", r -> r.path(
                                "/api/landmark/**"
                        )
                        .uri("http://localhost:8085"))
                .route("payment-service", r -> r.path(
                                "/payment/**"
                        )
                        .uri("http://localhost:8086"))
                .build();
    }
}
