# Rental Service Application

## Overview

This project is a Rental Service Application designed to manage person reservations, payments, and inventory efficiently. The application leverages modern technologies and frameworks to provide a robust and scalable solution for rental services.

## Technologies Used

- **Java:** The primary programming language used for backend development.

- **Spring Boot:** A framework used for building the backend REST API, enabling rapid development and easy configuration.

- **Hibernate:** An ORM (Object-Relational Mapping) tool used for managing database operations seamlessly.

- **Stripe:** A payment processing service integrated to handle online payments securely and efficiently.

- **Thymeleaf:** A template engine used for rendering dynamic web pages and enhancing the user interface.

- **H2 Database:** An in-memory database utilized for development and testing, ensuring fast setup and execution.

- **Maven:** A build automation tool used for managing project dependencies and simplifying the build process.

- **RabbitMQ:** A messaging broker used for handling asynchronous communication, such as sending emails and notifications.

- **Spring Security and JWT:** Spring Security provides comprehensive security services for Java applications, and JWT (JSON Web Tokens) is used for secure user authentication and authorization.

## Features

- **User Management:** Register and manage user accounts with secure authentication and authorization.

- **Reservations:** Make, view, and manage reservations for rental items.

- **Payments:** Process payments seamlessly using Stripe.

- **Inventory Management:** Track and manage the availability of rental items.

- **Email Notifications:** Send email notifications for various actions such as registration and reservation confirmations using RabbitMQ.