package com.mercadolibre.fraudeapi.dto.country_layer;



import java.util.List;

public record CountryDetails(
    String name,
    List<String> topLevelDomain,
    String alpha2Code,
    String alpha3Code,
    List<String> callingCodes,
    String capital,
    List<String> altSpellings,
    String region
){}

