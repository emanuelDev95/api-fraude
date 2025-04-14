package com.mercadolibre.fraudeapi.config;

import com.mercadolibre.fraudeapi.client.CountryInfoClient;
import com.mercadolibre.fraudeapi.client.CurrencyClient;
import com.mercadolibre.fraudeapi.client.GeoLocationClient;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    // Propiedades para las URLs base de cada cliente
    @Value("${api.ip-api.url}")
    private String ipApiBaseUrl;

    @Value("${api.country-layer.url}")
    private String countryInfoBaseUrl;

    @Value("${api.exchange-rates.url}")
    private String currencyBaseUrl;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        // Configuración común para todos los clientes
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(5))               // Timeout para respuesta
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)  // Timeout de conexión
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(5))   // Timeout de lectura
                        .addHandlerLast(new WriteTimeoutHandler(5))  // Timeout de escritura
                );

        return builder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }


    @Bean
    public GeoLocationClient geoLocationClient(WebClient webClient) {
        HttpServiceProxyFactory factory = createProxyFactory(webClient, ipApiBaseUrl);
        return factory.createClient(GeoLocationClient.class);
    }

    @Bean
    public CountryInfoClient countryInfoClient(WebClient webClient) {
        HttpServiceProxyFactory factory = createProxyFactory(webClient, countryInfoBaseUrl);
        return factory.createClient(CountryInfoClient.class);
    }

    @Bean
    public CurrencyClient currencyClient(WebClient webClient) {
        HttpServiceProxyFactory factory = createProxyFactory(webClient, currencyBaseUrl);
        return factory.createClient(CurrencyClient.class);
    }

    //  crea el proxy factory
    private HttpServiceProxyFactory createProxyFactory(WebClient webClient, String baseUrl) {
        WebClientAdapter adapter = WebClientAdapter.create(webClient.mutate().baseUrl(baseUrl).build());
        return HttpServiceProxyFactory.builder()
                .exchangeAdapter(adapter)
                .build();
    }

}
