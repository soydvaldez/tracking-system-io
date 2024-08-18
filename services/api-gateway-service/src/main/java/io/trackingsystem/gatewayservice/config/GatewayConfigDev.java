package io.trackingsystem.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class GatewayConfigDev {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/v1/ingestdata")
                        .uri("http://localhost:8000/"))
                .route(p -> p
                        .path("/api/v1/sensors")
                        .uri("http://localhost:8002/"))
                .route(p -> p
                        .path("/api/v1/notifies")
                        .uri("http://localhost:8004/"))
                .build();
    }
}
