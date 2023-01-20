package com.stasko.tomasz.cantor.service;

import com.stasko.tomasz.cantor.model.ExchangeRate;
import com.stasko.tomasz.cantor.model.FixerLatestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FixerApiService {
    @Autowired
    private WebClient fixerWebClient;

    public List<ExchangeRate> getLatestRatesByBase(String baseCurrency) {
        FixerLatestResponse result = fixerWebClient.get()
                .uri("/latest?base={base}", baseCurrency)
                .retrieve()
                .bodyToMono(FixerLatestResponse.class)
                .onErrorReturn(new FixerLatestResponse())
                .block();

        return result.getRates().entrySet().stream()
                .map(e -> new ExchangeRate(result.getBase(), result.getDate(), e.getKey(), BigDecimal.valueOf(e.getValue())))
                .collect(Collectors.toList());
    }

}
