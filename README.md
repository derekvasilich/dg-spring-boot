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