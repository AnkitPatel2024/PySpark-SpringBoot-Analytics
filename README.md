# PySpark-SpringBoot-Analytics
An end-to-end financial analytics and reporting platform leveraging PySpark ETL, Spring Boot REST APIs, and Streamlit visualization.

---

## 🌟Project Overview

This project demonstrates an **a production-grade data lifecycle designed for financial or regulatory reporting**:

1. **PySpark/Big Data ETL**: Processes raw transaction data (CSV) using PySpark on Databricks to generate cleaned, validated and aggregated datasets.
2. **Spring Boot Backend**: A robust Java service layer that reads processed data from PostgresSQL database and expose high-performance REST APIs.
3. **Reporting**: Provides structured outputs (JSON / Parquet) that can be consumed by dashboards or other services.
4. **Analytics Dashboard**: An interactive Streamlit frontend providing real-time business intelligence and structured reporting.

**Purpose:** This project simulates a financial or regulatory reporting system, showing the ability to handle large-scale ETL, structured data, and API-driven reporting and analytics dashboard for quick visualization.

### Scaling for Large Datasets

- In real banking environments, daily transactions can reach millions per branch and product type.
- To ensure our ETL pipeline can handle large datasets:
  1. **Repartition the DataFrame** by `txn_date` and `branch_name` to distribute data evenly across Spark partitions.
  2. **Partition the output table** by `txn_date` and `branch_name` for faster queries and dashboard updates.
- This setup allows processing **months of historical data** efficiently without hitting performance bottlenecks.

---
## 🏗️ Architecture Diagram

```mermaid
graph TD
    A[Data Source / Streaming Input] --> B[PySpark ETL]
    B --> C[Processed Output]
    C --> D[Spring Boot Backend]
    D --> E[REST API]
    E --> F[Dashboard / Reporting]

    style A fill:#f9f,stroke:#333,stroke-width:2px
    style B fill:#bbf,stroke:#333,stroke-width:2px
    style C fill:#bfb,stroke:#333,stroke-width:2px
    style D fill:#ffb,stroke:#333,stroke-width:2px
    style E fill:#fbf,stroke:#333,stroke-width:2px
    style F fill:#fbb,stroke:#333,stroke-width:2px

---

