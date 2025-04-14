package com.mercadolibre.fraudeapi.dto.exchage_rates;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class CurrencyDetailsTest {

    @Test
    void testCurrencyDetails_Creation() {
        // Arrange
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("EUR", 0.85);

        // Act
        CurrencyDetails currencyDetails = new CurrencyDetails(
                true,
                1633035600L,
                "USD",
                "2025-04-13",
                rates
        );

        // Assert
        assertEquals(true, currencyDetails.success());
        assertEquals(1633035600L, currencyDetails.timestamp());
        assertEquals("USD", currencyDetails.base());
        assertEquals("2025-04-13", currencyDetails.date());
        assertEquals(rates, currencyDetails.rates());
    }

    @Test
    void testCurrencyDetails_NullValues() {
        // Act
        CurrencyDetails currencyDetails = new CurrencyDetails(
                null, null, null, null, null
        );

        // Assert
        assertNull(currencyDetails.success());
        assertNull(currencyDetails.timestamp());
        assertNull(currencyDetails.base());
        assertNull(currencyDetails.date());
        assertNull(currencyDetails.rates());
    }

    @Test
    void testCurrencyDetails_EmptyRates() {
        // Arrange
        Map<String, Double> emptyRates = new HashMap<>();

        // Act
        CurrencyDetails currencyDetails = new CurrencyDetails(
                true,
                1633035600L,
                "USD",
                "2025-04-13",
                emptyRates
        );

        // Assert
        assertEquals(emptyRates, currencyDetails.rates());
    }
}
