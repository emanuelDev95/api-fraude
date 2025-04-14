package com.mercadolibre.fraudeapi.controller.rest;

import com.mercadolibre.fraudeapi.dto.rest.StatsResponse;
import com.mercadolibre.fraudeapi.service.StatsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsControllerTest {

    @Mock
    private StatsService statsService;

    @InjectMocks
    private StatsController statsController;

    @Test
    void testGetStats_Success() {
        // Arrange
        StatsResponse expectedResponse = new StatsResponse();
        expectedResponse.setAverageDistance(4.00);
        expectedResponse.setClosestCountry("Europe");
        expectedResponse.setFarthestCountry("America");

        when(statsService.getStats()).thenReturn(Mono.just(expectedResponse));

        // Act
        ResponseEntity<Mono<StatsResponse>> result = statsController.getStats();

        // Assert
        StepVerifier.create(Objects.requireNonNull(result.getBody()))
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    void testGetStats_Empty() {
        // Arrange
        when(statsService.getStats()).thenReturn(Mono.empty());

        // Act
        ResponseEntity<Mono<StatsResponse>> result = statsController.getStats();

        // Assert
        StepVerifier.create(Objects.requireNonNull(result.getBody()))
                .expectComplete()  // Verifica que el Mono se complete sin emitir valores
                .verify();
    }
}
