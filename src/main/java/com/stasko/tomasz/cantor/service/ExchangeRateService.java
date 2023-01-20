package com.stasko.tomasz.cantor.service;

import com.stasko.tomasz.cantor.model.CurrencySpreadPercentage;
import com.stasko.tomasz.cantor.model.ExchangeRate;
import com.stasko.tomasz.cantor.repository.CurrencySpreadPercentageRepository;
import com.stasko.tomasz.cantor.repository.ExchangeRateRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRateService {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private CurrencySpreadPercentageRepository currencySpreadPercentageRepository;

    @SneakyThrows
    @Caching(cacheable = {
            @Cacheable(value = "historicalCache",
                    key = "#from+'-'+#to+'-'+#date",
                    unless = "#date.isEmpty()"),
            @Cacheable(value = "latestCache",
                    key = "#from+'-'+#to+'-'+#date",
                    condition = "#date.isEmpty() || T(java.time.LocalDate).now().isEqual(#date.get())")})
    public BigDecimal computeRate(String from, String to, Optional<LocalDate> date) {
        List<String> symbols = Arrays.asList(from, to);

        List<CurrencySpreadPercentage> spreadsList = currencySpreadPercentageRepository.findBySymbols(symbols);
        BigDecimal toCurrencySpread = getSpreadBySymbol(spreadsList, to);
        BigDecimal fromCurrencySpread = getSpreadBySymbol(spreadsList, from);
        BigDecimal spread = MathFormulaHelper.calculateSpread(toCurrencySpread, fromCurrencySpread);

        List<ExchangeRate> ratesList = exchangeRateRepository.findBySymbolsAndDate(symbols, date);
        BigDecimal toCurrencyExchangeRateToBase = getRateBySymbol(ratesList, to);
        BigDecimal fromCurrencyExchangeRateToBase = getRateBySymbol(ratesList, from);
        BigDecimal rate = MathFormulaHelper.calculateRate(toCurrencyExchangeRateToBase, fromCurrencyExchangeRateToBase, spread);

        return rate;
    }

    private BigDecimal getSpreadBySymbol(List<CurrencySpreadPercentage> spreads, String symbol) {
        return spreads.stream()
                .filter(e -> e.getSymbol().equals(symbol))
                .findFirst()
                .map(e -> e.getSpreadPercentage())
                .get();
    }

    private BigDecimal getRateBySymbol(List<ExchangeRate> rates, String symbol) {
        return rates.stream()
                .filter(e -> e.getSymbol().equals(symbol))
                .findFirst()
                .map(e -> e.getRate())
                .get();
    }
}

