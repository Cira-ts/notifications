# Customer Notification Management Microservice
A fully Functional Java Microservice with Spring Boot REST API for Managing Customer Notifications, Preferences and Contact Information. Ideal for Systems that Require Centralized Notification Management and CRUD Operations.

## Prerequisites
- Docker Desktop

## Installation
```bash
git clone https://github.com/Cira-ts/notifications.git
cd notifications
docker-compose up -d
```
This will start the PostgreSQL and application containers.
On first run, SQL scripts in the ./scripts folder will be executed automatically to initialize the database.

## API Documentation
The APIs for this project can be accessed through the Swagger UI at the following endpoint: 
http://localhost:8080/swagger/swagger-ui/index.html
You can use this interface to explore the available APIs, their request and response formats, and test them directly from your browser.

## Brief Documentation of Project's Structure and Design Choices
**Structure:**
The project follows a feature-based folder structure, where each major functionality such as customer management, address management, notifications and preferences is organized into separate, self-contained packages.
Within each package, related controllers, services, repositories and DTOs are grouped together to keep the codebase modular, organized, and easy to navigate.

**Design choices:**
- **Layered Architecture:** Clean separation between controllers, services, repositories, and DTOs to improve maintainability.
- **PostgreSQL Database:** Reliable and compatible with relational data, with indexed tables to ensure faster query performance.
- **Docker:** For easy setup, deployment, and consistent environments using Docker Compose.
- **Security:** Spring Security with JWT-based authentication protects all endpoints and ensures secure admin access.
- **Swagger Integration:** API documentation is provided for easy exploration and testing of endpoints.
- **Scalable Design:** To easily handle growth of customer data and integrate with other microservices or systems.


