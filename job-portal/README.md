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

## 🧪 API Testing Flow (Quick Start)

### Prerequisites

- Docker running (for PostgreSQL)
- curl or Postman installed

### 1. Start PostgreSQL

```bash
docker-compose up -d
```

PostgreSQL will be available at `localhost:5433` with:

- Database: `jobportal`
- Username: `postgres`
- Password: `postgres`

### 2. Start the Application

```bash
doppler run -- ./gradlew bootRun
```

The app runs on **http://localhost:8082**

---

## 🔐 API Workflow

### Step 1: Get CSRF Token (Public - Optional for testing)

Some endpoints may require CSRF tokens. Fetch one with:

```bash
curl -i -X GET http://localhost:8082/api/csrf-token/public
```

Response includes:

- `X-CSRF-TOKEN` header (or `XSRF-TOKEN` cookie)
- Store this token for state-changing requests (POST, PUT, DELETE)

### Step 2: Register a User (Public)

```bash
curl -X POST http://localhost:8082/api/auth/register/public \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "mobileNumber": "9876543210",
    "password": "SecurePassword123!"
  }'
```

**Response:**

```
User registered successfully
```

### Step 3: Login to Get JWT Token (Public)

```bash
curl -i -X POST http://localhost:8082/api/auth/login/public \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john@example.com",
    "password": "SecurePassword123!"
  }'
```

**Response includes:**

```json
{
  "message": "OK",
  "user": {
    "userId": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "role": "JOB_SEEKER"
  },
  "jwtToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Save the `jwtToken` — you'll need it for authenticated requests.**

---

## 🔐 Making Authenticated Requests

### For Secured Endpoints (requires JWT token)

Include the JWT token in the `Authorization: Bearer <token>` header:

```bash
curl -X GET http://localhost:8082/api/companies \
  -H "Authorization: Bearer <your-jwt-token-here>"
```

### Adding CSRF Protection (for state-changing requests)

1. **Get CSRF token** from `/api/csrf-token/public`
2. **Include** both:
    - `Authorization: Bearer <jwt-token>`
    - `X-CSRF-TOKEN: <csrf-token>` header

Example with CSRF:

```bash
curl -X POST http://localhost:8082/api/companies \
  -H "Authorization: Bearer <your-jwt-token-here>" \
  -H "X-CSRF-TOKEN: <csrf-token-here>" \
  -H "Content-Type: application/json" \
  -d '{ "name": "My Company" }'
```

---

## 🧪 API Endpoints Reference

| Method | Endpoint                    | Auth | CSRF | Purpose               |
|--------|-----------------------------|------|------|-----------------------|
| GET    | `/api/csrf-token/public`    | No   | No   | Fetch CSRF token      |
| POST   | `/api/auth/register/public` | No   | No   | Register new user     |
| POST   | `/api/auth/login/public`    | No   | No   | Login & get JWT token |
| GET    | `/api/companies`            | Yes  | No   | List companies        |
| POST   | `/api/companies`            | Yes  | Yes  | Create company        |
| GET    | `/api/contacts/public`      | No   | No   | List public contacts  |
| POST   | `/api/contacts`             | Yes  | Yes  | Create contact        |

---

## 🐳 Running with Docker Compose (All-in-One)

Start PostgreSQL:

```bash
docker-compose up -d
```

Then run the app:

```bash
doppler run -- ./gradlew bootRun
```

Check logs:

```bash
docker-compose logs -f postgres
```

Stop everything:

```bash
docker-compose down
```

---

## 🔐 Secrets Management (Doppler)

All credentials (database, API keys, tokens, etc.) are stored in **Doppler** and injected at runtime as environment
variables.

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