package com.stasko.tomasz.cantor.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"date", "symbol"}),
        indexes = {
                @Index(columnList = "symbol,date")
        })
public class ServiceCallCounter {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 3)
    private String symbol;
    private LocalDate date;
    private Long counter;

    public ServiceCallCounter(String symbol, LocalDate date, Long counter) {
        this.symbol = symbol;
        this.date = date;
        this.counter = counter;
    }
}
