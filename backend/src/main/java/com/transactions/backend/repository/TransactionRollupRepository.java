package com.transactions.backend.repository;

import com.transactions.backend.entity.TransactionRollupEntity;
import com.transactions.backend.entity.RollupGranularity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRollupRepository
        extends JpaRepository<TransactionRollupEntity, Long> {

    // For "Show All"
    List<TransactionRollupEntity> findByGranularity(RollupGranularity granularity);

    // For specific filters
    List<TransactionRollupEntity> findByGranularityAndTxnDate(RollupGranularity g, LocalDate d);
    List<TransactionRollupEntity> findByGranularityAndYearMonth(RollupGranularity g, String ym);
}

