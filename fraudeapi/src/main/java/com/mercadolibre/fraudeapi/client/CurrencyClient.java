package com.mercadolibre.fraudeapi.client;

import com.mercadolibre.fraudeapi.dto.exchage_rates.CurrencyDetails;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

/**
 * Cliente para interactuar con el servicio de tasas de cambio de divisas.
 * Este cliente realiza peticiones HTTP para obtener la tasa de cambio
 * a través del servicio externo de Exchange Rates API.
 */
@HttpExchange
public interface CurrencyClient {
    /**
     * Obtiene la información de la conversion de divisas.
     *
     * @param accessKey La clave de acceso para la API del servicio de servicio de tasas de cambio de divisas .
     * @return Un Mono que encapsula la información de la divisa.
     */
    @GetExchange("/latest")
    Mono<CurrencyDetails> getConversionRate(@RequestParam("access_key")String accessKey);
}

