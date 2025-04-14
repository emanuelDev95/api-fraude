package com.mercadolibre.fraudeapi.service;

import com.mercadolibre.fraudeapi.client.CurrencyClient;
import com.mercadolibre.fraudeapi.dto.exchage_rates.CurrencyDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Servicio que proporciona detalles sobre tasas de cambio de moneda utilizando el servicio externo Exchange Rates API.
 */
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyClient client;

    @Value("${api.exchange-rates.key}")
    private String key;

    public Mono<CurrencyDetails> getConversionRate() {
        return client.getConversionRate(key);
    }
}
