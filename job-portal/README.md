# Spring Boot 4 Application

## 📌 Overview

This is a backend service built with:

- Java 25
- Spring Boot 4
- Gradle 9
- PostgreSQL (or your DB)
- Doppler (for secrets management)

This application uses **Doppler** to securely manage all credentials and environment variables.  
No secrets are stored in code, `.env` files, or the repository.

---

## 🔐 Secrets Management (Doppler)

All credentials (database, API keys, tokens, etc.) are stored in **Doppler** and injected at runtime as environment variables.

Doppler provides:
- centralized secret storage
- environment separation (dev, stg, prod)
- secure runtime injection

Secrets are accessed in the app via:

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

---

<details>
<summary>Example Doppler CLI usage</summary>

```md
🔐 Secrets Management (Doppler)

Doppler is used to store and inject secrets at runtime.

### Why Doppler?

- Centralized secret management
- No secrets in code or repo
- Environment isolation (dev, stg, prod)
- Secure runtime injection

---

## ⚙️ Prerequisites

Install the following:

- Java 25
- Gradle 9 (or wrapper)
- Docker (optional)
- Doppler CLI

---

## 📥 Install Doppler CLI

Mac:

```bash
brew install dopplerhq/cli/doppler
```

Verify:

```bash
doppler --version
```

---

## 🔑 Authenticate with Doppler

```bash
doppler login
```

---

## 🔗 Link Project to Doppler

```bash
doppler setup
```

---

## 🔐 Add Secrets

```bash
doppler secrets set DB_URL="jdbc:postgresql://localhost:5432/student"
doppler secrets set DB_USERNAME="postgres"
doppler secrets set DB_PASSWORD="StrongPassword123!"
```

---

## 📄 application.yml Configuration

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

---

## ▶️ Running the Application

```bash
doppler run -- ./gradlew bootRun
```

---

## 🐳 Running with Docker

```dockerfile
FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY build/libs/app.jar app.jar
CMD ["doppler", "run", "--", "java", "-jar", "app.jar"]
```

Run:

```bash
docker run -e DOPPLER_TOKEN=dp.st.dev.xxxxx my-app
```

---

## 🚀 CI/CD Example

```yaml
- name: Build with Doppler
  run: doppler run -- ./gradlew build
  env:
    DOPPLER_TOKEN: ${{ secrets.DOPPLER_TOKEN }}
```

---

## 🧠 Best Practices

### ✅ DO

- Use environment variables
- Use Doppler for secrets
- Use service tokens
- Rotate secrets

### ❌ DON'T

- Commit .env files
- Hardcode secrets
- Log secrets

---

## 🏁 Summary

This setup ensures:
- secure secret management
- clean configuration
- production-ready architecture
```

</details>