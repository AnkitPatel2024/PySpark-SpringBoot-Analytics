package com.transactions.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.transactions.backend.service.TransactionsService;
import com.transactions.backend.dto.DailyRollupDTO;
import com.transactions.backend.dto.MonthlyRollupDTO;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TransactionsController {

    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }
    
    @GetMapping("health")
    public String healthCheck() {
        return transactionsService.getStatus();
    }

    
    @GetMapping("/daily-rollups")
    public List<DailyRollupDTO> getDailyRollups(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return transactionsService.getDailyRollups(date);
    }  

    @GetMapping("/monthly-rollups")
    public List<MonthlyRollupDTO> getMonthlyRollups(
            @RequestParam(required = false) String yearMonth) {
        return transactionsService.getMonthlyRollups(yearMonth);
    }
}
