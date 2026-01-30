# Assignment 4: Freelance Marketplace System

### Author: Nursayat Bashan

## Description
This project is a console-based Java application.
The system is designed to manage a freelance marketplace and allows working with
clients, freelancers, and projects.

All data is stored in a PostgreSQL database.

## Functionality
The application supports:
- create, read, update, and delete operations for clients
- create, read, update, and delete operations for freelancers
- create, read, update, and delete operations for projects
- error handling using custom exceptions

## Technologies Used
- Java
- PostgreSQL
- JDBC

## Object-Oriented Programming
The project follows basic OOP principles:
- inheritance (Client and Freelancer extend BaseUser)
- polymorphism (payment logic is implemented using the Payable interface)
- encapsulation (business logic is separated into Controller, Service, and Repository layers)

## Database
PostgreSQL is used to store all application data.
Database interaction is implemented using JDBC.
All changes are saved to the database in real time.

## How to Run
1. Install and run PostgreSQL
2. Create a database named `freelanceDB`
3. Execute the `schema.sql` file from the `src/resources` directory
4. Update database credentials in `DatabaseConnection.java`
5. Run the `Main.java` file

## Screenshots
Screenshots demonstrating the application functionality are located in the
`docs/screenshots` directory:
- main menu
- CRUD operations for clients
- project management
