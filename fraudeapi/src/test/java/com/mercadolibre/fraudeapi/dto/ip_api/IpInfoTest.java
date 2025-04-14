package com.mercadolibre.fraudeapi.dto.ip_api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IpInfoTest {

    @Test
    void testIpInfo_Creation() {
        // Arrange
        IpInfo.Language language = new IpInfo.Language("en", "English", "English");
        List<IpInfo.Language> languages = List.of(language);

        IpInfo.Location location = new IpInfo.Location(
                12345,
                "Capital City",
                languages,
                "flag.png",
                "🇺🇸",
                "U+1F1FA U+1F1F8",
                "+1",
                true
        );

        IpInfo.ErrorDetails errorDetails = new IpInfo.ErrorDetails(true, 200, "OK", "No issues");

        IpInfo ipInfo = new IpInfo(
                "192.168.0.1",
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
                location,
                errorDetails
        );

        // Act & Assert
        assertEquals("192.168.0.1", ipInfo.ip());
        assertEquals("IPv4", ipInfo.type());
        assertEquals("NA", ipInfo.continentCode());
        assertEquals("North America", ipInfo.continentName());
        assertEquals("US", ipInfo.countryCode());
        assertEquals("United States", ipInfo.countryName());
        assertEquals("CA", ipInfo.regionCode());
        assertEquals("California", ipInfo.regionName());
        assertEquals("San Francisco", ipInfo.city());
        assertEquals("94105", ipInfo.zip());
        assertEquals(37.7749, ipInfo.latitude());
        assertEquals(-122.4194, ipInfo.longitude());
        assertEquals("MSA123", ipInfo.msa());
        assertEquals("DMA123", ipInfo.dma());
        assertEquals(50.0, ipInfo.radius());
        assertEquals("RoutingType", ipInfo.ipRoutingType());
        assertEquals("Fiber", ipInfo.connectionType());

        // Testing nested Location
        assertEquals(12345, ipInfo.location().geonameId());
        assertEquals("Capital City", ipInfo.location().capital());
        assertEquals("flag.png", ipInfo.location().countryFlag());
        assertEquals("🇺🇸", ipInfo.location().countryFlagEmoji());
        assertEquals("U+1F1FA U+1F1F8", ipInfo.location().countryFlagEmojiUnicode());
        assertEquals("+1", ipInfo.location().callingCode());
        assertEquals(true, ipInfo.location().isEu());

        // Testing nested ErrorDetails
        assertTrue(ipInfo.error().success());
        assertEquals(200, ipInfo.error().code());
        assertEquals("OK", ipInfo.error().type());
        assertEquals("No issues", ipInfo.error().info());
    }

    @Test
    void testIpInfo_NullValues() {
        // Act
        IpInfo ipInfo = new IpInfo(
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        // Assert
        assertNull(ipInfo.ip());
        assertNull(ipInfo.type());
        assertNull(ipInfo.continentCode());
        assertNull(ipInfo.continentName());
        assertNull(ipInfo.countryCode());
        assertNull(ipInfo.countryName());
        assertNull(ipInfo.regionCode());
        assertNull(ipInfo.regionName());
        assertNull(ipInfo.city());
        assertNull(ipInfo.zip());
        assertNull(ipInfo.latitude());
        assertNull(ipInfo.longitude());
        assertNull(ipInfo.msa());
        assertNull(ipInfo.dma());
        assertNull(ipInfo.radius());
        assertNull(ipInfo.ipRoutingType());
        assertNull(ipInfo.connectionType());
        assertNull(ipInfo.location());
        assertNull(ipInfo.error());
    }
}
