# dg-spring-boot

A Spring Boot backend sample demonstrating a hybrid API architecture with REST, GraphQL, Kafka event streaming, JWT security, and enterprise-grade persistence.

## Why this project stands out

This repository is designed to highlight the architecture and engineering capabilities expected of a Principal Architect, Architect, or AI Engineer:

- **Hybrid API strategy**: REST endpoints for conventional resource access and GraphQL for flexible, client-driven queries.
- **Event-driven platform readiness**: Spring Kafka producer, consumer, topic admin, and configuration for asynchronous workflows.
- **Security-first backend**: JWT authentication, custom password hashing, stateless sessions, and scoped CORS configuration.
- **Data architecture discipline**: Spring Data JPA repositories, entity scanning, MySQL production support, and H2 test compatibility.
- **GraphQL pagination**: Relay-style connection support for efficient vehicle query pagination.
- **Frontend-agnostic integration**: built to serve modern React/Next.js or other SPA/enterprise clients.

## Key capabilities

- REST API for vehicles and route resources
- GraphQL API for user profile, vehicles, and customers
- JWT-based authentication and authorization
- Kafka integration with producer and consumer configuration
- GraphQL schema with JSON scalar support
- Spring Boot Mail with Thymeleaf HTML and plain-text templates for email delivery
- Inline attachment support for rich email content and MUA portability
- Spring Security with custom `ShaPasswordEncoder`
- Cross-origin support for frontend integration
- Maven-based build and Docker-friendly Spring Boot packaging

## Architecture highlights

- **`com.dg.HelloSpringBootApplication.java`** application bootstrap
- **`com.dg.controllers.*`** REST controllers exposing `/api/*`
- **`com.dg.configuration.*`** Kafka, GraphQL, JPA, and security configuration
- **`com.dg.repositories.*`** provides JPA persistence for users, vehicles, refresh tokens, and domain entities
- **`com.dg.queryresolvers.*`** GraphQL resolvers using `graphql-java-kickstart`
- **`com.dg.security.*`** JWT filters, authentication provider, and security policy- `com.dg.service.EmailServiceImpl` and `com.dg.configuration.EmailConfig` for SMTP mail delivery, Thymeleaf email templates, and inline attachment support- **`src/main/resources/graphql/vehicles.graphqls`** GraphQL schema for core domain models

## Architecture summary

This project is structured as a backend platform with:

- a hybrid API layer supporting both REST and GraphQL
- an event-driven Kafka layer for asynchronous workflows
- a secure JWT-powered identity and access layer
- a domain data layer backed by Spring Data JPA and relational persistence

## Security design

This repository demonstrates an enterprise-grade security architecture with:

- **JWT authentication and authorization** for stateless access control and secure token-based sessions
- **Custom password hashing** via a dedicated `ShaPasswordEncoder` and application-managed salt
- **Scoped CORS configuration** to limit cross-origin access to trusted frontends
- **Stateless security model** using `SessionCreationPolicy.STATELESS` to avoid server session state
- **Fine-grained request handling** with explicit permit rules for authentication and GraphQL tooling endpoints
- **Secure defaults** by disabling CSRF for API flows while enforcing authentication for protected resources

## Email and messaging design

This project also demonstrates a robust email delivery design with:

- Spring Boot Mail integration for SMTP-based messaging
- Thymeleaf HTML templating with support for plain-text fallback for MUAs
- inline attachment and embedded content capability for richer transactional emails
- an event-driven design pattern that supports asynchronous message delivery via Kafka signup workflows

## What this demonstrates for hiring managers

- ability to design and deliver hybrid API systems
- event-driven architecture competency with Kafka
- secure enterprise backend engineering with JWT and CORS scoping
- domain-driven data access patterns and layered organization

## Technology stack

- Java 11
- Spring Boot 2.7
- Spring Web, Spring Data JPA, Spring Security
- Spring Kafka
- GraphQL Java Kickstart
- JWT authentication (`io.jsonwebtoken`)
- Spring Boot Mail
- Thymeleaf
- MySQL and H2 support
- Maven build system

## Testing

This project includes Spring Boot test coverage for API endpoints and authentication workflows.

- REST controller tests for `vehicles`, `routes`, `users`, and `customers`
- Spring Boot `@SpringBootTest` and `@AutoConfigureMockMvc` support for end-to-end request validation
- `MockMvc` and `Mockito` usage to isolate controller behavior and verify response handling
- dedicated security test config for authentication and authorization scenarios
- `src/test/java/com/dg` contains full API test suites and context load validation

### Test coverage focus

- REST controller endpoint coverage for `vehicles`, `routes`, `users`, and `customers`
- validation of response codes and error handling for not-found and invalid requests
- authentication workflow coverage for login, signup, refresh token, and secured endpoints
- foundation for GraphQL query coverage through schema and resolver testing patterns

Run the test suite with:

```bash
./mvnw test
```

## Environment variables

The application is configured through Spring Boot properties. When setting environment variables, use the standard Spring Boot naming convention (`.` becomes `_` and keys are uppercase).

