# Jobly — Smart Job Hunting Platform

A polished Java 21 course project for a smart job-hunting platform. The original OOP/UML-driven domain model is preserved, while the project now includes a modern desktop dashboard, service validation, matching, analytics, application tracking, and automated tests.

## Highlights

- Modern **Swing desktop UI** with separate Job Seeker, Employer, and Admin workspaces
- Job discovery and search with skill/salary **match scores**
- Application tracking with status history and duplicate-application protection
- Resume management and rule-based salary suggestions
- Employer candidate review and application-status updates
- Admin market analytics, user management, and reporting
- In-memory repository layer, keeping the architecture easy to understand and demo
- JUnit 5 test coverage for core service behavior
- CLI mode retained for backward compatibility

## Requirements

- JDK 21+
- Maven 3.9+ (recommended)

## Run the desktop app

```bash
mvn clean test
mvn exec:java
```

Or build the runnable JAR:

```bash
mvn clean package
java -jar target/job-platform-1.0-SNAPSHOT.jar
```

## Demo accounts

| Role | Username | Password |
|---|---|---|
| Job Seeker | `seeker` | `seek123` |
| Employer | `employer` | `emp123` |
| Admin | `admin` | `admin123` |

## CLI mode

```bash
java -jar target/job-platform-1.0-SNAPSHOT.jar --cli
```

## Architecture

```text
jobportal/
├── config/              # Application composition / demo bootstrap
├── domain/              # Core entities and value objects
├── interfaces/          # OOP contracts from the original UML design
├── repository/          # Repository abstractions
│   └── memory/          # In-memory implementations
├── service/             # Business logic / use cases
└── presentation/
    ├── cli/             # Legacy console interface
    └── gui/             # Modern Swing dashboard
```

The app intentionally uses in-memory persistence to keep the focus on OOP, architecture, UML alignment, and demonstrable product behavior. The repository abstractions make it straightforward to replace this layer with a database later.

## Engineering improvements

- Removed reflection-based resume ID assignment
- Added registration and job-posting input validation
- Prevented duplicate applications to the same job
- Added a central `ApplicationContext` composition root
- Added executable Maven configuration
- Corrected the documented entry point and run instructions

## Project documents

The original UML, sequence, use-case, and state diagrams are kept in `diagrams/`, along with the course PDFs in the repository root.
