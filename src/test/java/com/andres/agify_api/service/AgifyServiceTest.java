package com.andres.agify_api.service;

import com.andres.agify_api.model.AgifyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString; // Importa anyString()
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AgifyServiceTest {

    // Mocks que necesitarás
    private WebClient webClientMock;
    private WebClient.Builder webClientBuilderMock; // Mock para el Builder
    private AgifyService agifyService;

    // Mocks para la cadena de WebClient
    private RequestHeadersUriSpec requestHeadersUriSpecMock;
    private RequestHeadersSpec requestHeadersSpecMock;
    private ResponseSpec responseSpecMock;

    @BeforeEach // Este método se ejecuta antes de cada test
    void setUp() {
        // Inicializa el mock de WebClient
        webClientMock = mock(WebClient.class);
        // Inicializa el mock de WebClient.Builder
        webClientBuilderMock = mock(WebClient.Builder.class);

        // Define el comportamiento del mock de WebClient.Builder
        // Cuando se llama a .baseUrl() en webClientBuilderMock, devuelve webClientBuilderMock
        when(webClientBuilderMock.baseUrl(anyString())).thenReturn(webClientBuilderMock);
        // Cuando se llama a .build() en webClientBuilderMock, devuelve nuestro webClientMock
        when(webClientBuilderMock.build()).thenReturn(webClientMock);

        // Inicializa los mocks para la cadena de llamadas de WebClient
        requestHeadersUriSpecMock = mock(RequestHeadersUriSpec.class);
        requestHeadersSpecMock = mock(RequestHeadersSpec.class);
        responseSpecMock = mock(ResponseSpec.class);

        // Define el comportamiento del mock de WebClient
        // Cuando se llama a .get() en webClientMock, devuelve requestHeadersUriSpecMock
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        // Cuando se llama a .uri() en requestHeadersUriSpecMock con cualquier URI, devuelve requestHeadersSpecMock
        when(requestHeadersUriSpecMock.uri(any(java.util.function.Function.class))).thenReturn(requestHeadersSpecMock);
        // Cuando se llama a .retrieve() en requestHeadersSpecMock, devuelve responseSpecMock
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);

        // Finalmente, cuando se llama a .bodyToMono() en responseSpecMock, devuelve el Mono esperado
        // Esto simula la respuesta exitosa de la API externa
        when(responseSpecMock.bodyToMono(AgifyResponse.class)).thenReturn(Mono.just(new AgifyResponse("Michael", 62, 108496, "US")));

        // Instancia AgifyService inyectando el mock de WebClient.Builder
        agifyService = new AgifyService(webClientBuilderMock);
    }

    @Test
    void shouldReturnAgePrediction() {
        // La respuesta esperada que nuestro mock de WebClient simulará
        AgifyResponse expectedResponse = new AgifyResponse("Michael", 62, 108496, "US");

        // Llama al método del servicio, que internamente usará el webClientMock
        Mono<AgifyResponse> resultMono = agifyService.getPredictedAge("Michael", "US");

        // Bloquea el Mono para obtener el resultado y aserción
        assertThat(resultMono.block()).isEqualTo(expectedResponse);
    }
}
