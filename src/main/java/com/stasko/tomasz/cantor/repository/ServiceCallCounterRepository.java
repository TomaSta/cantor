package com.stasko.tomasz.cantor.repository;

import com.stasko.tomasz.cantor.model.ServiceCallCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServiceCallCounterRepository extends JpaRepository<ServiceCallCounter, Long> {
    @Transactional
    @Modifying
    @Query("update ServiceCallCounter s set s.counter = s.counter+1 where s.symbol in :symbols and s.date = :date")
    void updateCounterBySymbolAndDate(List<String> symbols, LocalDate date);

}
