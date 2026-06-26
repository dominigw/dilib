# 📚 Dilib - Library Management API

A lightweight RESTful backend application built using **Java** and the **Spring Boot** ecosystem.

This project manages a digital library catalog, tracking books, authors, and user rentals.

## 🚀 Core Features
* **Spring Boot REST API:** Standard web endpoints for managing books, authors, and transactions.
* **Spring Data JPA:** Relational database management using an in-memory database.
* **Spring Security:** Stateless JWT authentication with role-based access control (USER and ADMIN).
* **Robust Error Handling:** Global exception interception returning clean, standardized JSON errors.
* **Automated Testing:** Covered by both JUnit 5/Mockito Unit tests and MockMvc Integration tests.

## 🛠️ How to Run Locally

1. Clone this repository.
2. Open the project in your IDE (e.g., IntelliJ IDEA or Eclipse).
3. Run the application via your IDE or run the following command in your terminal:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Access the local database console in your browser at: `http://localhost:8080/h2-console`
