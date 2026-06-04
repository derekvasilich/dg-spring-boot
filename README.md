# dg-spring-boot

A backend-first Spring Boot sample application built to demonstrate modern API architecture, secure authentication, and enterprise integration patterns.

## Why this repo matters

This repository is designed as a strong example for Architect / Principal Architect / AI Engineer roles by showcasing:

- **Hybrid API design** combining REST and GraphQL support
- **Production-style security** with JWT access tokens, refresh tokens, and method-level authorization
- **Email-driven user onboarding** via Spring Boot Mail
- **Database portability** with MySQL runtime plus H2 support for tests
- **Domain-driven layering** with controllers, services, repositories, models, and security modules separated cleanly
- **Real-world deployment readiness** targeting cloud environments such as AWS Elastic Beanstalk

## Key capabilities

- JWT-based access tokens plus refresh tokens for secure, stateless session management
- Signup registration with encrypted password storage
- Role-aware method-level authorization on protected endpoints
- GraphQL schema for users, customers, companies, and vehicle queries
- Custom GraphQL `JSON` scalar for flexible vehicle metadata and description payloads
- Nested GraphQL domain relationships for vehicle → company and vehicle → description resolution
- Connection-style pagination with `VehicleConnection` and `PageInfo` to support scalable queries
- REST endpoints for authentication, user management, and business data access
- Spring Security with CORS configuration and production-aware GraphiQL gating
- Email notification integration for signup confirmation

## Architecture highlights

- **`com.example.security`** handles authentication, authorization, and token lifecycle
- **`com.example.controllers`** exposes API endpoints for auth and business logic
- **`com.example.repositories`** provides JPA persistence for users, vehicles, refresh tokens, and domain entities
- **`com.example.configuration`** centralizes GraphQL and mail config beans
- **`src/main/resources/graphql/vehicles.graphqls`** defines a GraphQL schema with custom scalars, nested domain types, and connection-based pagination
- GraphiQL support is intentionally gated and marked as insecure in configuration, showing production-aware API exposure practices

## Noteworthy technical choices

- Spring Boot `2.7.0` and Spring Security
- `spring-boot-starter-data-jpa` for database access
- `jjwt` for JWT token creation and validation
- GraphQL Java Kickstart for schema-driven GraphQL exposure
- `spring-boot-starter-mail` for transactional email support
- Modular configuration using `@Configuration` and bean wiring

## Security design notes

- **Stateless auth** using JWT access tokens with a refresh-token lifecycle
- **Method-level authorization** on sensitive endpoints via `@PreAuthorize`
- **Custom authentication filter** (`AuthTokenFilter`) for secure token validation
- **Password hashing** with a dedicated password encoder
- **CORS policy** configured for allowed origins and headers, minimizing cross-site exposure
- **GraphiQL gating** is intentionally logged as insecure when enabled, showing awareness of development vs production security boundaries
- **Unauthorized entrypoint handling** for consistent 401/403 responses

## What you can demonstrate with this repo

- Designing a hybrid backend API surface for REST and GraphQL consumers
- Building secure authentication and refresh-token flows for modern clients
- Applying enterprise security controls with Spring Security and method-level authorization
- Managing cross-cutting concerns like email, CORS, and flexible JSON data handling in GraphQL
- Architecting a modular Java backend ready for cloud deployment and production validation

## Running the application

1. Build the project:

```bash
./mvnw clean package
```

2. Run locally:

```bash
./mvnw spring-boot:run
```

3. Open the API or GraphQL endpoint in a browser or API client.

> This application expects runtime database configuration for MySQL. Tests can run against the included H2 in-memory database.

## Containerization

A Dockerfile is included so this application can run as a containerized microservice in AWS Fargate or any container platform.

Build the image:

```bash
docker build -t dg-spring-boot:latest .
```

Run locally:

```bash
docker run --rm -p 8080:8080 dg-spring-boot:latest
```

A `docker-compose.yml` is also provided for local development with MySQL:

```bash
docker compose up --build
```

This starts:
- `app` on port `8080`
- `db` MySQL 8 with database `dg_spring_boot`

For Fargate, push the image to ECR or another container registry, then deploy the task definition with port `8080`.

### Docker build note

During image builds the Maven wrapper can sometimes pick up a `MAVEN_CONFIG` value from the builder environment (for example `/root/.m2`) which may be interpreted incorrectly as a Maven lifecycle phase. The included `Dockerfile` unsets `MAVEN_CONFIG` when running `./mvnw` to avoid the `Unknown lifecycle phase '/root/.m2'` error. If you see that error, rebuild with:

```bash
docker build --no-cache -t dg-spring-boot:latest .
```

Or set/clear `MAVEN_CONFIG` in your build stage (for example `ENV MAVEN_CONFIG=""`) to prevent wrapper argument injection.

## Environment variables

