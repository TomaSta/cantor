package com.stasko.tomasz.cantor.service;

import java.math.BigDecimal;
import java.math.MathContext;

public class MathFormulaHelper {
    public static BigDecimal calculateSpread(BigDecimal toCurrencySpread, BigDecimal fromCurrencySpread) {
        BigDecimal result = BigDecimal.valueOf(100).subtract(toCurrencySpread.max(fromCurrencySpread));
        return result.divide(BigDecimal.valueOf(100));
    }

    public static BigDecimal calculateRate(BigDecimal toCurrencyExchangeRateToBase, BigDecimal fromCurrencyExchangeRateToBase, BigDecimal spread) {
        MathContext mc = new MathContext(14);
        return toCurrencyExchangeRateToBase.divide(fromCurrencyExchangeRateToBase,mc).multiply(spread);
    }
}
