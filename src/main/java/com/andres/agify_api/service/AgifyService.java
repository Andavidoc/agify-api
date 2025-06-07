package com.andres.agify_api.service;

import com.andres.agify_api.model.AgifyResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AgifyService {

    private final WebClient webClient;

    public AgifyService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.agify.io").build();
    }

    public Mono<AgifyResponse> getPredictedAge(String name, String countryId) {
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.queryParam("name", name);
                    if (countryId != null && !countryId.isEmpty()) {
                        uriBuilder.queryParam("country_id", countryId);
                    }
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(AgifyResponse.class);
    }
}