package com.mercadolibre.fraudeapi.dto.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class ExceptionResponseTest {

    @Test
    void testExceptionResponse_Creation() {
        // Arrange
        Integer statusCode = 404;
        String error = "Not Found";
        String message = "The requested resource could not be found";

        // Act
        ExceptionResponse exceptionResponse = new ExceptionResponse(statusCode, error, message);

        // Assert
        assertEquals(404, exceptionResponse.statusCode());
        assertEquals("Not Found", exceptionResponse.error());
        assertEquals("The requested resource could not be found", exceptionResponse.message());
    }

    @Test
    void testExceptionResponse_NullValues() {
        // Act
        ExceptionResponse exceptionResponse = new ExceptionResponse(null, null, null);

        // Assert
        assertNull(exceptionResponse.statusCode());
        assertNull(exceptionResponse.error());
        assertNull(exceptionResponse.message());
    }
}
