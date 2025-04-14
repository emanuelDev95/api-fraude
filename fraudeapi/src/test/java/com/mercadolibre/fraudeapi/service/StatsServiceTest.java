package com.mercadolibre.fraudeapi.service;

import com.mercadolibre.fraudeapi.persistence.model.CountryStat;
import com.mercadolibre.fraudeapi.persistence.repository.CountryStatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private CountryStatRepository countryStatRepository;

    @InjectMocks
    private StatsService statsService;

    private static final String SPAIN = "Spain";

    @Test
    void testRegisterNewCountryStat() {
        String countryName = SPAIN;
        Double distance = 1234.56;

        CountryStat countryStat = CountryStat.builder()
                .country(countryName)
                .distance(distance)
                .requestDate(LocalDateTime.now())
                .build();

        when(countryStatRepository.findByCountry(countryName)).thenReturn(Mono.empty());
        when(countryStatRepository.save(any(CountryStat.class))).thenReturn(Mono.just(countryStat));

        StepVerifier.create(statsService.register(countryName, distance))
                .verifyComplete();

    }

    @Test
    void testGetStats() {
        CountryStat countryStat1 = CountryStat.builder().country(SPAIN).distance(500.0).build();
        CountryStat countryStat2 = CountryStat.builder().country("USA").distance(10000.0).build();
        CountryStat countryStat3 = CountryStat.builder().country("Argentina").distance(2000.0).build();

        when(countryStatRepository.findAll()).thenReturn(Flux.just(countryStat1, countryStat2, countryStat3));

        StepVerifier.create(statsService.getStats())
                .expectNextMatches(response ->
                        response.getClosestCountry().equals(SPAIN) &&
                                response.getFarthestCountry().equals("USA") &&
                                response.getAverageDistance() == 4166.666666666667
                )
                .verifyComplete();
    }
}
