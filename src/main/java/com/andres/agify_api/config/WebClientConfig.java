package com.andres.agify_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {
    @Bean
    public WebClient agifyClient(WebClient.Builder builder) {
        return builder.baseUrl("https://api.agify.io").build();
    }
}