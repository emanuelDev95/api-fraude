package com.mercadolibre.fraudeapi.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpInfoResponse {
    private String ip;                    // IP consultada
    private String date;                  // Fecha actual en formato UTC
    private String country;               // Nombre del país
    private String isoCode;              // Código ISO del país
    private List<String> languages;       // Idiomas del país
    private String currency;              // Moneda del país
    private String conversionRate;        // Tasa de conversión USD -> moneda local
    private Double distanceToBuenosAires; // Distancia en km a Buenos Aires
}


