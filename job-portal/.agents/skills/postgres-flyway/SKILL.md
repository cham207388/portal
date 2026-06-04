---
name: postgres-flyway
description: Use when adding or modifying PostgreSQL schema, JPA mappings, indexes, constraints, or Flyway migrations in a Spring Boot 4 service.
---

# PostgreSQL + Flyway skill

## Goal
Make database changes safely and cleanly.

## Rules
- Create a new Flyway migration for every schema change.
- Do not modify old applied migrations.
- Prefer backward-compatible changes.
- Consider indexes, nullability, defaults, and data backfill.
- Update entity mapping only after confirming schema intent.

## Deliverables
- migration file
- entity/repository updates
- test impact summary
- rollback or compatibility notes
