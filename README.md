# FinTechApp - Backend API

## Introduction
FinTechApp is a backend API built using Spring Boot that provides core financial services such as user account management, transactions, and balance tracking. This project is designed to be a foundation for fintech applications, allowing developers to integrate financial functionalities into their systems seamlessly.

This README provides an overview of the project, setup instructions, and detailed API documentation to help you get started.

---

## Features
- **User Account Management**: Create, and update user accounts.
- **Transaction Handling**: Perform transactions between user accounts.
- **Balance Tracking**: Retrieve account balances.
- **Secure Authentication**: JWT-based authentication for secure API access.
- **Swagger UI**: Interactive API documentation for easy exploration.

---

## Prerequisites
Before you begin, ensure you have the following installed:
- Java Development Kit (JDK) 17 or higher
- Apache Maven
- MySQL or any preferred database
- Postman or any API testing tool (optional)

---

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/ZilaidAwwab/FinTechApp.git
cd FinTechApp
```
### 2. Configure the Database
- Create a MySQL database named fintech_db.
- Update the application.properties file with your database credentials:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/fintech_db
spring.datasource.username=your-username
spring.datasource.password=your-password
```
### 3. Build and Run the Application
```bash
mvn clean install
mvn spring-boot:run
```
## API Documentation
### Swagger UI
- For detailed and interactive API documentation, run the project and visit the Swagger UI at:
```bash
http://localhost:8080/swagger-ui/index.html#/
```
Swagger UI provides a user-friendly interface to explore all available endpoints, request/response models, and test APIs directly from the browser.

## Base URL
All API endpoints are prefixed with /api/user.

## Authentication
This API uses JWT for authentication. Include the token in the Authorization header for secured endpoints:
```bash
Authorization: Bearer <your-token>
```
## Contributing
We welcome contributions! If you'd like to contribute to FinTechApp, please follow these steps:

- Fork the repository.
- Create a new branch for your feature or bugfix.
- Submit a pull request with a detailed description of your changes.

## Support
For any questions or issues, please open an issue on the GitHub repository.
