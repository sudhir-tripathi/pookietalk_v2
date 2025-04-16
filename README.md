# PookieTalk

PookieTalk is a modern real-time chat application built with Spring Boot backend and React frontend. It features a scalable, secure, and maintainable architecture with comprehensive monitoring and deployment capabilities.

## Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Development Environment Setup](#development-environment-setup)
  - [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Deployment](#deployment)
- [Monitoring](#monitoring)

## Features

- Real-time chat functionality using WebSocket
- User authentication and authorization with JWT
- Message persistence with PostgreSQL
- Caching with Redis
- Event-driven architecture using Kafka
- Comprehensive monitoring with Prometheus and Grafana
- Progressive Web App (PWA) support
- Responsive and modern UI

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.1.0
- Spring Security
- Spring WebSocket
- Spring Data JPA
- PostgreSQL
- Redis
- Apache Kafka
- JWT Authentication
- Flyway for database migrations

### Frontend
- React 18
- TypeScript
- Redux Toolkit
- React Query
- Socket.io Client
- Vite
- PWA support
- Jest & Testing Library
- Cypress for E2E testing

### DevOps & Monitoring
- Docker
- Docker Compose
- Prometheus
- Grafana

## Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- Docker and Docker Compose
- Git

## Getting Started

### Development Environment Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd pookietalk
```

2. Start the infrastructure services:
```bash
docker-compose up -d postgres redis zookeeper kafka prometheus grafana
```

3. Backend setup:
```bash
cd backend
./gradlew build
```

4. Frontend setup:
```bash
cd frontend
npm install
```

### Running the Application

1. Start the backend:
```bash
cd backend
./gradlew bootRun
```

2. Start the frontend development server:
```bash
cd frontend
npm run dev
```

The application will be available at:
- Frontend: http://localhost:5173
- Backend API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

## Project Structure

### Backend Structure
```
/backend/
├── /src/main/java/com/pookietalk/
    ├── /aop/                    # Aspect-Oriented Programming
    ├── /config/                 # Application configurations
    ├── /controller/            
    ├── /dto/                    
    ├── /event/                 # Event-driven components
    ├── /exception/             # Custom exceptions
    ├── /mapper/                # Object mappers
    ├── /model/
    ├── /repository/
    ├── /service/
    ├── /validation/           
    └── /security/            
```

### Frontend Structure
```
/frontend/
├── /src/
    ├── /api/                  # API client layer
    ├── /components/
    ├── /config/              
    ├── /constants/           
    ├── /hooks/               
    ├── /store/              
    ├── /types/              
    ├── /utils/
    └── /features/          
```

## Testing

### Backend Testing
```bash
cd backend
./gradlew test
```

### Frontend Testing
```bash
cd frontend
# Unit tests
npm run test

# E2E tests
npm run test:e2e

# Component documentation
npm run storybook
```

## Deployment

The application is containerized and can be deployed using Docker Compose:

```bash
# Build and start all services
docker-compose up -d
```

## Monitoring

- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000 (admin/admin)

### Available Metrics
- Application metrics via Spring Boot Actuator
- JVM metrics
- Custom business metrics
- Infrastructure metrics

## Security Considerations

- JWT-based authentication
- CORS configuration
- Rate limiting
- Input validation
- XSS protection
- CSRF protection
- Security headers

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.