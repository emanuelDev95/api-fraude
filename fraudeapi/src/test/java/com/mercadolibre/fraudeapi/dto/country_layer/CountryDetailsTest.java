package com.mercadolibre.fraudeapi.dto.country_layer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class CountryDetailsTest {

    @Test
    void testCountryDetails_Creation() {
        // Arrange
        List<String> topLevelDomain = Arrays.asList(".us", ".com");
        List<String> callingCodes = List.of("+1");
        List<String> altSpellings = Arrays.asList("USA", "America");

        // Act
        CountryDetails countryDetails = new CountryDetails(
                "United States",
                topLevelDomain,
                "US",
                "USA",
                callingCodes,
                "Washington, D.C.",
                altSpellings,
                "Americas"
        );

        // Assert
        assertEquals("United States", countryDetails.name());
        assertEquals(topLevelDomain, countryDetails.topLevelDomain());
        assertEquals("US", countryDetails.alpha2Code());
        assertEquals("USA", countryDetails.alpha3Code());
        assertEquals(callingCodes, countryDetails.callingCodes());
        assertEquals("Washington, D.C.", countryDetails.capital());
        assertEquals(altSpellings, countryDetails.altSpellings());
        assertEquals("Americas", countryDetails.region());
    }

    @Test
    void testCountryDetails_NullValues() {
        // Act
        CountryDetails countryDetails = new CountryDetails(
                null, null, null, null, null, null, null, null
        );

        // Assert
        assertNull(countryDetails.name());
        assertNull(countryDetails.topLevelDomain());
        assertNull(countryDetails.alpha2Code());
        assertNull(countryDetails.alpha3Code());
        assertNull(countryDetails.callingCodes());
        assertNull(countryDetails.capital());
        assertNull(countryDetails.altSpellings());
        assertNull(countryDetails.region());
    }
}
