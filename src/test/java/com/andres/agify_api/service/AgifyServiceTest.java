package com.andres.agify_api.service;

import com.andres.agify_api.model.AgifyResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.Assertions.assertThat;

class AgifyServiceTest {

    private final WebClient webClient = Mockito.mock(WebClient.class);
    private final AgifyService agifyService = new AgifyService(WebClient.builder());

    @Test
    void shouldReturnAgePrediction() {
        AgifyResponse mockResponse = new AgifyResponse("Michael", 62, 108496, "US");

        Mockito.when(agifyService.getPredictedAge("Michael", "US"))
                .thenReturn(Mono.just(mockResponse));

        Mono<AgifyResponse> result = agifyService.getPredictedAge("Michael", "US");

        assertThat(result.block()).isEqualTo(mockResponse);
    }
}