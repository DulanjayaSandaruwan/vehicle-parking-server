# Vehicle Parking Application - Backend

## Overview

This is the backend for the Vehicle Parking Application, built using Spring Boot. The backend provides RESTful APIs for user authentication, vehicle category management, seasonal discounts, and parking invoicing.

## Features

### 1. User Authentication
- Secure user registration and login using Spring Security.
- Passwords are securely hashed using BCrypt.
- Authenticated users can access parking invoicing features.

### 2. Vehicle Categories
- Supports multiple vehicle categories (e.g., Car, Motorcycle, Truck) with configurable per hour pricing.
- Admin interface for managing vehicle categories.

### 3. Seasonal Discounts
- Allows the setup of seasonal discounts, which can be applied to parking fees.
- Discounts can be configured based on the time of year or special events.

### 4. Parking Invoicing
- Calculates and generates invoices for parked vehicles.
- Includes vehicle category, parking duration, base price, applied discounts, and total amount due in the invoice.
- Invoices are stored in the database and can be retrieved via APIs.

## Technologies Used

- **Spring Boot**: Main framework for building the backend.
- **Spring Security**: For authentication and authorization.
- **Spring Data JPA**: For database interaction.
- **Hibernate**: ORM tool used with Spring Data JPA.
- **MySQL**: Database used for persisting data.

## Getting Started

### Prerequisites
- **Java 11** or higher
- **MySQL** database
- **Gradle** for project management and build

### Setting Up the Backend

1. **Clone the repository:**
   ```bash
   git clone https://github.com/DulanjayaSandaruwan/vehicle-parking-server.git
   cd car-parking-invoicing/backend
   ```

2. **Set up the database:**
    - Create a MySQL database named `parking-management-system`.
    - Update the `application.properties` file with your MySQL username and password.
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/parking-management-system
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build and run the backend:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   The backend will start on `http://localhost:8080`.

## License

This project is licensed under the MIT License - see the [LICENSE](../LICENSE) file for details.
