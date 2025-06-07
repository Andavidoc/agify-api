/* package com.andres.agify_api.controller;

import com.andres.agify_api.model.AgifyResponse;
import com.andres.agify_api.service.AgifyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

@WebFluxTest(AgifyController.class)
class AgifyControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnAgePrediction() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/age")
                        .queryParam("name", "Michael")
                        .queryParam("countryId", "US").build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(AgifyResponse.class)
                .value(response -> {
                    assertThat(response.name()).isEqualTo("Michael");
                    assertThat(response.country_id()).isEqualTo("US");
                });
    }
} */