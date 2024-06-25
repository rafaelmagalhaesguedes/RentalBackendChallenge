# Rental Service Application

This project is a Rental Service Application that manages customer reservations, payments, and inventory.
The application leverages Spring Boot for backend development, Hibernate for ORM, and Stripe for payment processing.

## Technologies Used

- **Java**: Programming language used for the backend development.
- **Spring Boot**: Framework used for building the backend REST API.
- **Hibernate**: ORM tool for managing database operations.
- **Stripe**: Payment processing service for handling online payments.
- **Thymeleaf**: Template engine for rendering web pages.
- **H2 Database**: In-memory database used for development and testing.
- **Maven**: Build automation tool for managing project dependencies.

## Project Structure

- **Controller**: Handles incoming HTTP requests and routes them to the appropriate service methods.
- **Service**: Contains the business logic of the application.
- **Repository**: Interfaces for database operations.
- **Entity**: Java classes representing the database entities.
- **DTO**: Data Transfer Objects for transferring data between layers.

## Features

- Customer management
- Group management
- Accessory management
- Reservation creation and retrieval
- Payment processing with Stripe
- Web pages for payment success and failure notifications

