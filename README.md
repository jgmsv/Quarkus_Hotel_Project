# Quarkus Project - Hotel Reservations

This is a Java-based project that utilizes the Quarkus framework and Maven for dependency management. The project is structured around a hotel management system, with specific focus on handling various exceptions and error messages.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java Development Kit (JDK)
- IntelliJ IDEA 2023.2.2 or any preferred IDE
- Maven

### Installing

1. Clone the repository to your local machine.
2. Open the project in your IDE.
3. Run the Maven install command to download the necessary dependencies.

## Project Structure

The project is structured into different packages, each serving a specific purpose:

1. aspects: This package contains aspect-oriented programming components, which are used to modularize cross-cutting concerns in Quarkus.  
2. controller: This package contains the controllers of your application, which handle incoming HTTP requests and return responses.  
3. converter: This package contains converter classes, which are used to convert one type to another, often used in the context of transforming domain objects to DTOs and vice versa.  
4. dto: This package contains Data Transfer Objects (DTOs), which are used to transfer data between processes or API calls.  
5. model: This package contains the application model classes, which represent the application data.  
6. repository: This package contains the repository classes, which handle data access and storage.  
7. service: This package contains the service classes, which contain business logic and call methods in the repository classes.  
8. util: This package contains utility classes, which provide exception methods and constants with app messages that can be used throughout the application.

## Built With

- [Java](https://www.java.com/) - The programming language used.
- [Quarkus](https://quarkus.io/) - The framework used.
- [Maven](https://maven.apache.org/) - Dependency Management.

## Authors

- jgmsv
