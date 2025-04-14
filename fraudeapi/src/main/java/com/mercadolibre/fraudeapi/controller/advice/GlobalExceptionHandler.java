package com.mercadolibre.fraudeapi.controller.advice;

import com.mercadolibre.fraudeapi.dto.rest.ExceptionResponse;
import com.mercadolibre.fraudeapi.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ExceptionResponse> handleWebClientResponseException(WebClientResponseException ex) {
        var code = HttpStatus.valueOf(ex.getStatusCode().value());
        var response = new ExceptionResponse(code.value(), code.getReasonPhrase(), ex.getMessage());
        return ResponseEntity.status(code)
                .body(response);

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException ex) {
        var response = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
