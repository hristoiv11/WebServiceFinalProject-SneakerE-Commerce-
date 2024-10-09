Sneaker E-commerce - Microservices-based Architecture
-
This project is a microservices-based sneaker e-commerce platform.The system allows users to manage sneaker-related data, customer profiles, inventory, and purchases through independent microservices. The microservices architecture provides scalability, flexibility, and ease of maintenance by breaking down the application into multiple smaller services that can function independently.
The project follows Domain-Driven Design (DDD) principles and integrates with multiple databases, including both SQL (MySQL, PostgreSQL) MongoDB. Each microservice exposes RESTful APIs for performing CRUD operations.

FEATURES
-
- Microservices Architecture: The system is decomposed into smaller microservices, each managing a specific domain (e.g., Sneakers, Customers, Inventory, and Purchases). These services communicate via REST APIs.
- API Gateway: A centralized API gateway routes requests to the appropriate microservices, providing a single entry point for external clients.
- Aggregator Service: Gathers data from multiple microservices to provide a comprehensive view of different resources (e.g., sneakers and their inventory levels).
- Dockerized Deployment: All microservices, along with their databases, are containerized using Docker and orchestrated using Docker Compose.
- Database Integration: Each microservice uses its own database—MySQL, PostgreSQL, or MongoDB.
- Testing: Comprehensive unit and integration tests ensure that each microservice is functioning correctly. Jacoco is used to measure test coverage, with a goal of at least 90%.

Technologies Used
-

Backend
-
- Spring Boot
- MySQL/PostgreSQL/MongoDB
- Docker & Docker Compose
- JUnit & Mockito
- Jacoco

Microservices
-
- Sneakers Service: Manages CRUD operations for sneakers, including model details, price, and available stores.
- Customers Service: Handles customer information, including profile creation and retrieval.
- Inventory Service: Manages sneaker inventory, including stock levels and availability.
- Purchases Service: Processes customer purchases, managing orders and transactions.
