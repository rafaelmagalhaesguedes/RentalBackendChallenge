# Rental Car System - Backend

This project encompasses a robust API for managing clients, reservations, accessory rentals, and vehicle groups within a car rental service. It features integration with the Stripe payment gateway for secure online transactions. Additionally, the system includes a microservices architecture for efficient email communication, utilizing message queues for enhanced reliability and scalability.

## Main Features
- Authentication and Authorization: User authentication and role-based authorization (ADMIN, MANAGER, and USER).

- Reservation Management: Register and manage accessory reservations for individuals or vehicle groups.

- Person Management: Create, list, update, and delete information about individuals, including clients and employees.

- Accessory Management: Create, list, update, and delete accessories available for rental.

- Vehicle Group Management: Create, list, update, and delete vehicle groups.

- Stripe Integration: Allows users to pay for reservations using credit/debit cards through Stripe.

## Email Sending Microservice
The system includes a microservice for sending emails, utilizing queues for asynchronous processing and ensuring email delivery. The microservice is responsible for:

- Asynchronous Email Sending: Sending emails asynchronously to avoid blocking during the execution of other tasks.

- Failure Handling: Robust and efficient handling of email sending failures.

- RabbitMQ Configuration: Using RabbitMQ for queue management and ensuring email delivery.

- Uses Cloud AMQP: Provides cloud-hosted messaging infrastructure, ensuring high availability, scalability, and performance for RabbitMQ queues.

## Technologies Used
- Spring Boot: Framework for developing Java applications.

- Spring AMQP (RabbitMQ): Integration with RabbitMQ for asynchronous email sending.

- JUnit 5: Unit and integration testing.

- Stripe API: Integration with the Stripe API for payment processing.

- SpringDoc OpenAPI: API documented with SpringDoc OpenAPI Swagger.

- NGINX: Web server and reverse proxy.

- Docker: Services use Docker containers for their lifecycle.

## Running the Application with Docker
### Prerequisites
Ensure Docker is installed on your machine.

1. Clone the Repository
2. Configure environment variables if needed
3. Build images and start containers:
  ```
  docker-compose up -d
  ```
4. Access the application at http://localhost:8080

### Main Endpoints

#### Authentication

- **Login:**
  - **POST /auth/login**
  - Request Body:
    ```json
    {
      "email": "user@email.com",
      "password": "password123"
    }
    ```
  - Response:
    ```json
    {
      "token": "jwt-token"
    }
    ```

#### Users

- **Create User:**
  - **POST /persons**
  - Request Body:
    ```json
    {
      "fullName": "User Test", 
      "username": "user123",
      "email": "user@example.com",
      "password": "password123",
      "role": "USER"
    }
    ```
  - Response:
    ```json
    {
      "id": "uuid",
      "fullName": "User Test",
      "username": "user123",
      "email": "user@example.com",
      "role": "USER"
    }
    ```

#### Reservations

- **List Reservations:**
  - **GET /reservations**
  - Query Parameters:
    - `pageNumber`: Page number (default: 0)
    - `pageSize`: Page size (default: 10)
    - Response:
    ```json
     [
      {
        "id": "uuid",
        "person": {
          "id": "uuid",
          "fullName": "User test",
          "username": "user",
          "email": "user@example.com",
          "role": "USER"
        },
        "group": {
          "id": "uuid",
          "name": "Group GX",
          "vehicles": "Renegade, Creta, Duster, Toro or similar",
          "dailyRate": 250.0
        },
        "accessories": [
          {
            "id": "uuid",
            "name": "GPS",
            "description": "SmartPhone com GPS",
            "quantity": 1,
            "dailyRate": 100.0
          },
          {
            "id": "uuid",
            "name": "Baby comfort",
            "description": "Comfort plus ergonomic top",
            "quantity": 1,
            "dailyRate": 100.0
          }
        ],
        "pickupDateTime": "2024-10-01T08:00:00",
        "returnDateTime": "2024-11-05T10:00:00",
        "totalAmount": 15750.0,
        "totalDays": 35,
        "reservationStatus": "PENDING",
        "paymentType": "Online",
        "createdDate": "2024-06-30T20:28:30.783332566",
      }
     ]
    ```

- **Create Reservation:**
  - **POST /reservations**
  - Request Body:
    ```json
    {
      "personId": "uuid",
      "groupId": "uuid",
      "accessoryIds": ["uuid", "uuid"],
      "pickupDateTime": "2024-10-01T08:00:00",
      "returnDateTime": "2024-11-05T10:00:00",
      "totalAmount": 15750.00,
      "paymentType": "Online"
    }
    ```
  - Response:
    ```json
    {
      "id": "uuid",
      "person": {
        "id": "uuid",
        "fullName": "User test",
        "username": "user",
        "email": "user@example.com",
        "role": "USER"
      },
      "group": {
        "id": "uuid",
        "name": "Group GX",
        "vehicles": "Renegade, Creta, Duster, Toro or similar",
        "dailyRate": 250.0
      },
      "accessories": [
        {
          "id": "uuid",
          "name": "GPS",
          "description": "SmartPhone with GPS",
          "quantity": 1,
          "dailyRate": 100.0
        },
        {
          "id": "uuid",
          "name": "Baby comfort",
          "description": "Comfort plus ergonomic top.",
          "quantity": 1,
          "dailyRate": 100.0
        }
      ],
      "pickupDateTime": "2024-10-01T08:00:00",
      "returnDateTime": "2024-11-05T10:00:00",
      "totalAmount": 15750.0,
      "totalDays": 35,
      "reservationStatus": "PENDING",
      "paymentType": "Online",
      "createdDate": "2024-06-30T20:28:30.783332566",
      "paymentUrl": "https://checkout.stripe.com/c/pay/cs_test_a1DWuVtXutyIcEyCPFOK2XiLoixgdRP7snfmN5fSmbsGleR0aWIwJIvseY#fidkdWxOYHwnPyd1blpxYHZxWjA0SmtVTWFCaT1WXHY8X2BnX0dIQ31pT0o8cTZVS3BRSUNqYTdAd2N0dDdoQmRWdjBOazRUU2tvSWtEV21Ka1Vyc3Q2XDZ%2FbUNvQGhLPVw2QENtQHdIdElDNTVhfFV9a21SQCcpJ2N3amhWYHdzYHcnP3F3cGApJ2lkfGpwcVF8dWAnPyd2bGtiaWBabHFgaCcpJ2BrZGdpYFVpZGZgbWppYWB3dic%2FcXdwYHgl"
    }
    ```

- **Payment Successfully:**
  - **GET /payment/success/{paymentId}**
  
  - Response:
    ```json
    {
      "id": "5c1ad591-5060-497a-985f-80899f32d3b5",
      "paymentStatus": "CONFIRMED",
      "paymentDate": "2024-07-03T12:37:52.923245"
    }
    ```

- **Payment Failed:**
  - **GET /payment/cancel/{paymentId}**

  - Response:
    ```json
    {
      "id": "5c1ad591-5060-497a-985f-80899f32d3b5",
      "paymentStatus": "CANCEL",
      "paymentDate": "2024-07-03T12:37:52.923245"
    }
    ```
