package com.mercadolibre.fraudeapi.service;

import com.mercadolibre.fraudeapi.dto.rest.StatsResponse;
import com.mercadolibre.fraudeapi.persistence.model.CountryStat;
import com.mercadolibre.fraudeapi.persistence.repository.CountryStatRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Servicio encargado de manejar las estadísticas relacionadas con la distancia de países.
 */
@Service
@RequiredArgsConstructor
public class StatsService {

    private final CountryStatRepository repository;

    /**
     * Registra una nueva estadística de distancia para un país.
     *
     * @param countryName El nombre del país.
     * @param distance La distancia calculada desde Buenos Aires.
     * @return Mono<Void> que completa cuando la operación se ha realizado.
     */
    public Mono<Void> register(String countryName, Double distance) {
        return repository.findByCountry(countryName)
                .flatMap(countryStat -> Mono.empty()) // Si el país ya existe, no hace nada
                .switchIfEmpty(
                        repository.save(CountryStat.builder()
                                .country(countryName)
                                .distance(distance)
                                .requestDate(LocalDateTime.now()) // Fecha y hora actual
                                .build()
                        )
                ).then(); // El Mono<Void> se completa después de la operación
    }

    /**
     * Obtiene las estadísticas agregadas de todos los países, como el país más cercano,
     * el más lejano y la distancia promedio.
     *
     * @return Mono<StatsResponse> con las estadísticas agregadas.
     */
    public Mono<StatsResponse> getStats() {
        // Inicia un Flux con todos los stats
        Flux<CountryStat> statsFlux = repository.findAll();

        return statsFlux
                .reduce(
                        new StatsAccumulator(), // Estado inicial acumulador
                        StatsAccumulator::update // Actualiza el acumulador con cada CountryStat
                )
                .map(accumulator -> StatsResponse.builder()
                        .closestCountry(accumulator.getClosestCountry()) // País más cercano
                        .farthestCountry(accumulator.getFarthestCountry()) // País más lejano
                        .averageDistance(accumulator.getAverageDistance()) // Distancia promedio
                        .build());
    }

    /**
     * Clase interna para acumular las estadísticas sin necesidad de recolectar todos los elementos en memoria.
     */
    private static class StatsAccumulator {
        private double totalDistance = 0;
        private long count = 0;
        @Getter
        private String closestCountry = "N/A";
        @Getter
        private String farthestCountry = "N/A";
        private double closestDistance = Double.MAX_VALUE;
        private double farthestDistance = Double.MIN_VALUE;

        /**
         * Actualiza el acumulador con un nuevo CountryStat.
         *
         * @param stat El nuevo CountryStat.
         * @return El acumulador actualizado.
         */
        public StatsAccumulator update(CountryStat stat) {
            this.totalDistance += stat.getDistance(); // Suma la distancia total
            this.count++; // Incrementa el contador de elementos procesados

            // Actualiza el país más cercano
            if (stat.getDistance() < closestDistance) {
                closestDistance = stat.getDistance();
                closestCountry = stat.getCountry();
            }

            // Actualiza el país más lejano
            if (stat.getDistance() > farthestDistance) {
                farthestDistance = stat.getDistance();
                farthestCountry = stat.getCountry();
            }

            return this;
        }

        /**
         * Obtiene la distancia promedio de todos los registros procesados.
         *
         * @return La distancia promedio, o 0 si no se han procesado registros.
         */
        public double getAverageDistance() {
            return count == 0 ? 0 : totalDistance / count;
        }
    }
}
