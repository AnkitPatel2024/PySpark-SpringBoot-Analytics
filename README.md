# PySpark-SpringBoot-Analytics
End-to-end analytics and reporting platform using PySpark ETL and Spring Boot REST APIs

---

## Project Overview

This project demonstrates an **end-to-end analytics and reporting pipeline**:

1. **PySpark ETL**: Processes raw transaction data (CSV) into cleaned and aggregated datasets.
2. **Spring Boot Backend**: Reads processed data and exposes REST APIs for analytics and reporting.
3. **Reporting**: Provides structured outputs (JSON / Parquet) that can be consumed by dashboards or other services.

**Purpose:** This project simulates a financial or regulatory reporting system, showing the ability to handle large-scale ETL, structured data, and API-driven reporting.

---
## Architecture Diagram

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
