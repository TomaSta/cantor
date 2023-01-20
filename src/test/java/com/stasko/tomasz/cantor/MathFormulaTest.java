package com.stasko.tomasz.cantor;


import com.stasko.tomasz.cantor.service.MathFormulaHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


public class MathFormulaTest {

    @Test
    public void testCalculateSpreadWithPositiveValues() {
        BigDecimal toCurrencySpread = new BigDecimal("1");
        BigDecimal fromCurrencySpread = new BigDecimal("4");
        BigDecimal expectedResult = new BigDecimal("0.96");

        BigDecimal result = MathFormulaHelper.calculateSpread(toCurrencySpread, fromCurrencySpread);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void testCalculateSpreadWithEqualValues() {
        BigDecimal toCurrencySpread = new BigDecimal("1.5");
        BigDecimal fromCurrencySpread = new BigDecimal("1.5");
        BigDecimal expectedResult = new BigDecimal("0.985");

        BigDecimal result = MathFormulaHelper.calculateSpread(toCurrencySpread, fromCurrencySpread);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void testCalculateSpreadWithZeroValues() {
        BigDecimal toCurrencySpread = new BigDecimal("0.0");
        BigDecimal fromCurrencySpread = new BigDecimal("0.0");
        BigDecimal expectedResult = new BigDecimal("1.0");

        BigDecimal result = MathFormulaHelper.calculateSpread(toCurrencySpread, fromCurrencySpread);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void testCalculateRateWithPositiveValues() {
        BigDecimal toCurrencyExchangeRateToBase = new BigDecimal("3.7");
        BigDecimal fromCurrencyExchangeRateToBase = new BigDecimal("0.8");
        BigDecimal spread = new BigDecimal("0.96");
        BigDecimal expectedResult = new BigDecimal("4.44000");

        BigDecimal result = MathFormulaHelper.calculateRate(toCurrencyExchangeRateToBase, fromCurrencyExchangeRateToBase, spread);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void testCalculateRateWithEqualValues() {
        BigDecimal toCurrencyExchangeRateToBase = new BigDecimal("2.0");
        BigDecimal fromCurrencyExchangeRateToBase = new BigDecimal("2.0");
        BigDecimal spread = new BigDecimal("0.1");
        BigDecimal expectedResult = new BigDecimal("0.1");

        BigDecimal result = MathFormulaHelper.calculateRate(toCurrencyExchangeRateToBase, fromCurrencyExchangeRateToBase, spread);

        Assertions.assertEquals(expectedResult, result);
    }
}
