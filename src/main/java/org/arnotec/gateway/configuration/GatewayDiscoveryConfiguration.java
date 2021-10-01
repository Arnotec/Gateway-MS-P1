package org.arnotec.gateway.configuration;

import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayDiscoveryConfiguration {

    @Bean()
    public DiscoveryClientRouteDefinitionLocator customRouteDefinitionLocator(
            ReactiveDiscoveryClient rdc,
            DiscoveryLocatorProperties properties) {
        return new DiscoveryClientRouteDefinitionLocator(rdc, properties);
    }

}
