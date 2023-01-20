package com.stasko.tomasz.cantor.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
@EnableRetry
public class FixerApiConfiguration {
    @Value("${fixer.api.key}")
    private String apiKey;

    @Bean
    public WebClient getFixerWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("https://api.apilayer.com/fixer")
                .defaultHeader("apikey", apiKey)
                .build();
    }
}
