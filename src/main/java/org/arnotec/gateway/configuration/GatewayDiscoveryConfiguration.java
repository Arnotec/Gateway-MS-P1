package org.arnotec.gateway.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class GatewayDiscoveryConfiguration {


    /**
     *
     * Dynamically get the good Microservice to handle the request
     *
     * @param rdc
     * @param properties
     * @return
     */
    @Bean()
    public DiscoveryClientRouteDefinitionLocator customRouteDefinitionLocator(
            ReactiveDiscoveryClient rdc,
            DiscoveryLocatorProperties properties) {
        return new DiscoveryClientRouteDefinitionLocator(rdc, properties);
    }

    /**
     *
     * Static imports from RoutePredicates
     *
     * @param routeLocatorBuilder
     * @return
     */
    @Bean
    public RouteLocator staticRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route((r) -> r
                        .path("/covidAllCountries/**")
                        .filters(f -> f
                                .addRequestHeader("x-rapidapi-host", "corona-virus-world-and-india-data.p.rapidapi.com")
                                .addRequestHeader("x-rapidapi-key", "c6b9c85924mshd168bb089e7d351p15a9f3jsn515cbe6a60ad")
                                .rewritePath("/covidAllCountries/(?<segment>.*)", "/${segment}")
                                //.circuitBreaker(cb -> cb.setName("covidCountries").setFallbackUri("forward:/defaultCovidCountries"))
                        )
                        .uri("https://corona-virus-world-and-india-data.p.rapidapi.com"))

                .route((r) -> r
                        .path("/muslim/**")
                        .filters(f -> f
                                .addRequestHeader("x-rapidapi-host", "muslimsalat.p.rapidapi.com")
                                .addRequestHeader("x-rapidapi-key", "c6b9c85924mshd168bb089e7d351p15a9f3jsn515cbe6a60ad")
                                .rewritePath("/muslim/(?<segment>.*)", "/${segment}")
                                .circuitBreaker(cb -> cb.setName("muslimCountries").setFallbackUri("forward:/defaultCovidCountries"))
                        )
                        .uri("https://muslimsalat.p.rapidapi.com"))
                .build();
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> customizer(){
        return factory->factory.configureDefault(id-> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration
                                .ofSeconds(2))
                        .build())
                .build());
    }

}
