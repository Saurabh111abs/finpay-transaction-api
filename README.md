# FinPay Transaction API

A production-grade Transaction Processing REST API built with Java and Spring Boot, simulating core fintech payment workflows.

## Features

- Process debit/credit transactions
- Idempotency key validation to prevent duplicate payments
- Insufficient funds detection
- Full audit trail with timestamps
- Global exception handling with proper HTTP status codes
- API documentation with Swagger UI

## Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA / Hibernate
- MySQL
- Swagger / OpenAPI 3.0
- JUnit 5 + Mockito
- Maven

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/v1/transactions/createTransaction | Process a new transaction |
| GET | /api/v1/transactions/getTransactions | Get all transactions |
| GET | /api/v1/transactions/{id} | Get transaction by ID |

## How to Run

1. Clone the repository
   git clone https://github.com/Saurabh111abs/finpay-transaction-api.git

2. Create MySQL database
   CREATE DATABASE finpay;

3. Update application.properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password

4. Run the application
   mvn spring-boot:run

5. Access Swagger UI
   http://localhost:8080/swagger-ui/index.html

## Sample Request

POST /api/v1/transactions/createTransaction

{
    "idempotencyKey": "txn-001",
    "senderId": "user-1",
    "receiverId": "user-2",
    "amount": 500.00,
    "currency": "INR"
}

## Sample Response

{
    "transactionId": "uuid-here",
    "status": "SUCCESS",
    "amount": 500.00,
    "currency": "INR",
    "senderId": "user-1",
    "receiverId": "user-2",
    "failureReason": null,
    "createdAt": "2026-05-22T01:17:32"
}

## Test Cases

- Successful transaction processing
- Duplicate transaction rejection (409 Conflict)
- Insufficient funds handling (422 Unprocessable Entity)
- Transaction not found (500)