package com.mercadolibre.fraudeapi.service;

import com.mercadolibre.fraudeapi.client.CurrencyClient;
import com.mercadolibre.fraudeapi.dto.exchage_rates.CurrencyDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class CurrencyServiceTest {

    @Mock
    private CurrencyClient currencyClient;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    void testGetConversionRate() {
        // Arrange
        CurrencyDetails currencyDetails = new CurrencyDetails(
                true,
                1617187200L,
                "USD",
                "2025-04-13",
                Map.of("ARS", 100.0)
        );

        // Simulando la respuesta del cliente
        when(currencyClient.getConversionRate(null)).thenReturn(Mono.just(currencyDetails));

        // Act & Assert
        StepVerifier.create(currencyService.getConversionRate())
                .expectNext(currencyDetails)
                .verifyComplete();
    }

    @Test
    void testGetConversionRate_WhenErrorOccurs() {


        when(currencyClient.getConversionRate(null)).thenReturn(Mono.error(new RuntimeException("Service error")));

        // Act & Assert
        StepVerifier.create(currencyService.getConversionRate())
                .expectError(RuntimeException.class)
                .verify();
    }
}
