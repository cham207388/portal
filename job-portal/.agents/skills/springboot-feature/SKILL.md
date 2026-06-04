---
name: springboot-feature
description: Use when adding a new Spring Boot 4 feature such as a controller, service, DTO, repository, configuration properties, validation, or tests.
---

# Spring Boot feature skill

## Goal
Implement new backend features in a disciplined Spring Boot 4 style.

## Checklist
- Identify entry point: controller, service, scheduled task, listener, or integration.
- Identify domain objects and DTOs.
- Validate request payloads.
- Keep business logic in services.
- Add or update persistence only if required.
- Add Flyway migration for schema changes.
- Add tests:
  - service tests
  - controller tests
  - integration tests if persistence or serialization is involved

## Expected output
- plan
- changed files
- tests added or updated
- assumptions and risks
