# Minimal-Gradebook

# Academic Management System

This project is a role-based academic management system designed for managing students, grades, and disciplines within an academic environment. Professors can add, edit, and delete students, assign or update their grades for specific disciplines, and manage course information such as the year of study and the assigned professor. Students can view other students along with their average grades, as well as browse the list of disciplines to see details like the year of study and the professor delivering the course.

The system stores all academic data, including students, grades, and disciplines, in **MongoDB**, while authentication and authorization are handled through a separate **gRPC**-based service with user credentials and roles stored in **MySQL**. This means that the IDs of authenticated users are not directly linked to the student records in MongoDB, ensuring a clear separation between authentication and academic data.

The backend is implemented in **Spring Boot**, exposing REST API endpoints for academic data management, while authentication is validated via gRPC. The frontend is built with **React**, providing an interactive and responsive interface for both professors and students. Development is streamlined using **MapStruct** for DTO mappings, **Lombok** for boilerplate reduction, **Spring Data** for database operations, and **Spring HATEOAS** for building hypermedia-driven APIs.

## Tech Stack

- **Backend:** Spring Boot, Java, gRPC
- **Frontend:** React
- **Databases:** MongoDB (academic data), MySQL (authentication)
- **Libraries & Tools:** MapStruct, Lombok, Spring Data, Spring HATEOAS

## Getting Started

To run the application locally, set up both MongoDB and MySQL instances, start the gRPC authentication service, then launch the Spring Boot backend and React frontend.
