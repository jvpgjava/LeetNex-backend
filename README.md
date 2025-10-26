# LeetNex Backend

Backend system for LeetNex platform - A technical interview preparation platform for developers and students.

## Overview

LeetNex is a comprehensive platform designed to help developers and students prepare for technical interviews through practice problems, coding challenges, and session tracking. This backend provides RESTful APIs to support the frontend application with user management, problem management, code execution, and analytics.

## Technology Stack

- **Java** 17
- **Spring Boot** 3.2.0
- **Spring Security** - Authentication and authorization
- **JPA/Hibernate** - Object-relational mapping
- **PostgreSQL** - Database
- **Redis** - Caching
- **JWT** - Token-based authentication
- **MapStruct** - DTO mapping
- **Maven** - Dependency management
- **WebRTC** - Real-time communication support

## Features

### User Management
- User registration and authentication
- JWT-based secure authentication
- Role-based access control (Admin, User)
- User profiles and progress tracking

### Problem Management
- CRUD operations for coding problems
- Support for multiple programming languages
- Problem difficulty levels (Easy, Medium, Hard)
- Problem categories (Arrays, Strings, Dynamic Programming, etc.)
- Example test cases and constraints

### Code Execution
- Multi-language code execution support
- Sandboxed code execution environment
- Test case validation
- Runtime and memory usage tracking

### Practice Sessions
- Timer functionality for practice sessions
- Camera integration support for monitoring
- Session recording and history
- Submission tracking

### Analytics
- User statistics and progress tracking
- Submission history and analysis
- Performance metrics
- Leaderboard support

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/leetnex/
│   │   │   ├── config/              # Configuration classes
│   │   │   ├── controller/          # REST controllers
│   │   │   ├── dto/                 # Data transfer objects
│   │   │   ├── mapper/              # MapStruct mappers
│   │   │   ├── model/               # Entity models
│   │   │   ├── repository/          # Data access layer
│   │   │   ├── security/            # Security configuration
│   │   │   ├── service/             # Business logic
│   │   │   └── util/                # Utility classes
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/        # Database migrations
│   └── test/
│       └── java/com/leetnex/
└── pom.xml
```

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Redis (optional, for caching)

## Configuration

### Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE leetnex;
```

2. Update `application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/leetnex
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### JWT Configuration

Configure JWT secret in `application.properties`:
```properties
jwt.secret=your-secret-key-here
jwt.expiration=86400000
```

## Running the Application

1. Clone the repository:
```bash
git clone <repository-url>
cd backend
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh access token

### Users
- `GET /api/users/me` - Get current user profile
- `PUT /api/users/me` - Update user profile
- `GET /api/users/{id}` - Get user by ID (Admin)

### Problems
- `GET /api/problems` - Get all problems
- `GET /api/problems/{id}` - Get problem by ID
- `POST /api/problems` - Create problem (Admin)
- `PUT /api/problems/{id}` - Update problem (Admin)
- `DELETE /api/problems/{id}` - Delete problem (Admin)

### Submissions
- `POST /api/submissions` - Submit code solution
- `GET /api/submissions` - Get user submissions
- `GET /api/submissions/{id}` - Get submission by ID

### Sessions
- `GET /api/sessions` - Get user practice sessions
- `POST /api/sessions` - Start a new practice session
- `PUT /api/sessions/{id}` - Update session
- `DELETE /api/sessions/{id}` - End session

## Testing

Run all tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=YourTestClass
```

## Database Migrations

The project uses Flyway for database migrations. Migrations are located in `src/main/resources/db/migration/`

## Security

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control
- CORS configuration
- SQL injection prevention through JPA

## Development Guidelines

### Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Keep methods focused and single-purpose

### Database
- Use JPA entities for database mapping
- Always use DTOs for API responses
- Avoid exposing entity objects directly
- Use transactions appropriately

### Error Handling
- Implement global exception handling
- Return meaningful error messages
- Log errors appropriately
- Use appropriate HTTP status codes

## Contributing

When contributing to this project:

1. Create a feature branch from `main`
2. Make your changes
3. Write or update tests as needed
4. Ensure all tests pass
5. Submit a pull request

## License

See LICENSE file for details.

## Contact

For questions or support, please contact the development team.

## Version History

- **1.0.0** - Initial release with core features

