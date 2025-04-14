package com.mercadolibre.fraudeapi.dto.rest;

public record ExceptionResponse(
        Integer statusCode,
        String error,
        String message
) {
}