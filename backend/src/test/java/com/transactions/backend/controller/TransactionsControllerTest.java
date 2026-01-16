package com.transactions.backend.controller;

import com.transactions.backend.dto.DailyRollupDTO;
import com.transactions.backend.dto.MonthlyRollupDTO;
import com.transactions.backend.exception.DataNotFoundException;
import com.transactions.backend.exception.ValidationException;
import com.transactions.backend.service.TransactionsService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@WebMvcTest(controllers = TransactionsController.class)
public class TransactionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionsService transactionsService;

    // ---------- Daily Rollups: Happy Path (All Data) ----------
    @Test
    public void testGetDailyRollups_noParams_success() throws Exception {
        List<DailyRollupDTO> mockDaily = List.of(
                new DailyRollupDTO("New York", LocalDate.of(2026, 1, 10), "FX", new BigDecimal(1200000), 15L, "USD")
        );

        // Update: Service now expects null if no param is passed
        when(transactionsService.getDailyRollups(null)).thenReturn(mockDaily);

        mockMvc.perform(get("/daily-rollups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].branch").value("New York"));
    }

    // ---------- Daily Rollups: Happy Path (Filtered) ----------
    @Test
    public void testGetDailyRollups_withParam_success() throws Exception {
        LocalDate testDate = LocalDate.of(2026, 1, 10);
        List<DailyRollupDTO> mockDaily = List.of(
                new DailyRollupDTO("New York", testDate, "FX", new BigDecimal(1200000), 15L, "USD")
        );

        // Update: Mock the specific date call
        when(transactionsService.getDailyRollups(testDate)).thenReturn(mockDaily);

        mockMvc.perform(get("/daily-rollups").param("date", "2026-01-10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].txnDate").value("2026-01-10"));
    }

    // ---------- New Test: Invalid Date Format (400) ----------
    @Test
    public void testGetDailyRollups_invalidFormat_returns400() throws Exception {
        // This hits the MethodArgumentTypeMismatchException handler we discussed!
        mockMvc.perform(get("/daily-rollups").param("date", "2026-1-31"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Parameter Format"));
    }

    // ---------- Monthly Rollups: Happy Path ----------
    @Test
    public void testGetMonthlyRollups_success() throws Exception {
        List<MonthlyRollupDTO> mockMonthly = List.of(
                new MonthlyRollupDTO("New York", "2026-01", "FX", new BigDecimal(32000000), 10L, "USD")       
        );

        // Update: Mock accepts null for all data
        when(transactionsService.getMonthlyRollups(null)).thenReturn(mockMonthly);

        mockMvc.perform(get("/monthly-rollups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].yearMonth").value("2026-01"));
    }

    // ---------- Exception Handling ----------
    @Test
    public void testDataNotFoundException() throws Exception {
        // Mocking the scenario where date is null but DB is empty
        when(transactionsService.getDailyRollups(null))
                .thenThrow(new DataNotFoundException("No daily data found"));

        mockMvc.perform(get("/daily-rollups"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Data Not Found"));
    }

    @Test
    public void testValidationException() throws Exception {
        when(transactionsService.getDailyRollups(null))
                .thenThrow(new ValidationException("Invalid date range"));

        mockMvc.perform(get("/daily-rollups"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"));
    }
}

