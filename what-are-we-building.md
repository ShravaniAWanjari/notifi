# Event-Driven Notification Platform

## Objective

Build a production-quality notification platform capable of delivering notifications through multiple channels.

This is NOT a CRUD application.

The system should demonstrate scalable backend architecture, asynchronous processing, retries, fault tolerance, and clean API design.

---

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Security
- PostgreSQL
- Redis
- RabbitMQ
- Docker Compose
- JUnit 5
- Testcontainers
- OpenAPI / Swagger

---

## Functional Requirements

Users should be able to:

- Register/Login
- Create Projects
- Create Notification Templates
- Create Notification Rules
- Send Notifications

Supported notification channels:

- Email (mock implementation)
- SMS (mock implementation)
- Webhook
- In-App notifications

---

## Architecture

The project must use:

Controller
↓

Service
↓

Repository

Event Driven Processing

RabbitMQ

Notification Queue

Worker Services

Retry Queue

Dead Letter Queue

---

## Features

Authentication

- JWT Authentication
- Role Based Access

Notifications

- Immediate notifications
- Scheduled notifications
- Retry failed notifications
- Notification history
- Delivery status

Redis

Use Redis for:

- Rate limiting
- Caching templates
- Session management

---

## Monitoring

Expose Prometheus metrics

Track

- queue size
- notifications sent
- failures
- retry count
- average processing latency

---

## Testing

Write

- Unit Tests
- Integration Tests
- Repository Tests

Use Testcontainers.

---

## Docker

Provide

docker-compose.yml

Should start

- API
- PostgreSQL
- RabbitMQ
- Redis

using one command.

---

## Documentation

Include

Architecture Diagram

ER Diagram

Sequence Diagram

README

API Documentation

Deployment Guide

---

## Quality Requirements

Follow SOLID

Use DTOs

Use Global Exception Handling

Validation

Logging

Pagination

Filtering

Sorting

No generated code.

Write production-quality code.
