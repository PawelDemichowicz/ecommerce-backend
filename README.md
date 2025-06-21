# 🛒 E-commerce Backend Application

🔗 **Live Swagger UI:** [https://ecommerce-backend-paweldemichowicz.koyeb.app/ecommerce/swagger-ui/index.html#/](https://ecommerce-backend-paweldemichowicz.koyeb.app/ecommerce/swagger-ui/index.html#/)

This is a backend application for an e-commerce platform built with **Spring Boot** and **PostgreSQL**. It provides a secure REST API for managing users, products, shopping carts, and orders, with role-based access for customers and administrators.

## 🎯 Project Goal

The goal of this project is to create a robust and secure backend for an online store. Key features include:

- 🔐 User registration, authentication (JWT), and role-based authorization (admin & customer).
- 📦 Product management with full CRUD operations.
- 🛒 Shopping cart functionality.
- 🧾 Order placement and order history per user.
- 🧑‍💼 Admin panel for managing users and products.
- 📑 API documentation with Swagger/OpenAPI.
- 🧪 Unit and integration tests for controllers and services.
- 🐳 Dockerized environment for easy development and testing.

## 🧰 Tech Stack

- **Java 17**
- **Spring Boot 3** (REST, Validation, Security)
- **PostgreSQL** (via Testcontainers in tests)
- **Spring Security & JWT** (authentication and authorization)
- **Hibernate & JPA** (ORM and database interaction)
- **Flyway** (database migrations)
- **Lombok** (boilerplate reduction)
- **MapStruct** (DTO <-> domain <-> entity mapping)
- **Docker** (local development environment)
- **JUnit 5**, **Mockito**, **Testcontainers**, **RestAssured** (testing)

## ▶️ Run Locally with Docker

Make sure you have Docker and Docker Compose installed.

### 1. Create a `.env` file in the root directory

```env
DB_URL=jdbc:postgresql://postgresql:5432/ecommerce
DB_USERNAME=your_username
DB_PASSWORD=your_password
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000
```

> These variables are injected into the container at runtime.

### 2. Build the JAR

```bash
./gradlew bootJar
```

### 3. Start the application

```bash
docker compose up --build
```

> Using `--build` ensures Docker always builds a fresh image based on your current code.

This will start:
- Spring Boot application on **`localhost:8190/ecommerce`**
- PostgreSQL database container

## 🧪 Running Tests

You can run the tests locally with:

```bash
./gradlew test
```

This will start PostgreSQL using Testcontainers and execute all unit and integration tests.

## 📘 API Documentation

Swagger UI is available at:  
**`http://localhost:8190/ecommerce/swagger-ui/index.html`**

## 📁 Project Structure

```
src/main/java/com/ecommerce
├── api
│   ├── controller       # REST controllers & global exception handler
│   └── dto              # Data Transfer Objects & mappers dtos <-> domain
├── business
│   ├── domain           # Domain models
│   ├── exception        # Business-layer exceptions
│   └── service          # Application services
├── configuration        # Spring/OpenAPI configuration
├── database
│   ├── entity           # JPA entities & mappers entities <-> domain
│   └── repository       # Spring Data JPA repositories
├── security             # JWT-based authentication & authorization
└── EcommerceBackendApplication.java
```

## ✅ Status

Project is actively developed. MVP version includes:
- Secure authentication and role-based access control
- Product and order API
- Cart functionality
- Global error handling
- High test coverage
- Clean architecture and modular structure