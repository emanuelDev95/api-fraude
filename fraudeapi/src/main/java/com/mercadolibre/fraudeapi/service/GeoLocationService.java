package com.mercadolibre.fraudeapi.service;

import com.mercadolibre.fraudeapi.client.GeoLocationClient;
import com.mercadolibre.fraudeapi.dto.ip_api.IpInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Servicio que maneja la localización geográfica de una dirección IP utilizando el servicio externo IP API.
 */
@Service
@RequiredArgsConstructor
public class GeoLocationService {

    private final GeoLocationClient client;

    @Value("${api.ip-api.key}")
    private String ipApiKey;

    public Mono<IpInfo> locateIp(String ip) {
        return client.locateIp(ip, ipApiKey);
    }
}

