package io.trackingsystem.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class GatewayConfig {

        // return builder.routes()
        // .route(p -> p
        // .path("/api/v1/ingestdata")
        // .uri("http://173.18.0.7:8000/"))
        // .route(p -> p
        // .path("/api/v1/sensors")
        // .uri("http://173.18.0.6:8002/"))
        // .build();

        @Bean
        public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
                return builder.routes()
                                .route(p -> p
                                                .path("/api/v1/ingestdata")
                                                .uri("http://sensor-ingest-service:8080/"))
                                .route(p -> p
                                                .path("/api/v1/sensors")
                                                .uri("http://sensor-manager-service:8080/"))
                                .build();
        }
}
