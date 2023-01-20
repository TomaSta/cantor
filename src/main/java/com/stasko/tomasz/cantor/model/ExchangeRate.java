package com.stasko.tomasz.cantor.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"date", "symbol"}),
        indexes = {
                @Index(columnList = "symbol,date"),
                @Index(columnList = "date")
        })
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 3)
    private String base;
    private LocalDate date;
    @Column(length = 3)
    private String symbol;
    @Column(precision = 14, scale = 6)
    private BigDecimal rate;

    public ExchangeRate(String base, LocalDate date, String symbol, BigDecimal rate) {
        this.base = base;
        this.date = date;
        this.symbol = symbol;
        this.rate = rate;
    }
}
