package com.mercadolibre.fraudeapi.service;

import com.mercadolibre.fraudeapi.dto.country_layer.CountryDetails;
import com.mercadolibre.fraudeapi.dto.exchage_rates.CurrencyDetails;
import com.mercadolibre.fraudeapi.dto.ip_api.IpInfo;
import com.mercadolibre.fraudeapi.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class IpInfoServiceTest {

    @Mock
    private GeoLocationService geoLocationService;

    @Mock
    private CountryInfoService countryInfoService;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private StatsService statsService;

    @InjectMocks
    private IpInfoService ipInfoService;

    @Test
    void testGetInfoSuccess() {

        String ip = "83.44.196.93";

        IpInfo ipInfo = new IpInfo(
                ip, "ipv4", "EU", "Europe", "ES", "Spain",
                "CT", "Catalonia", "Vic", "08500", 41.93029022216797,
                2.254349946975708, null, null, 34.76264953613281,
                "fixed", "dsl",
                new IpInfo.Location(
                        3106050, "Madrid", Arrays.asList(
                        new IpInfo.Language("es", "Spanish", "Español"),
                        new IpInfo.Language("eu", "Basque", "Euskara"),
                        new IpInfo.Language("ca", "Catalan", "Català"),
                        new IpInfo.Language("gl", "Galician", "Galego"),
                        new IpInfo.Language("oc", "Occitan", "Occitan")
                ), "https://assets.ipstack.com/flags/es.svg",
                        "🇪🇸", "U+1F1EA U+1F1F8", "34", true
                ), null
        );


        List<String> topLevelDomain = List.of(".es");
        List<String> callingCodes = List.of("+34");
        List<String> altSpellings = Arrays.asList("ESP", "España");

        CountryDetails countryDetails = new CountryDetails(
                "Spain", topLevelDomain, "ES", "ESP", callingCodes,
                "Madrid", altSpellings, "Europe"
        );


        CurrencyDetails currencyDetails = new CurrencyDetails(
                true, 1617187200L, "EUR", "2021-03-31",
                Map.of("USD", 1.0, "EUR", 0.85)
        );


        when(geoLocationService.locateIp(ip)).thenReturn(Mono.just(ipInfo));
        when(countryInfoService.getCountryDetails("ES")).thenReturn(Mono.just(countryDetails));
        when(currencyService.getConversionRate()).thenReturn(Mono.just(currencyDetails));


        StepVerifier.create(ipInfoService.getInfo(ip))
                .expectNextMatches(response ->
                        response.getIp().equals(ip) &&
                                response.getCountry().equals("Spain") &&
                                response.getIsoCode().equals("ES") &&
                                response.getLanguages().contains("Spanish") &&
                                response.getDistanceToBuenosAires() >= 0
                )
                .verifyComplete();


        verify(statsService).register("Spain", 10512.734760208825d);
    }

    @Test
    void testGetInfoError() {

        String ip = "83.44.196.93";


        IpInfo ipInfoWithError = new IpInfo(
                ip, "ipv4", "EU", "Europe", "ES", "Spain",
                "CT", "Catalonia", "Vic", "08500", 41.93029022216797,
                2.254349946975708, null, null, 34.76264953613281,
                "fixed", "dsl",
                new IpInfo.Location(
                        3106050, "Madrid", Arrays.asList(
                        new IpInfo.Language("es", "Spanish", "Español"),
                        new IpInfo.Language("eu", "Basque", "Euskara"),
                        new IpInfo.Language("ca", "Catalan", "Català"),
                        new IpInfo.Language("gl", "Galician", "Galego"),
                        new IpInfo.Language("oc", "Occitan", "Occitan")
                ), "https://assets.ipstack.com/flags/es.svg",
                        "🇪🇸", "U+1F1EA U+1F1F8", "34", true
                ), new IpInfo.ErrorDetails(false, 404, "Not Found", "IP not found")
        );


        when(geoLocationService.locateIp(ip)).thenReturn(Mono.just(ipInfoWithError));


        StepVerifier.create(ipInfoService.getInfo(ip))
                .expectError(NotFoundException.class)
                .verify();


        verify(countryInfoService, never()).getCountryDetails(any());
        verify(currencyService, never()).getConversionRate();
    }
}
