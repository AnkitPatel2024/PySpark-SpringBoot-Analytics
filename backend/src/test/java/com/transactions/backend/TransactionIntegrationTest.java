package com.transactions.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.transactions.backend.entity.RollupGranularity;
import com.transactions.backend.entity.TransactionRollupEntity;
import com.transactions.backend.repository.TransactionRollupRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;

@SpringBootTest // Loads the full application context
@AutoConfigureMockMvc // Allows us to call the API
@Transactional // Rolls back database changes after each test
public class TransactionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRollupRepository repository;

    @Test
    public void testFullDailyFlow_DataExists() throws Exception {
        repository.save(
        TransactionRollupEntity.builder()
            .branch("New York")
            .granularity(RollupGranularity.DAILY)
            .txnDate(LocalDate.of(2024, 1, 10))
            .productType("FX")
            .totalAmount(new BigDecimal("1200000"))
            .txnCount(15L)
            .currency("USD")
            .build()
        );

        mockMvc.perform(get("/daily-rollups")
            .param("date", "2024-01-10")) 
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].branch").value("New York"))
            .andExpect(jsonPath("$[0].txnCount").value(15));;
    }

    @Test
    public void testDailyFlow_InvalidDate_Returns400() throws Exception {
        // Verifies Controller -> GlobalExceptionHandler wiring
        mockMvc.perform(get("/daily-rollups").param("date", "not-a-date"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Parameter Format"));
    }

    @Test
    public void testMonthlyFlow_NotFound_Returns404() throws Exception {
        // Verifies Service Exception -> GlobalExceptionHandler wiring
        // This assumes "1900-01" has no data
        mockMvc.perform(get("/monthly-rollups").param("yearMonth", "1900-01"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Data Not Found"));
    }
}
