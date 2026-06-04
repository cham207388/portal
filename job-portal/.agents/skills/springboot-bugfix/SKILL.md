---
name: springboot-bugfix
description: Use when diagnosing or fixing a Spring Boot 4 bug, exception, startup failure, bean wiring issue, transaction problem, serialization issue, or test failure.
---

# Spring Boot bugfix skill

## Goal
Diagnose root cause before changing code.

## Workflow
1. Restate the bug precisely.
2. Identify likely layer:
   - configuration
   - bean wiring
   - web
   - validation
   - persistence
   - transaction
   - serialization
   - security
3. Inspect stack trace and affected files.
4. Explain probable root cause.
5. Propose minimal fix.
6. Add or update regression tests.

## Rules
- Do not guess blindly.
- Do not make broad refactors during a bugfix unless the bug is architectural.
- Prefer one focused fix with a regression test.
