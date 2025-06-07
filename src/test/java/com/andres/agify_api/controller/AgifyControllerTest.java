package com.andres.agify_api.controller;

import com.andres.agify_api.model.AgifyResponse;
import com.andres.agify_api.service.AgifyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import org.springframework.test.context.TestPropertySource; // Nueva importación

@WebFluxTest(AgifyController.class)
// Se añade @TestPropertySource para excluir auto-configuraciones comunes que pueden causar fallos en el contexto
// Ajusta estas exclusiones según las dependencias reales de tu aplicación si el problema persiste.
@TestPropertySource(properties = {
    "spring.autoconfigure.exclude=" +
    "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," + // Excluye configuración de DataSource
    "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration," + // Excluye configuración de JPA/Hibernate
    "org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration" // Excluye Spring Security Reactivo si lo usas
})
class AgifyControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AgifyService agifyService;

    @Test
    void shouldReturnAgePrediction() {
        AgifyResponse mockResponse = new AgifyResponse("Michael", 62, 108496, "US");

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
                    assertThat(response.name()).isEqualTo("Michael");
                    assertThat(response.age()).isEqualTo(62);
                    assertThat(response.count()).isEqualTo(108496);
                    assertThat(response.country_id()).isEqualTo("US");
                });
    }

    @Test
    void shouldReturnAgePredictionWithoutCountryId() {
        AgifyResponse mockResponse = new AgifyResponse("Maria", 30, 50000, null);

        when(agifyService.getPredictedAge(anyString(), Mockito.eq(null)))
                .thenReturn(Mono.just(mockResponse));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/age")
                        .queryParam("name", "Maria")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(AgifyResponse.class)
                .value(response -> {
                    assertThat(response.name()).isEqualTo("Maria");
                    assertThat(response.age()).isEqualTo(30);
                    assertThat(response.count()).isEqualTo(50000);
                    assertThat(response.country_id()).isNull();
                });
    }
}
