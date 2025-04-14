package com.mercadolibre.fraudeapi.client;

import com.mercadolibre.fraudeapi.dto.ip_api.IpInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

/**
 * Cliente para interactuar con el servicio de geolocalización de IPs.
 * Este cliente realiza peticiones HTTP para obtener la información geográfica
 * asociada a una dirección IP a través de un servicio externo.
 */
@HttpExchange
public interface GeoLocationClient {

    /**
     * Obtiene la información de la ubicación geográfica asociada a una dirección IP.
     *
     * @param ip La dirección IP a localizar.
     * @param accessKey La clave de acceso para la API del servicio de geolocalización.
     * @return Un Mono que encapsula la información de la ubicación geográfica asociada a la IP.
     */
    @GetExchange("/api/{ip}")
    Mono<IpInfo> locateIp(
            @PathVariable String ip,
            @RequestParam("access_key") String accessKey);
}




