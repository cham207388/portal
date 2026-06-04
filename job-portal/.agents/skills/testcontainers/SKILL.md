---
name: testcontainers
description: Use when creating or fixing integration tests that require PostgreSQL, Kafka, Redis, or other real infrastructure dependencies.
---

# Testcontainers skill

## Goal
Create reliable integration tests with realistic dependencies.

## Rules
- Prefer Testcontainers over local machine assumptions.
- Keep tests deterministic.
- Use container reuse only if the repo already standardizes it.
- Keep test setup readable.
- Verify schema migrations and startup wiring where relevant.

## Deliverables
- integration test class
- supporting test configuration
- short explanation of what behavior is truly being verified
