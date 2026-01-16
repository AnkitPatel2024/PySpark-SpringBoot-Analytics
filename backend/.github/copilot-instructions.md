# Copilot / AI Agent Instructions for backend

Purpose: get AI coding agents productive quickly with concrete, repo-specific guidance.

- Big picture: This is a Spring Boot REST backend under package `com.transactions.backend`.
  - Layering: `controller` -> `service` -> `repository` -> (data source). DTOs live in `dto` and custom exceptions in `exception`.
  - Example flow: an HTTP request hits `TransactionsController` -> calls `TransactionsService` -> calls `TransactionsRepository` -> returns `DailyRollupDTO`/`MonthlyRollupDTO` to the client.

- Project structure to reference:
  - `src/main/java/com/transactions/backend/controller/TransactionsController.java`
  - `src/main/java/com/transactions/backend/service/TransactionsService.java`
  - `src/main/java/com/transactions/backend/repository/TransactionsRepository.java`
  - `src/main/java/com/transactions/backend/dto/*` (e.g. `DailyRollupDTO.java`, `MonthlyRollupDTO.java`)
  - `src/main/java/com/transactions/backend/exception/GlobalExceptionHandler.java`
  - Tests mirror packages in `src/test/java/...` (e.g. `TransactionsControllerTest.java`).

- Error handling conventions:
  - Throw domain exceptions from lower layers (`ValidationException`, `DataNotFoundException`).
  - `GlobalExceptionHandler` centralizes mapping to `ErrorResponse` objects; do not return raw exception messages for 500 errors.

- Data & repository notes:
  - `TransactionsRepository` currently returns hard-coded sample lists (see `getDailyRollups()` / `getMonthlyRollups()`).
  - If you integrate a DB, keep repository methods returning DTOs (or map Entities -> DTOs in repository/service) to avoid breaking controllers.

- Build / run / test (project-specific):
  - Use the Maven wrapper included in repo root. On Windows run: `mvnw.cmd test` and `mvnw.cmd spring-boot:run`.
  - On Unix/macOS use `./mvnw test` and `./mvnw spring-boot:run`.
  - Run a single test class: `mvnw.cmd -Dtest=TransactionsServiceTest test` (Windows).

- Testing & changing behavior:
  - Unit tests are in `src/test/java` and follow the same package layout as `src/main/java`.
  - When adding controller endpoints, add/update corresponding controller tests in the test package.

- Code style & conventions (discoverable patterns):
  - Controllers are thin: keep routing, delegate logic to `TransactionsService`.
  - Services act as orchestration layer and depend on repository interfaces/classes.
  - DTOs are simple data carriers located in `dto` package; prefer returning DTOs from controllers.

- Debugging tips:
  - Start the app with the wrapper and attach an IDE debugger to the Spring Boot process.
  - Logs and error responses are shaped by `GlobalExceptionHandler`; to inspect underlying exceptions, reproduce locally and check application logs rather than changing response payloads.

- When editing files:
  - Update unit tests alongside behavior changes. Look at `src/test/java/com/transactions/backend/controller/TransactionsControllerTest.java` for patterns.
  - If introducing new configuration, modify `src/main/resources/application.properties` and ensure tests mock or supply required properties.

If any of these areas look incomplete or you'd like me to expand examples (e.g., show a sample controller test or add a DB-backed repository), tell me which part to expand and I will update this doc.
