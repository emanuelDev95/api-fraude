package com.mercadolibre.fraudeapi.service;

import com.mercadolibre.fraudeapi.client.GeoLocationClient;
import com.mercadolibre.fraudeapi.dto.ip_api.IpInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class GeoLocationServiceTest {

    @Mock
    private GeoLocationClient geoLocationClient;

    @InjectMocks
    private GeoLocationService geoLocationService;

    @Test
    void testLocateIp() {
        // Arrange
        String ip = "192.168.1.1";
        IpInfo ipInfo = new IpInfo(
                "192.168.1.1",
                "IPv4",
                "NA",
                "North America",
                "US",
                "United States",
                "CA",
                "California",
                "San Francisco",
                "94105",
                37.7749,
                -122.4194,
                "MSA123",
                "DMA123",
                50.0,
                "RoutingType",
                "Fiber",
                null, // location would be nested but can be omitted for this test
                null // error can be omitted for this test
        );

        when(geoLocationClient.locateIp(ip, null)).thenReturn(Mono.just(ipInfo));

        // Act & Assert
        StepVerifier.create(geoLocationService.locateIp(ip))
                .expectNext(ipInfo) // Verificamos que la respuesta emitida es la correcta
                .verifyComplete(); // Verificamos que la secuencia se complete sin errores
    }

    @Test
    void testLocateIp_WhenErrorOccurs() {
        // Arrange
        String ip = "192.168.1.1";

        when(geoLocationClient.locateIp(ip, null)).thenReturn(Mono.error(new RuntimeException("Service error")));

        // Act & Assert
        StepVerifier.create(geoLocationService.locateIp(ip))
                .expectError(RuntimeException.class) // Verificamos que se lanza el error esperado
                .verify();
    }
}
