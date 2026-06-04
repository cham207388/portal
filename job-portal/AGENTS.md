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
- Gradle
- PostgreSQL
- Flyway
- JUnit 5
- Testcontainers

## Repository-specific preferences
- Prefer constructor injection.
- Prefer record DTOs where appropriate.
- Prefer configuration properties for grouped settings.
- Avoid large helper classes.
- Avoid changing build files unless needed for the task.
