package com.mercadolibre.fraudeapi.controller.rest;

import com.mercadolibre.fraudeapi.dto.rest.StatsResponse;
import com.mercadolibre.fraudeapi.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public ResponseEntity<Mono<StatsResponse>> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}
