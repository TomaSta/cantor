package com.stasko.tomasz.cantor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateResponse {
    private Currency from;
    private Currency to;
    private BigDecimal rate;
}
