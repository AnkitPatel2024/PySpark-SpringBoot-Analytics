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



## 🛠️ The Tech Stack
* **Backend:** Java 17, Spring Boot 3, Spring Data JPA, RESTful APIs, Lombok
* **Database:** PostgreSQL (Production), H2 (In-memory Testing), Docker
* **Data Engineering:** PySpark, Databricks, Pandas
* **Frontend:** Python, Streamlit, Plotly
* **Testing:** JUnit 5, Mockito, MockMvc, AssertJ

---

## 📉 Interactive Dashboard
The dashboard provides a bird's-eye view of financial health with the following features:

* **Dynamic Visuals**: Bar charts for "Total Volume" and "Transaction Count" segmented by branch and product type.
* **Flexible Filtering**: Users can filter by specific dates or switch granularities to view monthly performance trends.

---

## ⚙️ How to Run

### 1. Database Setup
The environment is containerized using Docker for consistency and ease of deployment. Run the following command to start the PostgreSQL instance:

```bash
docker run --name pg-backend \
  -e POSTGRES_DB=transactions_db \
  -e POSTGRES_USER=user \
  -e POSTGRES_PASSWORD=password \
  -p 5432:5432 -d postgres

### 2. Backend API
```bash
./mvnw spring-boot:run

### 3. Dashboard
```bash
streamlit run app.py
