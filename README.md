# 📝 Notes & Task Management API

A robust, secure, and production-ready **RESTful Web API** built with **Spring Boot 3**, implementing full authentication, task/flashcard management, pagination, filtering, and OpenAPI (Swagger) documentation.

---

## 🛠️ Tech Stack & Architecture

* **Language:** Java 17+
* **Framework:** Spring Boot 3
* **Security:** Spring Security, JWT (JSON Web Tokens)
* **Database & ORM:** MySQL, Spring Data JPA, Hibernate
* **DTO Mapping:** Manual / ModelMapper (Data Transfer Objects for clean request/response logic)
* **Validation & Exception Handling:** Jakarta Bean Validation, `@ControllerAdvice` (Global Exception Handling)
* **API Documentation:** Swagger UI / OpenAPI 3.0
* **Build Tool:** Maven

---

## ✨ Key Features

* 🔐 **Authentication & Authorization:** Secure user registration, JWT-based stateless login, and protected endpoints.
* 📝 **Notes Management:** Complete CRUD operations for user notes with pagination, sorting, and search capabilities.
* 🎴 **Task & Flashcard Modules:** Expanded task tracking and flashcard functionality.
* 📑 **Pagination & Sorting:** Efficient database querying for handling large datasets.
* 🛡️ **Data Validation:** Strict DTO validation with descriptive custom error responses.
* 🌐 **Global Exception Handling:** Centralized exception handling layer ensuring consistent JSON error formats.
* 📖 **Interactive API Docs:** Fully integrated Swagger UI for easy API testing and endpoint exploration.

---

## 🚀 API Endpoints Overview

### 🔐 Authentication (`/api/v1/auth`)
| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/auth/register` | Register a new user | Public |
| `POST` | `/api/v1/auth/login` | Authenticate user & return JWT token | Public |

### 📝 Notes (`/api/v1/notes`)
| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/notes` | Get all notes (Supports `page`, `size`, `sortBy`, `search`) | Bearer Token |
| `GET` | `/api/v1/notes/{id}` | Get note by ID | Bearer Token |
| `POST` | `/api/v1/notes` | Create a new note | Bearer Token |
| `PUT` | `/api/v1/notes/{id}` | Update existing note | Bearer Token |
| `DELETE` | `/api/v1/notes/{id}` | Delete note by ID | Bearer Token |

---

## 📖 API Documentation (Swagger)

Project is documented using **OpenAPI 3.0**. Once the application is running locally, you can access the interactive Swagger UI at:

```text
http://localhost:8080/swagger-ui.html
⚙️ Getting Started
Prerequisites
JDK 17 or higher

MySQL Server

Maven

Environment Setup
Set up the following environment variables in your IDE or local environment:

DB_PASSWORD: Your MySQL database root password

JWT_SECRET: Secret key used for signing JWT tokens

Running Locally
Clone the repository:

Bash
git clone [https://github.com/GulbasM-JBD5/springboot-notes-api.git](https://github.com/GulbasM-JBD5/springboot-notes-api.git)
Navigate to the project directory:

Bash
cd springboot-notes-api
Build and run the project:

Bash
mvn spring-boot:run