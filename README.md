# Library Management System

A modern, containerized **RESTful API** application for managing a library system, built with **Java 21** and **Spring Boot 3**. This project demonstrates best practices in backend development, including multi-layered architecture, secure authentication, containerization, and unit testing.

## Features

* **User Management & Authentication**: Secure registration and login using **JWT (JSON Web Tokens)**. Role-based access control (e.g., `USER`, `ADMIN`).
* **Book & Category Management**: CRUD operations for books and categories. Admin-only privileges for modifying the library's catalog.
* **Loan System**: Users can reserve, borrow, and return books. Built-in business logic (e.g., max 10 active loans per user).
* **DTO Pattern & Mapping**: Strict separation of database entities and API payload using Data Transfer Objects and the Builder pattern to prevent infinite recursion and data exposure.
* **Global Exception Handling**: Centralized error handling using `@ControllerAdvice` to provide clean, consistent JSON error responses.
* **Frontend UI**: Built-in vanilla HTML/JS/CSS frontend served statically by Spring Boot to easily test and interact with the API.

## Tech Stack

* **Language**: Java 21
* **Framework**: Spring Boot 3.4.0 (Spring Web, Spring Security, Spring Data JPA)
* **Database**: MySQL (Production/Docker) & H2 (In-memory for local testing)
* **Security**: Spring Security + JWT (`io.jsonwebtoken`)
* **Testing**: JUnit 5, Mockito, MockMvc
* **Build Tool**: Gradle
* **Containerization**: Docker, Docker Compose

## Quick Start (Recommended)

The easiest way to run the application is via Docker. This will automatically spin up the MySQL database and the Spring Boot application.

1. Ensure you have [Docker](https://www.docker.com/) installed and running.
2. Open your terminal in the root directory of the project.
3. Run the following command:

```bash
docker-compose up --build
```

4. Once the containers are running, open your browser and visit:
   **http://localhost:8080/**

## Local Development Setup

If you prefer to run the application locally without Docker (e.g., via IntelliJ IDEA):

1. The application uses an **H2 In-Memory Database** by default when run via Gradle.
2. Build and run the app:
   ```bash
   ./gradlew bootRun
   ```
3. Access the application at **http://localhost:8081/** (Port defined in `application.properties`).
4. **H2 Console** is available at `http://localhost:8081/h2-console` (JDBC URL: `jdbc:h2:./school`).

## Running Tests

The project includes comprehensive unit tests for Services and Controllers, utilizing **Mockito** and **MockMvc**. To run the test suite:

```bash
./gradlew test
```

## Architecture & Patterns Used

* **N-Tier Architecture**: Controller -> Service -> Repository.
* **DTO (Data Transfer Object)**: Decouples the presentation layer from the persistence layer.
* **Builder Pattern**: Used inside DTOs for cleaner and more maintainable object instantiation.
* **Mapper Pattern**: Manual mappers to translate between Entities and DTOs.
* **Multi-stage Docker Build**: Optimizes the final Docker image size by separating the build environment from the runtime environment.

## API Endpoints Overview

* **Auth**: `POST /account/login`, `POST /api/users` (Registration)
* **Books**: `GET /api/books`, `POST /api/books` (Admin), `PUT /api/books/{id}` (Admin), `DELETE /api/books/{id}` (Admin)
* **Categories**: `GET /api/categories`, `POST /api/categories` (Admin), `DELETE /api/categories/{id}` (Admin)
* **Loans**: `POST /api/loans/reserve`, `POST /api/loans/loan`, `POST /api/loans/return/{id}`, `GET /api/loans` (Admin)
