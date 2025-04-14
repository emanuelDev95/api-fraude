package com.mercadolibre.fraudeapi.dto.exchage_rates;

import java.util.Map;

public record CurrencyDetails(
        Boolean success,
        Long timestamp,
        String base,
        String date,
        Map<String, Double> rates
) {
}
