package com.transactions.backend.service;

import org.springframework.stereotype.Service;
import com.transactions.backend.dto.DailyRollupDTO;
import com.transactions.backend.dto.MonthlyRollupDTO;
import com.transactions.backend.entity.RollupGranularity;
import com.transactions.backend.entity.TransactionRollupEntity;
import com.transactions.backend.exception.DataNotFoundException;
import com.transactions.backend.repository.TransactionRollupRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionsService {

    public String getStatus(){
        return "Transactions Service is up and running!";
    }
    private final TransactionRollupRepository transactionsRepository;

    public TransactionsService(TransactionRollupRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public List<DailyRollupDTO> getDailyRollups(LocalDate date) {
        List<TransactionRollupEntity> entities;

        if (date == null) {
            // 1. If no date is passed, get all DAILY records
            entities = transactionsRepository.findByGranularity(RollupGranularity.DAILY);
        } else {
            // 2. If a date is passed, filter by DAILY and that specific date
            entities = transactionsRepository.findByGranularityAndTxnDate(RollupGranularity.DAILY, date);
        }
        if (entities.isEmpty()) {
                throw new DataNotFoundException("No transactions found for the requested criteria.");
            }
        return entities.stream()
                .map(entity -> new DailyRollupDTO(
                        entity.getBranch(),
                        entity.getTxnDate(),
                        entity.getProductType(),
                        entity.getTotalAmount(),
                        entity.getTxnCount(),
                        entity.getCurrency()
                        
                ))
                .toList();
    }

    public List<MonthlyRollupDTO> getMonthlyRollups(String yearMonth) {
    List<TransactionRollupEntity> entities;

        if (yearMonth == null || yearMonth.isEmpty()) {
            // 1. If no month is provided, get all MONTHLY records
            entities = transactionsRepository.findByGranularity(RollupGranularity.MONTHLY);
        } else {
            // 2. If yearMonth is provided (e.g., "2025-12"), filter specifically
            entities = transactionsRepository.findByGranularityAndYearMonth(RollupGranularity.MONTHLY, yearMonth);
        }
        if (entities.isEmpty()) {
                throw new DataNotFoundException("No transactions found for the requested criteria.");
            }
        return entities.stream()
                .map(entity -> new MonthlyRollupDTO(
                        entity.getBranch(),
                        entity.getYearMonth(),
                        entity.getProductType(),                        
                        entity.getTotalAmount(),
                        entity.getTxnCount(),
                        entity.getCurrency()
                ))
                .toList();
    }
    
}
