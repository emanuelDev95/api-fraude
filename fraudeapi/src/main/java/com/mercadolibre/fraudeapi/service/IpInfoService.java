package com.mercadolibre.fraudeapi.service;

import com.mercadolibre.fraudeapi.dto.country_layer.CountryDetails;
import com.mercadolibre.fraudeapi.dto.exchage_rates.CurrencyDetails;
import com.mercadolibre.fraudeapi.dto.ip_api.IpInfo;
import com.mercadolibre.fraudeapi.dto.rest.IpInfoResponse;
import com.mercadolibre.fraudeapi.exceptions.NotFoundException;
import com.mercadolibre.fraudeapi.utils.HaversineUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IpInfoService {

    private static final double BUENOS_AIRES_LAT = -34.6037;
    private static final double BUENOS_AIRES_LON = -58.3816;
    private static final ZoneId UTC_ZONE = ZoneId.of("UTC");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final GeoLocationService geoService;
    private final CountryInfoService countryService;
    private final CurrencyService currencyService;
    private final StatsService statsService;

    /**
     * Obtiene la información de la IP .
     *
     * @param ip la dirección IP
     * @return Mono que encapsula la respuesta de la informacion de la IP.
     */
    public Mono<IpInfoResponse> getInfo(String ip) {
        return geoService.locateIp(ip)
                .flatMap(location -> {

                    if(Objects.nonNull(location.error())){
                        return Mono.error(new NotFoundException(location.error().info()));
                    }
                    double distance = calculateDistanceToBuenosAires(location);

                    // Obtener información del país y la moneda
                    return fetchCountryAndCurrencyInfo(location)
                            .map(tuple -> buildIpInfoResponse(ip, location, distance));
                });


    }

    /**
     * Calcula la distancia de la ubicación a Buenos Aires .
     *
     * @param location la información de la ubicación geográfica.
     * @return la distancia calculada.
     */
    private double calculateDistanceToBuenosAires(IpInfo location) {
        return HaversineUtils.calculate(
                BUENOS_AIRES_LAT, BUENOS_AIRES_LON,
                location.latitude(), location.longitude()
        );
    }

    /**
     * Obtiene los detalles del país y la tasa de cambio de la moneda.
     *
     * @param location la información geográfica de la IP.
     * @return un Mono que contiene un Tuple2 con los detalles del país y la moneda.
     */
    private Mono<Tuple2<CountryDetails, CurrencyDetails>> fetchCountryAndCurrencyInfo(IpInfo location) {
        return countryService.getCountryDetails(location.countryCode())
                .zipWith(currencyService.getConversionRate());
    }

    /**
     * Construye la respuesta de la IP.
     *
     * @param ip la dirección IP.
     * @param location la información geográfica de la IP.
     * @param distance la distancia calculada.
     * @return la respuesta estructurada de la IP.
     */
    private IpInfoResponse buildIpInfoResponse(String ip, IpInfo location, double distance) {
        statsService.register(location.countryName(), distance); // Registrar la estadística

        List<String> languages = location.location()
                .languages()
                .stream()
                .map(IpInfo.Language::name)
                .toList();

        return IpInfoResponse.builder()
                .ip(ip)
                .date(ZonedDateTime.now(UTC_ZONE).format(DATE_FORMATTER))
                .country(location.countryName())
                .isoCode(location.countryCode())
                .languages(languages)
                .distanceToBuenosAires(distance)
                .build();
    }


}