The application reads configuration from Spring `application.properties` keys. Provide values via environment variables (uppercase + underscores), `SPRING_APPLICATION_JSON`, or your platform's secret manager.

| Environment variable | Property key | Description | Example |
|---|---|---|---|
| `SPRING_DATASOURCE_URL` | `spring.datasource.url` | JDBC connection URL for the runtime database | `jdbc:mysql://db:3306/dg_spring_boot?useSSL=false&serverTimezone=UTC` |
| `SPRING_DATASOURCE_USERNAME` | `spring.datasource.username` | Database username | `root` |
| `SPRING_DATASOURCE_PASSWORD` | `spring.datasource.password` | Database password | `example` |
| `APP_JWT_SECRET`, `APP_JWTSECRET` or `SPRING_APPLICATION_JSON` | `app.jwtSecret` | JWT signing secret (required) | `ReplaceWithYourJwtSecret` |
| `APP_JWT_EXPIRATION_MS` | `app.jwtExpirationMs` | Access token TTL (ms) | `3600000` |
| `APP_JWT_REFRESH_EXPIRATION_MS` | `app.jwtRefreshExpirationMs` | Refresh token TTL (ms) | `86400000` |
| `APP_JWTSIGNINGALGORITHM` | `app.jwtSigningAlgorithm` | JWT signing algorithm | `HS256` |
| `APP_SECURITY_SALT` | `app.security-salt` | Salt for the password encoder | `random-salt-value` |
| `APP_SECURITY_ALGORITHM` | `app.security-algorithm` | MessageDigest algorithm used by password encoder | `SHA-256` |
| `APP_SECURITY_CORS_ALLOWED_ORIGIN` | `app.security-cors-allowed-origin` | Comma-separated allowed CORS origins | `http://localhost:8080` |
| `APP_MAIL_SIGNUP_FROM` | `app.mail.signup.from` | From address for signup emails | `info@dealergears.com` |
| `APP_MAIL_SIGNUP_SUBJECT` | `app.mail.signup.subject` | Signup email subject | `Thanks for signing up to Dealer Gears!` |
| `SPRING_MAIL_HOST` | `spring.mail.host` | SMTP host | `smtp.example.com` |
| `SPRING_MAIL_PORT` | `spring.mail.port` | SMTP port | `587` |
| `SPRING_MAIL_USERNAME` | `spring.mail.username` | SMTP username | (your SMTP user) |
| `SPRING_MAIL_PASSWORD` | `spring.mail.password` | SMTP password | (your SMTP password) |
| `SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH` | `spring.mail.properties.mail.smtp.auth` | SMTP auth flag | `true` |
| `SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE` | `spring.mail.properties.mail.smtp.starttls.enable` | SMTP STARTTLS flag | `true` |

Notes:

- Spring Boot does relaxed binding: `APP_JWT_SECRET`, `app.jwt-secret`, and `app.jwtSecret` all map to `app.jwtSecret`.
- Do not commit secrets to `docker-compose.yml` for production. Use `SPRING_APPLICATION_JSON` or your cloud provider's secret manager in CI/CD.
- If you see `Could not resolve placeholder 'app.jwtSecret'`, inspect the container environment with:

```bash
docker compose exec app env | grep -i jwt
```

and verify the expected variables are present.

## Tech stack

- Java 11
- Spring Boot 2.7.0
- Spring Security
- Spring Data JPA
- GraphQL Java Kickstart
- JSON Web Tokens (jjwt)
- Spring Boot Mail
- MySQL runtime, H2 test database

## Example API calls

### Authenticate and retrieve JWT

```bash
curl -X POST http://localhost:8080/api/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"user@example.com","password":"password"}'
```

### Refresh JWT token

```bash
curl -X POST http://localhost:8080/api/refreshtoken \
  -H 'Content-Type: application/json' \
  -d '{"refreshToken":"<refresh-token>"}'
```

### Register a new user

```bash
curl -X POST http://localhost:8080/api/signup \
  -H 'Content-Type: application/json' \
  -d '{"email":"new@example.com","firstName":"Jane","lastName":"Doe","password":"securePass123"}'
```

### Query GraphQL

```bash
curl -X POST http://localhost:8080/graphql \
  -H 'Content-Type: application/json' \
  -d '{"query":"{ allVehicles { id vin name price } }"}'
```

## Recommended showcase narrative

Use this repo to highlight your ability to:

1. Translate business requirements into a secure backend API.
2. Choose the right integration patterns for authentication and data access.
3. Manage API evolution by exposing REST and GraphQL side-by-side.
4. Keep the codebase modular and ready for cloud deployment.
5. Demonstrate both functional and technical architecture in a portfolio context.

## Notes

- The core value of this sample is backend architecture, security, and API design rather than frontend styling.

---

For an architect-level portfolio, this project is a strong example of building a real backend service with security, extensibility, and deployment considerations handled clearly.