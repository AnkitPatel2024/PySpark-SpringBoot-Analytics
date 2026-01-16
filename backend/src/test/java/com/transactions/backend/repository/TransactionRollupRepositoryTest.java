package com.transactions.backend.repository;

import com.transactions.backend.entity.TransactionRollupEntity;
import com.transactions.backend.entity.RollupGranularity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRollupRepositoryTest {

    @Autowired
    private TransactionRollupRepository repository;

    @Test
    void testFindByGranularity_returnsAllForThatType() {
        // Arrange: Save two different daily records
        repository.save(TransactionRollupEntity.builder()
                .branch("London").granularity(RollupGranularity.DAILY)
                .txnDate(LocalDate.of(2026, 1, 1))
                .yearMonth("2026-01") // Added to satisfy non-null constraints
                .productType("FX")
                .totalAmount(new BigDecimal("100.00"))
                .txnCount(15L)
                .currency("GBP").build());

        repository.save(TransactionRollupEntity.builder()
                .branch("Paris").granularity(RollupGranularity.DAILY)
                .txnDate(LocalDate.of(2026, 1, 2))
                .yearMonth("2026-01") // Added
                .productType("Equities")
                .totalAmount(new BigDecimal("200.00"))
                .txnCount(25L) 
                .currency("GBP").build());

        // Act
        List<TransactionRollupEntity> results = repository.findByGranularity(RollupGranularity.DAILY);

        // Assert
        assertEquals(2, results.size(), "Should return all records matching the granularity");
    }

    @Test
    void testFindDailyRollupsByDate() {
        LocalDate date = LocalDate.of(2024, 1, 10);
        // Arrange
        TransactionRollupEntity entity = TransactionRollupEntity.builder()
                .branch("New York")
                .granularity(RollupGranularity.DAILY)
                .txnDate(date)
                .yearMonth("2024-01") // Ensure this is present
                .productType("FX")
                .totalAmount(new BigDecimal("1200000"))
                .txnCount(150L)  
                .currency("USD")
                .build();
        repository.save(entity);

        // Act
        List<TransactionRollupEntity> results = repository.findByGranularityAndTxnDate(
                RollupGranularity.DAILY, date
        );

        // Assert
        assertEquals(1, results.size());
        assertEquals("New York", results.get(0).getBranch());
    }

    @Test
    void testFindMonthlyRollupsByMonth() {
        String ym = "2024-01";
        // Arrange
        TransactionRollupEntity entity = TransactionRollupEntity.builder()
                .branch("Tokyo")
                .granularity(RollupGranularity.MONTHLY)
                .yearMonth(ym)
                // txnDate can usually be null for monthly rollups, 
                // but check your Entity's @Column(nullable = false)
                .productType("Equities")
                .totalAmount(new BigDecimal("21500000"))
                .txnCount(152L)  
                .currency("JPY")
                .build();
        repository.save(entity);

        // Act
        List<TransactionRollupEntity> results = repository.findByGranularityAndYearMonth(
                RollupGranularity.MONTHLY, ym
        );

        // Assert
        assertEquals(1, results.size());
        assertEquals("Tokyo", results.get(0).getBranch());
    }
}


