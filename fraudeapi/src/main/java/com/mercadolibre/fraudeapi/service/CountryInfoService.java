package com.mercadolibre.fraudeapi.service;

import com.mercadolibre.fraudeapi.client.CountryInfoClient;
import com.mercadolibre.fraudeapi.dto.country_layer.CountryDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Servicio que proporciona información detallada sobre países utilizando el servicio externo CountryLayer API.
 */
@Service
@RequiredArgsConstructor
public class CountryInfoService {

    private final CountryInfoClient client;

    @Value("${api.country-layer.key}")
    private String countryKey;

    public Mono<CountryDetails> getCountryDetails(String code) {
        return client.getCountryDetails(code,countryKey);
    }
}

