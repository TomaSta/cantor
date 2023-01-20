package com.stasko.tomasz.cantor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"symbol"}),
        indexes = {
                @Index(columnList = "symbol")
        })
public class CurrencySpreadPercentage {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 3)
    private String symbol;
    @Column(precision = 4, scale = 2)
    private BigDecimal spreadPercentage;
}
