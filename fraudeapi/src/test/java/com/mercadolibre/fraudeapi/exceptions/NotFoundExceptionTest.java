package com.mercadolibre.fraudeapi.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class NotFoundExceptionTest {

    @Test
    void testNotFoundException() {
        // Assert
        assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException("User not found");
        });
    }
}