| Environment variable | Spring property | Description |
|----------------------|-----------------|-------------|
| `SPRING_DATASOURCE_URL` | `spring.datasource.url` | JDBC connection URL for the primary database |
| `SPRING_DATASOURCE_USERNAME` | `spring.datasource.username` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | `spring.datasource.password` | Database password |
| `SPRING_DATASOURCE_DRIVER_CLASS_NAME` | `spring.datasource.driver-class-name` | JDBC driver classname |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `spring.jpa.hibernate.ddl-auto` | JPA schema generation mode |
| `SPRING_JPA_SHOW_SQL` | `spring.jpa.show-sql` | Enable SQL logging |
| `SPRING_JPA_DATABASE_PLATFORM` | `spring.jpa.database-platform` | Hibernate dialect |
| `APP_SECURITY_SALT` | `app.security-salt` | Password hashing salt for custom encoder |
| `APP_SECURITY_ALGORITHM` | `app.security-algorithm` | Hash algorithm for password encoding |
| `APP_SECURITY_CORS_ALLOWED_ORIGIN` | `app.security-cors-allowed-origin` | Allowed CORS origin list |
| `APP_JWTSECRET` | `app.jwtSecret` | Base64-encoded JWT secret key |
| `APP_JWTSIGNINGALGORITHM` | `app.jwtSigningAlgorithm` | JWT signing algorithm |
| `APP_JWTEXPIRATIONMS` | `app.jwtExpirationMs` | JWT token expiration in milliseconds |
| `APP_JWTREFRESHEXPIRATIONMS` | `app.jwtRefreshExpirationMs` | JWT refresh token expiration in milliseconds |
| `SPRING_MAIL_HOST` | `spring.mail.host` | SMTP server host |
| `SPRING_MAIL_PORT` | `spring.mail.port` | SMTP server port |
| `SPRING_MAIL_USERNAME` | `spring.mail.username` | SMTP username |
| `SPRING_MAIL_PASSWORD` | `spring.mail.password` | SMTP password |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | `spring.kafka.bootstrap-servers` | Kafka broker address |
| `SPRING_KAFKA_CONSUMER_GROUP_ID` | `spring.kafka.consumer.group-id` | Kafka consumer group id |

## GraphQL capabilities

The GraphQL schema supports:

- `getProfile` for authenticated user profiles
- `allVehicles` and `allVehiclesPaginated(page:size:)` for flexible listing
- `getVehicleById(id:)` for entity lookup
- `allCustomers` for customer data retrieval
- custom `JSON` scalar for rich vehicle description payloads

## Kafka message flow

Kafka is configured with:

- `KafkaProducerConfig` for publishing payloads
- `KafkaConsumerConfig` for topic listeners and consumer groups
- `KafkaTopicConfig` for admin-level topic creation
- asynchronous onboarding and event-driven workflow support

## Docker containerization

This project includes full Docker support for local development and deployment:

### Services

- **Spring Boot App** (`eclipse-temurin:11-jre`): Multi-stage Maven build optimized for production
- **MySQL 8.0**: Relational database with persistent volume
- **Apache Kafka** (Confluent 7.5.0): Event streaming broker
- **Zookeeper** (Confluent 7.5.0): Kafka coordination and broker management

### Docker Compose Features

- **Service networking**: All containers communicate via internal `dg-network` bridge
- **Health checks**: Kafka waits until broker is ready before app starts
- **Persistent volumes**: MySQL data persists across container restarts
- **Auto topic creation**: Kafka automatically creates configured topics on startup
- **Environment variable injection**: Simplified configuration via docker-compose.yml

### Kafka Inside Docker

Kafka runs with:
- **Internal broker address**: `kafka:29092` (for Docker-to-Docker communication)
- **External broker address**: `localhost:9092` (for host machine access)
- **Auto-created topics**: `dg-spring-boot` topic for event streaming
- **Single broker, single partition** for local development (configurable for production)

## Running locally

### Option 1: Docker Compose (Recommended)

The simplest way to run the entire stack with MySQL, Kafka, and Zookeeper:

```bash
docker-compose up
```

This starts:
- **Spring Boot app** on `http://localhost:8080`
- **MySQL database** on `localhost:3306`
- **Kafka broker** on `localhost:9092`
- **Zookeeper** on `localhost:2181`

All services are automatically configured and networked together. Access REST endpoints at `/api/*` and GraphQL at `/api/graphql`.

To run in background:
```bash
docker-compose up -d
```

To stop all services:
```bash
docker-compose down
```

### Option 2: Manual Setup

1. Set the required Spring Boot properties or environment variables.
2. Ensure Kafka and MySQL are running (manually or via docker).
3. Build the project:

```bash
./mvnw clean package
```

4. Run the application:

```bash
./mvnw spring-boot:run
```

5. Access REST endpoints at `/api/*` and GraphQL at `/api/graphql`.

## Why this repository is a strong pinned example

- demonstrates hybrid API architecture with a real backend stack
- shows event-driven design using Kafka alongside REST/GraphQL
- exhibits secure backend engineering with JWT support
- reflects architecture-level thinking for extensibility and integration

## Notes

- Legacy Angular assets are present but the backend is decoupled for modern frontends.
- The codebase is well positioned for extension with Kafka-driven workflows, GraphQL mutations, and enterprise features.
