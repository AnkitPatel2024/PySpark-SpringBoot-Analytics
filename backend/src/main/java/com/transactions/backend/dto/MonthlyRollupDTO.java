package com.transactions.backend.dto;

import java.math.BigDecimal;

public class MonthlyRollupDTO {
    
    private String branch;
    private String yearMonth;
    private String productType;
    private BigDecimal totalAmount;
    private Long txnCount;
    private String currency;    

    public MonthlyRollupDTO(String branch, String yearMonth, String productType, BigDecimal totalAmount, Long txnCount, String currency ) {
        this.branch = branch;
        this.yearMonth = yearMonth;
        this.productType = productType;
        this.totalAmount = totalAmount;
        this.txnCount = txnCount;
        this.currency = currency;
    }

    public String getBranch() {
        return branch;
    }   

    public String getYearMonth() {
        return yearMonth;
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
