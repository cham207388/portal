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
Testcontainers (optional; not included in the project build by default)
Doppler (secrets injected at runtime; see README.md)
Lombok (for @RequiredArgsConstructor, @Getter, @Setter)
Spring Data JPA with auditing support
JWT for authentication
springdoc-openapi (Swagger UI available via dependency `org.springdoc:springdoc-openapi-starter-webmvc-ui`)
Caffeine cache (dependency `com.github.ben-manes.caffeine:caffeine` is used)
H2 console (dev convenience; dependency `spring-boot-h2console` is included)

## Repository-specific preferences
- Prefer constructor injection.
- Prefer record DTOs where appropriate.
- Prefer configuration properties for grouped settings.
- Avoid large helper classes.
- Avoid changing build files unless needed for the task.
- Secrets: the repo uses Doppler for secret management; local/CI runs that need DB credentials expect DB_URL, DB_USERNAME, DB_PASSWORD environment variables (see `README.md`). Use `doppler run -- ./gradlew` when running the app locally with secrets.
- Flyway migrations are the canonical schema source and live in `src/main/resources/db/migration` — always add migrations for schema changes rather than altering entities only.
 - Flyway migrations are the canonical schema source and live in `src/main/resources/db/migration` — always add migrations for schema changes rather than altering entities only.
 - Note: this project uses Jakarta namespace imports (e.g. `jakarta.persistence`, `jakarta.servlet`) because it targets Spring Boot 4 / Jakarta EE — prefer `jakarta.*` imports when adding new code.

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
- JWT authentication via custom `JwtTokenValidatorFilter` in the security filter chain (see `src/main/java/com/abc/jobportal/security/filter/JwtTokenValidatorFilter.java`).
- JWT secret is read from environment/property `JWT_SECRET` (application.yml also defines `jwt.secret`). The project defines a default in `ApplicationConstants.JWT_SECRET_DEFAULT_VALUE`.
- There is a `JwtProperties` class at `src/main/java/com/abc/jobportal/security/config/JwtProperties.java`, but it is not currently registered as a `@ConfigurationProperties` bean (annotations are commented out). Current code reads the secret from the environment/config rather than an injected `JwtProperties` bean.
- Use `JobPortalSecurityConfig` to define `SecurityFilterChain`, CORS, and custom authentication provider. The config registers CORS with origin `http://localhost:5173` by default (see `corsConfigurationSource()` in `JobPortalSecurityConfig`).
 - Use `JobPortalSecurityConfig` to define `SecurityFilterChain`, CORS, and custom authentication provider. CORS is configurable via a `CorsProperties` bean (`src/main/java/com/abc/jobportal/security/util/CorsProperties.java`) and bound in the main application with `@EnableConfigurationProperties(CorsProperties.class)` (see `src/main/java/com/abc/jobportal/JobPortalApplication.java`).
   - Configuration keys live under `app.cors` in `src/main/resources/application.yml` (example: `app.cors.allowed-origins: http://localhost:5173,https://dev.abcham.com`).
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
- Integration tests do not include Testcontainers by default in this repository. The project uses a Spring Boot Docker Compose lifecycle integration (see `compose.yaml`) for developer runs and relies on environment variables `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` to connect to a database during local runs.
- If you want to add Testcontainers-based integration tests, add the `org.testcontainers:postgresql` dependency and configure a Testcontainers-managed Postgres in the test sources.
- Place test files in `src/test/java/com/abc/jobportal/` mirroring source structure.
