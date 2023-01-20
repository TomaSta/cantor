package com.stasko.tomasz.cantor.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class FixerLatestResponse {
    private String base;

    private LocalDate date;

    private Map<String, Double> rates;

    public FixerLatestResponse() {
        rates = new HashMap<>();
    }
}
