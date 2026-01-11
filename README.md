# PySpark-SpringBoot-Analytics
End-to-end analytics and reporting platform using PySpark ETL and Spring Boot REST APIs

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
