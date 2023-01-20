package com.stasko.tomasz.cantor.repository;

import com.stasko.tomasz.cantor.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Query("SELECT entity FROM ExchangeRate entity " +
            "WHERE entity.symbol in :symbols AND entity.date = COALESCE(:date, (SELECT max(e.date) FROM ExchangeRate e))")
    List<ExchangeRate> findBySymbolsAndDate(List symbols, Optional<LocalDate> date);
}
