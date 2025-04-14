package com.mercadolibre.fraudeapi.service;

import com.mercadolibre.fraudeapi.client.CountryInfoClient;
import com.mercadolibre.fraudeapi.dto.country_layer.CountryDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class CountryInfoServiceTest {

    @Mock
    private CountryInfoClient countryInfoClient;

    @InjectMocks
    private CountryInfoService countryInfoService;


    @Test
    void testGetCountryDetails() {
        // Arrange
        String countryCode = "US";
        CountryDetails countryDetails = new CountryDetails(
                "United States",
                null,
                "US",
                "USA",
                null,
                "Washington, D.C.",
                null,
                "Americas"
        );
        when(countryInfoClient.getCountryDetails(countryCode, null))
                .thenReturn(Mono.just(countryDetails));

        // Act & Assert
        StepVerifier.create(countryInfoService.getCountryDetails(countryCode))
                .expectNext(countryDetails)
                .verifyComplete();
    }
}
