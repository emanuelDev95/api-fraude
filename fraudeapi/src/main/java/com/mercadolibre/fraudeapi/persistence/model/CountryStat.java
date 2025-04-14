package com.mercadolibre.fraudeapi.persistence.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("country_stats")
@Data
@Builder
public class CountryStat {
    @Id
    private String id;
    private String country;
    private Double distance;
    private LocalDateTime requestDate;
}
