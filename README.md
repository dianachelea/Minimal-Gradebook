# Minimal-Gradebook

# Academic Management System

This project is a role-based academic management system designed for managing students, grades, and disciplines within an academic environment. 
<img width="1919" height="506" alt="Screenshot 2025-08-13 183245" src="https://github.com/user-attachments/assets/017ec98d-c1f0-43dc-aa60-d647aa2827de" />
Professors can add, edit, and delete students, assign or update their grades for specific disciplines, and manage course information such as the year of study and the assigned professor. 
CRUD OPERATIONS FOR STUDENTS
<img width="1902" height="415" alt="Screenshot 2025-08-13 183223" src="https://github.com/user-attachments/assets/6c836be4-66ec-4612-a8b7-57c6b6bbae07" />
<img width="1918" height="703" alt="Screenshot 2025-08-13 183239" src="https://github.com/user-attachments/assets/ebf7cd14-a087-4c74-8481-c9b8a04fbd8c" />
CRUD OPERATIONS FOR DISCIPLINES
<img width="1917" height="494" alt="Screenshot 2025-08-13 183251" src="https://github.com/user-attachments/assets/0ba845c4-7344-42fe-8e63-1b0a7b09a92e" />
<img width="1919" height="446" alt="Screenshot 2025-08-13 183301" src="https://github.com/user-attachments/assets/d1ee3805-08e1-4685-b010-c94de6577326" />
Students can view other students along with their average grades, as well as browse the list of disciplines to see details like the year of study and the professor delivering the course.
<img width="1917" height="535" alt="Screenshot 2025-08-13 183315" src="https://github.com/user-attachments/assets/f520bc2d-3dc4-49e7-8a9b-9b0d25dd1784" />
<img width="783" height="508" alt="Screenshot 2025-08-13 183212" src="https://github.com/user-attachments/assets/82b37fe1-b15d-47ab-b1cc-d8ee2b7f08b3" />
<img width="1919" height="564" alt="Screenshot 2025-08-13 183325" src="https://github.com/user-attachments/assets/9ed12fb4-3149-4823-9b67-86c10e682589" />
<img width="1919" height="697" alt="Screenshot 2025-08-13 183331" src="https://github.com/user-attachments/assets/d5bcbafc-2e98-4936-b6b1-1aaac3ea3bcc" />

The system stores all academic data, including students, grades, and disciplines, in **MongoDB**, while authentication and authorization are handled through a separate **gRPC**-based service with user credentials and roles stored in **MySQL**. This means that the IDs of authenticated users are not directly linked to the student records in MongoDB, ensuring a clear separation between authentication and academic data.

The backend is implemented in **Spring Boot**, exposing REST API endpoints for academic data management, while authentication is validated via gRPC. The frontend is built with **React**, providing an interactive and responsive interface for both professors and students. Development is streamlined using **MapStruct** for DTO mappings, **Lombok** for boilerplate reduction, **Spring Data** for database operations, and **Spring HATEOAS** for building hypermedia-driven APIs.

## Tech Stack

- **Backend:** Spring Boot, Java, gRPC
- **Frontend:** React
- **Databases:** MongoDB (academic data), MySQL (authentication)
- **Libraries & Tools:** MapStruct, Lombok, Spring Data, Spring HATEOAS

## Getting Started

To run the application locally, set up both MongoDB and MySQL instances, start the gRPC authentication service, then launch the Spring Boot backend and React frontend.
