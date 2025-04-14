package com.mercadolibre.fraudeapi.dto.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class IpInfoResponseTest {

    @Test
    void testIpInfoResponse_Creation() {
        // Arrange
        String ip = "192.168.1.1";
        String date = "2025-04-13T00:00:00Z";
        String country = "Argentina";
        String isoCode = "AR";
        List<String> languages = Arrays.asList("Spanish", "English");
        String currency = "ARS";
        String conversionRate = "90.5";
        Double distanceToBuenosAires = 500.0;

        // Act
        IpInfoResponse ipInfoResponse = IpInfoResponse.builder()
                .ip(ip)
                .date(date)
                .country(country)
                .isoCode(isoCode)
                .languages(languages)
                .currency(currency)
                .conversionRate(conversionRate)
                .distanceToBuenosAires(distanceToBuenosAires)
                .build();

        // Assert
        assertEquals(ip, ipInfoResponse.getIp());
        assertEquals(date, ipInfoResponse.getDate());
        assertEquals(country, ipInfoResponse.getCountry());
        assertEquals(isoCode, ipInfoResponse.getIsoCode());
        assertEquals(languages, ipInfoResponse.getLanguages());
        assertEquals(currency, ipInfoResponse.getCurrency());
        assertEquals(conversionRate, ipInfoResponse.getConversionRate());
        assertEquals(distanceToBuenosAires, ipInfoResponse.getDistanceToBuenosAires());
    }

    @Test
    void testIpInfoResponse_NullValues() {
        // Act
        IpInfoResponse ipInfoResponse = new IpInfoResponse();

        // Assert
        assertNull(ipInfoResponse.getIp());
        assertNull(ipInfoResponse.getDate());
        assertNull(ipInfoResponse.getCountry());
        assertNull(ipInfoResponse.getIsoCode());
        assertNull(ipInfoResponse.getLanguages());
        assertNull(ipInfoResponse.getCurrency());
        assertNull(ipInfoResponse.getConversionRate());
        assertNull(ipInfoResponse.getDistanceToBuenosAires());
    }
}
