# AGENTS.md

## Project summary
This repository contains a Spring Boot 4.x backend built with Java 25 and Gradle.
The service exposes REST APIs, persists to PostgreSQL, and uses Flyway for schema migrations.

## What good changes look like
A good change in this repo:
- respects the existing package structure
- includes tests
- avoids unnecessary framework additions
- keeps controllers thin
- keeps business logic in services
- uses migrations for schema changes
- explains tradeoffs clearly

## Default workflow
1. Inspect relevant files first.
2. Produce a short plan for any task affecting multiple files.
3. Make the smallest correct change.
4. Update tests.
5. Summarize changed files, rationale, and any follow-up.

## Tech assumptions
- Java 25
- Spring Boot 4.x
- Gradle 9 (use the project wrapper: `./gradlew`)
- PostgreSQL
- Flyway
- JUnit 5
- Testcontainers
- Doppler (secrets injected at runtime; see README.md)
- Lombok (for @RequiredArgsConstructor, @Getter, @Setter)
- Spring Data JPA with auditing support
- JWT for authentication

## Repository-specific preferences
- Prefer constructor injection.
- Prefer record DTOs where appropriate.
- Prefer configuration properties for grouped settings.
- Avoid large helper classes.
- Avoid changing build files unless needed for the task.
- Secrets: the repo uses Doppler for secret management; local/CI runs that need DB credentials expect DB_URL, DB_USERNAME, DB_PASSWORD environment variables (see `README.md`). Use `doppler run -- ./gradlew` when running the app locally with secrets.
- Flyway migrations are the canonical schema source and live in `src/main/resources/db/migration` — always add migrations for schema changes rather than altering entities only.

### Service & Repository Patterns
- Services follow interface-based pattern: create `IServiceName.java` interface in `src/main/java/com/abc/jobportal/{feature}/service/` and implement with `{ServiceName}Impl.java` in `src/main/java/com/abc/jobportal/{feature}/service/impl/` (see `ICompanyService` and `CompanyServiceImpl`).
- Use `@RequiredArgsConstructor` for constructor injection of dependencies.
- Repositories extend `JpaRepository<Entity, ID>` and live in `src/main/java/com/abc/jobportal/repository/` (see `CompanyRepository`, `JobPortalUserRepository`).
- Use `@NamedQueries` on entities for common queries to promote reusability.

### JPA Auditing Pattern
- All auditable entities extend `BaseEntity` (`src/main/java/com/abc/jobportal/entity/BaseEntity.java`), which provides:
  - `@CreatedDate` / `@CreatedBy`: set automatically on entity creation
  - `@LastModifiedDate` / `@LastModifiedBy`: updated on entity modifications
  - Fields are NOT updatable (`updatable = false` for created fields)
- `AuditorAwareImpl` (component) provides current username via Spring Security context.
- Enable auditing with `@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")` on the main application class.

### Exception Handling
- Centralize exception handling with `@RestControllerAdvice` (see `GlobalExceptionHandler`).
- Create custom exceptions (e.g., `RegistrationValidationException`) for domain-specific errors.
- `GlobalExceptionHandler` handles `MethodArgumentNotValidException`, `HandlerMethodValidationException`, and custom exceptions; returns normalized error responses.

### Security & Authentication
- JWT authentication via custom `JwtTokenValidatorFilter` in the security filter chain.
- Store JWT properties in configuration class (e.g., `JwtProperties`).
- Use `JobPortalSecurityConfig` to define `SecurityFilterChain`, CORS, and custom authentication provider.
- Controllers use `/api/{resource}` endpoints; public endpoints use `/api/{resource}/public`.
- Apply CSRF protection for state-changing requests (POST, PUT, DELETE) — see README for CSRF token flow.

### Aspects & Logging
- Use Spring AOP aspects for cross-cutting concerns (see `src/main/java/com/abc/jobportal/aspects/`):
  - `LoginSuccessAuditAspect`: audits successful login attempts via `@AfterReturning`.
  - `ExceptionAuditAspect`: logs exceptions via `@AfterThrowing`.
  - `LoggingAndPerformanceAspect`: logs method execution and performance metrics.
  - `RegisterValidationAspect`: validates registration input.
- Aspects should be minimal — keep validation and audit logic focused.

### Project layout examples:
- controllers: `src/main/java/com/abc/jobportal/{feature}/controller/{Feature}Controller.java`
- services: `src/main/java/com/abc/jobportal/{feature}/service/I{Service}.java` (interface) and `src/main/java/com/abc/jobportal/{feature}/service/impl/{Service}Impl.java` (implementation)
- repositories: `src/main/java/com/abc/jobportal/repository/{Entity}Repository.java`
- DTOs: `src/main/java/com/abc/jobportal/dto/{Dto}.java` (records, e.g., `CompanyDto.java`, `LoginResponseDto.java`)
- entities: `src/main/java/com/abc/jobportal/entity/{Entity}.java`
- configuration: `src/main/java/com/abc/jobportal/config/` and `src/main/java/com/abc/jobportal/security/`
- exceptions: `src/main/java/com/abc/jobportal/exception/`
- constants: `src/main/java/com/abc/jobportal/constants/ApplicationConstants.java`

### Testing
- Run tests via `./gradlew test`.
- Integration tests use Testcontainers for PostgreSQL; Docker must be available locally.
- Place test files in `src/test/java/com/abc/jobportal/` mirroring source structure.
