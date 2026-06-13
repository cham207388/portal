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

## Repository-specific preferences
- Prefer constructor injection.
- Prefer record DTOs where appropriate.
- Prefer configuration properties for grouped settings.
- Avoid large helper classes.
- Avoid changing build files unless needed for the task.
 - Secrets: the repo uses Doppler for secret management; local/CI runs that need DB credentials expect DB_URL, DB_USERNAME, DB_PASSWORD environment variables (see `README.md`). Use `doppler run -- ./gradlew` when running the app locally with secrets.
 - Flyway migrations are the canonical schema source and live in `src/main/resources/db/migration` — always add migrations for schema changes rather than altering entities only.
 - Project layout examples the agent should follow:
   - controllers: `src/main/java/com/abc/jobportal/**/controller` (e.g. `company/controller/CompanyController.java`)
   - services: `src/main/java/com/abc/jobportal/**/service` and `service/impl` for implementations
   - repositories: `src/main/java/com/abc/jobportal/**/repository`
   - DTOs: `src/main/java/com/abc/jobportal/dto` (records are used, e.g. `JobDto.java`)
 - Prefer running tests via the Gradle wrapper `./gradlew test`. Integration tests may require Docker (Testcontainers) to be available locally.
