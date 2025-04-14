package com.mercadolibre.fraudeapi.client;

import com.mercadolibre.fraudeapi.dto.country_layer.CountryDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

/**
 * Cliente para interactuar con el servicio de información de países.
 * Este cliente realiza peticiones HTTP para obtener los detalles de un país
 * a través del servicio externo Country Layer API.
 */
@HttpExchange
public interface CountryInfoClient {
    /**
     * Obtiene la información de el pais.
     *
     * @param code codigo de pais.
     * @param accessKey La clave de acceso para la API del servicio de informacion de paisaes.
     * @return Un Mono que encapsula la información de el pais.
     */
    @GetExchange("/alpha/{code}")
    Mono<CountryDetails> getCountryDetails(@PathVariable String code,
                                           @RequestParam("access_key") String accessKey);
}
