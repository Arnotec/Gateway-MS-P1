package org.arnotec.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfigurationBean {
    @Bean
    public RouteLocator RouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route((r) -> r.path("/customers/**").uri("lb://CUSTOMER-SERVICE/"))
                .route((r) -> r.path("/products/**").uri("lb://PRODUCT-SERVICE/"))
                .build();
    }

}
