package com.mercadolibre.fraudeapi.controller.advice;

import com.mercadolibre.fraudeapi.dto.rest.ExceptionResponse;
import com.mercadolibre.fraudeapi.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleWebClientResponseException() {
        // Arrange
        WebClientResponseException exception = mock(WebClientResponseException.class);
        // Simulamos el comportamiento del WebClientResponseException
        int statusCode = HttpStatus.BAD_REQUEST.value();
        String message = "Bad Request";
        when(exception.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        when(exception.getMessage()).thenReturn(message);

        // Act
        ResponseEntity<ExceptionResponse> response = globalExceptionHandler.handleWebClientResponseException(exception);

        // Assert
        ExceptionResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(statusCode, body.statusCode());
    }

    @Test
    void testHandleNotFoundException() {
        // Arrange
        String message = "Resource not found";
        NotFoundException exception = new NotFoundException(message);

        // Act
        ResponseEntity<ExceptionResponse> response = globalExceptionHandler.handleNotFoundException(exception);

        // Assert
        ExceptionResponse body = response.getBody();
        assert body != null;
        assertEquals(HttpStatus.NOT_FOUND.value(), body.statusCode());
        assertEquals(message, body.message());
    }
}
