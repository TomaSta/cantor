package com.stasko.tomasz.cantor.service;

import com.stasko.tomasz.cantor.model.ExchangeRate;
import com.stasko.tomasz.cantor.model.ServiceCallCounter;
import com.stasko.tomasz.cantor.repository.ExchangeRateRepository;
import com.stasko.tomasz.cantor.repository.ServiceCallCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExchangeRateUpdater {
    private final static String BASE_CURRENCY = "USD";
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private FixerApiService fixerApiService;
    @Autowired
    ExchangeRateRepository exchangeRateRepository;
    @Autowired
    ServiceCallCounterRepository serviceCallCounterRepository;

    @Scheduled(cron = "${fixer.rate.update.cron}", zone = "${fixer.rate.update.zone}")
    @Retryable(value = RuntimeException.class,
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}", multiplier = 2))
    public void updateRates() {
        List<ExchangeRate> rates = fixerApiService.getLatestRatesByBase(BASE_CURRENCY);
        exchangeRateRepository.saveAll(rates);
        insertNewServiceCallCounters(rates);
        clearLatestCache();
    }

    private void insertNewServiceCallCounters(List<ExchangeRate> rates) {
        List<ServiceCallCounter> newCounters = rates.stream()
                .map(e -> new ServiceCallCounter(e.getSymbol(), e.getDate(), Long.valueOf(1)))
                .toList();
        serviceCallCounterRepository.saveAll(newCounters);
    }

    private void clearLatestCache() {
        cacheManager.getCache("latestCache").clear();
    }
}
