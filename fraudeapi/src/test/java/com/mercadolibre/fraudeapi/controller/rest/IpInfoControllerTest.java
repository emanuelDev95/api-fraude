package com.mercadolibre.fraudeapi.controller.rest;

import com.mercadolibre.fraudeapi.dto.rest.IpInfoResponse;
import com.mercadolibre.fraudeapi.service.IpInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IpInfoControllerTest {

    @Mock
    private IpInfoService ipInfoService;

    @InjectMocks
    private IpInfoController ipInfoController;

    @Test
    void testGetInfo_ValidIp() {
        // Arrange
        String ip = "192.168.1.1";
        IpInfoResponse expectedResponse = new IpInfoResponse();
        expectedResponse.setIp(ip);
        expectedResponse.setCountry("Colombia");

        when(ipInfoService.getInfo(ip)).thenReturn(Mono.just(expectedResponse));

        // Act
        ResponseEntity<Mono<IpInfoResponse>> result = ipInfoController.getInfo(ip);

        // Assert
        StepVerifier.create(Objects.requireNonNull(result.getBody()))
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    void testGetInfo_InvalidIp() {
        // Arrange
        String ip = "invalid-ip";
        when(ipInfoService.getInfo(ip)).thenReturn(Mono.empty());

        // Act
        ResponseEntity<Mono<IpInfoResponse>> result = ipInfoController.getInfo(ip);

        // Assert
        StepVerifier.create(Objects.requireNonNull(result.getBody()))
                .expectComplete()
                .verify();
    }
}
