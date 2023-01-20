package com.stasko.tomasz.cantor.repository;

import com.stasko.tomasz.cantor.model.CurrencySpreadPercentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencySpreadPercentageRepository extends JpaRepository<CurrencySpreadPercentage, Long> {
    @Query("SELECT entity FROM CurrencySpreadPercentage entity WHERE entity.symbol in :symbols")
    List<CurrencySpreadPercentage> findBySymbols(List symbols);
}
