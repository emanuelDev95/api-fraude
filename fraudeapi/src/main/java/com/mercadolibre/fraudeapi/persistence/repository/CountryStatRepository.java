package com.mercadolibre.fraudeapi.persistence.repository;


import com.mercadolibre.fraudeapi.persistence.model.CountryStat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CountryStatRepository extends ReactiveMongoRepository<CountryStat, String> {
    Mono<CountryStat> findByCountry(String country);
}

