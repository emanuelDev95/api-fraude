package com.mercadolibre.fraudeapi.exceptions;

public class NotFoundException  extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
