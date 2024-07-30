# User Management API

A simple user management API built with Spring Boot, providing JWT-based authentication and authorization.

## Features

- User registration and authentication
- Role-based access control (Admin/User)
- JWT token generation and validation
- User listing and deletion (Admin only)
- Secure login and logout

## Technologies

- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- MySQL
- Lombok
- Log4j2

## Getting Started

### Prerequisites

- JDK 11 or higher
- MySQL Database
- Maven

### Configuration

1. **Database Setup**: Ensure MySQL is installed and running. Create a database named `user_management`.

2. **Application Properties**: Configure the database and JWT settings in `application.properties`:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/user_management?useSSL=false&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=root
    spring.jpa.hibernate.ddl-auto=none
    spring.jpa.show-sql=true

    jwt.token.validity=18000
    jwt.signing.key=signingkey
    jwt.authorities.key=role
    jwt.token.prefix=Bearer
    jwt.header.string=Authorization
    ```

### Build and Run

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/abhishekdsangani/user-management.git
    cd user-management
    ```

2. **Build the Project**:
    ```bash
    mvn clean install
    ```

3. **Run the Application**:
    ```bash
    mvn spring-boot:run
    ```

### Endpoints

- **Create User**: `POST /users/create`
    - Request Body: `UserDto`
    - Response: `UserData`

- **Authenticate User**: `POST /users/authenticate`
    - Request Body: `LoginUser`
    - Response: `AuthToken`

- **List Users**: `GET /users/list`
    - Requires Authentication

- **Delete User**: `DELETE /users/delete/{userId}`
    - Requires Admin Role

- **Logout User**: `POST /users/logout`
    - Requires Authentication

### Example Requests

**Create User**:
```bash
curl -X POST http://localhost:8080/users/create \
-H "Content-Type: application/json" \
-d '{"username": "john", "password": "password", "email": "john@example.com", "phone": "1234567890", "name": "John Doe"}'
```

**Authenticate User**:
```bash
curl -X POST http://localhost:8080/users/authenticate \
-H "Content-Type: application/json" \
-d '{"username": "john", "password": "password"}'
```

**List Users**:
```bash
curl -X GET http://localhost:8080/users/list \
-H "Authorization: Bearer <your-jwt-token>"
```

**Delete User**:
```bash
curl -X DELETE http://localhost:8080/users/delete/1 \
-H "Authorization: Bearer <your-jwt-token>"
```

**Logout User**:
```bash
curl -X POST http://localhost:8080/users/logout \
-H "Authorization: Bearer <your-jwt-token>"
```

### Admin User for Testing
For testing purposes, an admin user has been created with the following credentials:
- **Username**: `admin`
- **Password**: `admin`