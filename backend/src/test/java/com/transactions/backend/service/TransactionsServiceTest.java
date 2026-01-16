package com.transactions.backend.service;

import com.transactions.backend.dto.DailyRollupDTO;
import com.transactions.backend.dto.MonthlyRollupDTO;
import com.transactions.backend.entity.RollupGranularity;
import com.transactions.backend.entity.TransactionRollupEntity;
import com.transactions.backend.exception.DataNotFoundException;
import com.transactions.backend.repository.TransactionRollupRepository;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionsServiceTest {

    private final TransactionRollupRepository repository = mock(TransactionRollupRepository.class);
    private final TransactionsService service = new TransactionsService(repository);

    // ---------- Daily Rollup Tests ----------

    @Test
    public void testGetDailyRollups_allData_success() {
        // 1. Arrange: Note the 'null' at the end for yearMonth
        TransactionRollupEntity entity = createMockEntity(RollupGranularity.DAILY, LocalDate.now(), null);
        
        when(repository.findByGranularity(RollupGranularity.DAILY)).thenReturn(List.of(entity));

        // 2. Act: Pass null to get all
        List<DailyRollupDTO> result = service.getDailyRollups(null);

        // 3. Assert
        assertFalse(result.isEmpty(), "Result should contain all daily records");
        assertEquals(1, result.size());
        // Verify that the repository's 'findAll' style method was the one actually called
        verify(repository, times(1)).findByGranularity(RollupGranularity.DAILY);
    }

    @Test
    public void testGetDailyRollups_withFilter_success() {
        // Arrange
        LocalDate targetDate = LocalDate.of(2026, 1, 10);
        TransactionRollupEntity entity = createMockEntity(RollupGranularity.DAILY, targetDate, null);
        when(repository.findByGranularityAndTxnDate(RollupGranularity.DAILY, targetDate))
                .thenReturn(List.of(entity));

        // Act
        List<DailyRollupDTO> result = service.getDailyRollups(targetDate);

        // Assert
        assertEquals(1, result.size());
        assertEquals(targetDate, result.get(0).getTxnDate());
    }

    @Test
    public void testGetDailyRollups_notFound_throwsException() {
        // Arrange: Return empty list
        when(repository.findByGranularity(RollupGranularity.DAILY)).thenReturn(Collections.emptyList());

        // Act & Assert: Verify the custom exception we discussed earlier is thrown
        assertThrows(DataNotFoundException.class, () -> {
            service.getDailyRollups(null);
        });
    }

// ---------- Monthly Rollup Tests ----------

    @Test
    public void testGetMonthlyRollups_withFilter_success() {
        // 1. Arrange
        String ym = "2026-01";
        // Pass the yearMonth directly to the helper to avoid setter issues
        TransactionRollupEntity entity = createMockEntity(RollupGranularity.MONTHLY, null, ym);
        
        when(repository.findByGranularityAndYearMonth(RollupGranularity.MONTHLY, ym))
                .thenReturn(List.of(entity));

        // 2. Act
        List<MonthlyRollupDTO> result = service.getMonthlyRollups(ym);

        // 3. Assert
        assertFalse(result.isEmpty(), "Result list should not be empty");
        assertEquals(ym, result.get(0).getYearMonth(), "DTO yearMonth should match input");
    }

    // UPDATED Helper method to include yearMonth
    private TransactionRollupEntity createMockEntity(RollupGranularity g, LocalDate date, String ym) {
        return TransactionRollupEntity.builder()
                .branch("London")
                .txnDate(date)
                .yearMonth(ym) // Added this field to the builder
                .productType("FX")
                .totalAmount(new BigDecimal("5000"))
                .granularity(g)
                .build();
    }
}

