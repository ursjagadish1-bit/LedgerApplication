# LedgerApplication

## Event Ledger API

A Spring Boot application that accepts transaction events, ensures idempotency, orders events by timestamp, and computes account balances using an in-memory H2 database.

## Requirements

- Java 17
- Maven 3.8+

## Setup

From the project root:

```bash
mvn clean install
```

## Run

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Endpoints

- `POST /events`
- `GET /events/{eventId}`
- `GET /events?account={accountId}`
- `GET /accounts/{accountId}/balance`

## Swagger API documentation

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Tests

Run tests with:

```bash
mvn test
```

## Notes

- Duplicate `eventId` submissions are idempotent: the original event is returned and the balance does not change.
- Event listings are always sorted by `eventTimestamp`.
- Balance is computed as `sum(CREDIT) - sum(DEBIT)`.
- An in-memory H2 database is used with no external dependencies.
