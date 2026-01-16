package com.transactions.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Entity
@Table(name = "transaction_rollups")
@Getter                 // Generates all getters
@Setter                 // Generates all setters
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Required by JPA
@AllArgsConstructor     // Generates constructor with all fields
@Builder                // Allows cool syntax like Entity.builder().branch("NY").build()
public class TransactionRollupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="branch_name",nullable = false)
    private String branch;

    @Column(name = "product_type", nullable = false)
    private String productType;

    @Column(name = "txn_date")
    private LocalDate txnDate;

    @Column(name = "year_month")
    private String yearMonth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RollupGranularity granularity;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "txn_count", nullable = false)
    @JsonProperty("txnCount")
    private Long txnCount;

    @Column(name = "currency", nullable = false)
    private String currency;

}

