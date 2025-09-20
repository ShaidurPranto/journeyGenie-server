package com.example.journeyGenie;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-user-service", r -> r.path(
                                "/user/**",
                                "/token/**"
                        )
                        .uri("lb://auth-user-service"))
                .route("tour-service", r -> r.path(
                        "/tour/**",
                                "/api/blog/**",
                                "/api/plan/**",
                                "/api/route/**",
                                "/api/weather/**"
                        )
                        .uri("lb://tour-service"))
                .route("day-service", r -> r.path(
                        "/day/**"
                        )
                        .uri("lb://day-service"))
                .route("activity-service", r -> r.path(
                                "/activity/**"
                        )
                        .uri("lb://activity-service"))
                .route("photo-service", r -> r.path(
                                "/photo/**",
                                "/api/image/**"
                        )
                        .uri("lb://photo-service"))
                .route("landmark-detection-service", r -> r.path(
                                "/api/landmark/**"
                        )
                        .uri("lb://landmark-detection-service"))
                .route("payment-service", r -> r.path(
                                "/payment/**"
                        )
                        .uri("lb://payment-service"))
                .build();
    }
}
