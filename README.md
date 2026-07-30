# Notifi

An event-driven, multi-tenant notification platform built with Spring Boot.

---

## 🚀 Features

- **Multi-Tenancy**: Scopes Projects to Users, and Templates/Rules to Projects.
- **Cascade Deletion**: Uses database-level cascades to safely delete nested records.
- **Safe Templates**: Uses Handlebars to prevent Server-Side Template Injection (SSTI).
- **Memory Protection**: Employs Caffeine Cache (max 1000 items) and `@Size` constraints (max 64KB) to prevent OOM errors.
- **Rate Limiting**: Implements Redis + Bucket4j throttling before authentication to block brute-force attacks.
- **Async Queues**: RabbitMQ Direct Exchange routing with dead-letter queue (DLQ) retry topologies.
- **JSON Security**: Uses MapStruct DTOs to hide passwords and prevent circular reference crashes.
- **Central Errors**: A global exception handler maps errors to JSON and hides JVM stack traces.
- **Swagger Docs**: Self-documenting endpoints pre-configured to accept JWT authorization.

---

## 🛠️ Tech Stack

- **Java**: 21
- **Framework**: Spring Boot 3.2.5
- **Database**: PostgreSQL (Hibernate JPA)
- **Broker**: RabbitMQ
- **Cache/Rate Limits**: Redis (Lettuce), Caffeine
- **API UI**: Swagger / Springdoc OpenAPI

---

## 🗺️ Roadmap

1. **Auth APIs**: Implement JWT registration and login endpoints.
2. **REST endpoints**: Implement CRUD APIs for projects, templates, and rules.
3. **Workers**: Implement consumers to send notifications via Email and SMS.
