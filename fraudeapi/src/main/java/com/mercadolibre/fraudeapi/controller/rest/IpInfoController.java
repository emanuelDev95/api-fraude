package com.mercadolibre.fraudeapi.controller.rest;

import com.mercadolibre.fraudeapi.dto.rest.IpInfoResponse;
import com.mercadolibre.fraudeapi.service.IpInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ip-info")
@RequiredArgsConstructor
public class IpInfoController {

    private final IpInfoService ipInfoService;

    @GetMapping("/{ip}")
    public ResponseEntity<Mono<IpInfoResponse>> getInfo(@PathVariable String ip) {
        return ResponseEntity.ok(ipInfoService.getInfo(ip));
    }
}
