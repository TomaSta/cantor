package com.stasko.tomasz.cantor.controller;

import com.stasko.tomasz.cantor.model.ExchangeRateResponse;
import com.stasko.tomasz.cantor.repository.ServiceCallCounterRepository;
import com.stasko.tomasz.cantor.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;
    @Autowired
    private ServiceCallCounterRepository serviceCallCounterRepository;

    @GetMapping("/exchange")
    public ResponseEntity<ExchangeRateResponse> getExchange(@RequestParam Currency from,
                                                            @RequestParam Currency to,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date
    ) {
        incrementCounter(from.getCurrencyCode(), to.getCurrencyCode(), date);
        ExchangeRateResponse response = new ExchangeRateResponse(from,
                to,
                exchangeRateService.computeRate(from.getCurrencyCode(), to.getCurrencyCode(), date));
        return ResponseEntity.ok(response);
    }

    private CompletableFuture<Void> incrementCounter(String from, String to, Optional<LocalDate> date) {
        return CompletableFuture.runAsync(() -> {
            List<String> symbols = Arrays.asList(from, to);
            LocalDate localDate = date.isPresent() ? date.get() : LocalDate.now();
            serviceCallCounterRepository.updateCounterBySymbolAndDate(symbols, localDate);
        });
    }
}
