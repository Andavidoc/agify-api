// src/main/java/com/andres/agifyapi/service/AgifyService.java
package com.andres.agify_api.service;

import com.andres.agify_api.model.AgifyResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AgifyService {
    private final WebClient client;

    public AgifyService(WebClient agifyClient) {
        this.client = agifyClient;
    }

    public Mono<AgifyResponse> getPredictedAge(String name) {
        return client.get()
            .uri(uri -> uri.queryParam("name", name).build())
            .retrieve()
            .bodyToMono(AgifyResponse.class);
    }
}