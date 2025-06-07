package com.andres.agify_api.controller;

import com.andres.agify_api.model.AgifyResponse;
import com.andres.agify_api.service.AgifyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean; // Importar MockBean
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono; // Importar Mono

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString; // Importar anyString
import static org.mockito.Mockito.when; // Importar when

@WebFluxTest(AgifyController.class)
class AgifyControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    // Usamos @MockBean para que Spring Boot inyecte un mock de AgifyService
    // en el contexto del test, y el controlador use este mock.
    @MockBean
    private AgifyService agifyService;

    @Test
    void shouldReturnAgePrediction() {
        // Creamos una respuesta mock para simular lo que devolvería AgifyService
        AgifyResponse mockResponse = new AgifyResponse("Michael", 62, 108496, "US");

        // Configuramos el comportamiento del mock de AgifyService
        // Cuando se llame a getPredictedAge con cualquier string para 'name' y 'countryId',
        // devuelve un Mono con nuestra mockResponse.
        when(agifyService.getPredictedAge(anyString(), anyString()))
                .thenReturn(Mono.just(mockResponse));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/age")
                        .queryParam("name", "Michael")
                        .queryParam("countryId", "US")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(AgifyResponse.class)
                .value(response -> {
                    // Asegúrate de que las aserciones coincidan con los datos de tu mockResponse
                    assertThat(response.name()).isEqualTo("Michael");
                    assertThat(response.age()).isEqualTo(62); // Asegúrate de testear la edad
                    assertThat(response.count()).isEqualTo(108496); // Asegúrate de testear el conteo
                    assertThat(response.country_id()).isEqualTo("US");
                });
    }

    @Test
    void shouldReturnAgePredictionWithoutCountryId() {
        // Creamos una respuesta mock para simular lo que devolvería AgifyService
        AgifyResponse mockResponse = new AgifyResponse("Maria", 30, 50000, null);

        // Configuramos el comportamiento del mock de AgifyService para el caso sin countryId
        when(agifyService.getPredictedAge(anyString(), Mockito.eq(null))) // Mockear cuando countryId es null
                .thenReturn(Mono.just(mockResponse));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/age")
                        .queryParam("name", "Maria")
                        .build()) // No incluir countryId
                .exchange()
                .expectStatus().isOk()
                .expectBody(AgifyResponse.class)
                .value(response -> {
                    assertThat(response.name()).isEqualTo("Maria");
                    assertThat(response.age()).isEqualTo(30);
                    assertThat(response.count()).isEqualTo(50000);
                    assertThat(response.country_id()).isNull(); // Debe ser null
                });
    }
}
