package com.mercadolibre.fraudeapi.dto.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class StatsResponseTest {

    @Test
    void testStatsResponse_Creation() {
        // Arrange
        String closestCountry = "Argentina";
        String farthestCountry = "Australia";
        double averageDistance = 12000.5;

        // Act
        StatsResponse statsResponse = StatsResponse.builder()
                .closestCountry(closestCountry)
                .farthestCountry(farthestCountry)
                .averageDistance(averageDistance)
                .build();

        // Assert
        assertEquals(closestCountry, statsResponse.getClosestCountry());
        assertEquals(farthestCountry, statsResponse.getFarthestCountry());
        assertEquals(averageDistance, statsResponse.getAverageDistance(), 0.001);
    }

    @Test
    void testStatsResponse_NullValues() {
        // Act
        StatsResponse statsResponse = new StatsResponse();

        // Assert
        assertNull(statsResponse.getClosestCountry());
        assertNull(statsResponse.getFarthestCountry());
        assertEquals(0.0, statsResponse.getAverageDistance(), 0.001);
    }
}
