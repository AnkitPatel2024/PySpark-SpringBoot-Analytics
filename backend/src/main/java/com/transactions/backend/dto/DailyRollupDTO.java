package com.transactions.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailyRollupDTO {
    private String branch;
    private LocalDate txnDate;
    private String productType;
    private BigDecimal totalAmount;
    private Long txnCount;
    private String currency;

    public DailyRollupDTO(String branch, LocalDate txnDate, String productType, BigDecimal totalAmount, Long txnCount, String currency) {
        this.branch = branch;
        this.txnDate = txnDate;
        this.productType = productType;
        this.totalAmount = totalAmount;
        this.txnCount = txnCount;
        this.currency = currency;
    }

    public String getBranch() {
        return branch;
    }   

    public LocalDate getTxnDate() {
        return txnDate;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Long getTxnCount() {
        return txnCount;
    }       

    public String getCurrency() {
        return currency;
    }
}
