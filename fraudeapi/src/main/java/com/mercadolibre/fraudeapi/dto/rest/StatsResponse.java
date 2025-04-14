package com.mercadolibre.fraudeapi.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {
    private String closestCountry;
    private String farthestCountry;
    private double averageDistance;
}
