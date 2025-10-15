🧠 Fixamo – Smart Street Issue Reporting System

Empowering communities to make their streets better with the help of AI.
Built with Spring Boot, PostgreSQL, and AI integration, Fixamo helps citizens report, track, and resolve public issues efficiently.

🚀 Overview

Fixamo is an AI-powered web and mobile platform that allows users to report neighborhood issues such as potholes, broken lights, or water leaks.
The system automatically analyzes each report using AI to categorize, assess severity, and rewrite descriptions for clarity — making it easier for authorities to act faster.

The platform supports multiple user roles (Citizen, Authority, Admin), ensuring that reports are properly reviewed, managed, and resolved.

✨ Key Features
🧩 Core Functionality

Report management with image and location support

User management with detailed roles and permissions

Location-based issue tracking and filtering

Real-time report status updates

🤖 AI-Powered Capabilities

Smart report content rewriting for clarity

Automatic categorization and severity assessment

Keyword extraction for better search and analytics

AI-assisted suggestion system for authorities

👥 User Roles

Citizen – Create and view reports, manage profile

Authority – Review, assign, and resolve reports

Admin – Manage users, reports, categories, and platform settings

⚙️ User Status Management

Pending Verification – Waiting for email confirmation

Active – Verified and allowed to report

Suspended – Temporarily blocked

Deactivated – Account disabled by user or admin

Banned – Permanently removed from the system

🔐 Authentication & Security
🔑 JWT-Based Authentication

Secure login and signup with JWT tokens

Token refresh mechanism

Encrypted password storage

🧰 Security Features

Email verification via Gmail SMTP

CORS and CSRF configuration

Role-based access control for endpoints

Session and exception handling

🔒 Authentication Endpoints

/api/auth/register – Register new users

/api/auth/login – Login with email/password

/api/auth/refresh – Refresh JWT token

/api/auth/verify – Verify account via email

🧱 Technical Details
🧰 Tech Stack

Backend: Java 17, Spring Boot

Database: PostgreSQL

Security: JWT, Spring Security

AI Integration: OpenAI API for natural language processing

Deployment: Docker support for containerized environments

🗂️ Database Schema

Entities: User, Report, Category, Subcategory, Location, Attachment

Relationships: One-to-many between User and Report, Many-to-one between Category and Report


## 🏗️ **Main Source Structure (`src/main/java/com/fixmystreet/fixmystreet/`)**

### **1. 📋 Application Entry Point**

├── FixmystreetApplication.java          # Main Spring Boot application class
```

### **2. ⚙️ Configuration Package (`config/`)**
```
config/
├── AppConfig.java                      # Application configuration
├── RequestConfiguration.java          # Request handling configuration  
├── SwaggerConfig.java                  # API documentation setup
└── security/
    ├── JwtAuthenticationFilter.java    # JWT authentication filter
    └── JwtService.java                 # JWT token management
```

### **3. 🎮 Controllers Package (`controllers/`)**
```
controllers/
├── AuthController.java                 # Authentication endpoints
├── EmailVerificationTokenController.java # Email verification
├── ReportController.java                # Report management endpoints
└── UserController.java                 # User management endpoints
```

### **4. 📦 DTOs Package (`dtos/`) - Data Transfer Objects**
```
dtos/
├── AIReportDTO.java                    # AI-processed report data
├── ApiResponse/                        # API response wrappers
│   ├── ApiResponseDTO.java
│   └── ErrorResponseDTO.java
├── auth/                              # Authentication DTOs
│   ├── AuthResponseDTO.java
│   ├── LoginRequestDTO.java
│   ├── PasswordResetRequestDTO.java
│   ├── RefreshTokenRequestDTO.java
│   └── SignupRequestDTO.java
├── categories/
│   └── CategoryDTO.java               # Category data transfer
├── keywords/
│   └── KeywordDTO.java                # Keyword data transfer
├── locations/
│   └── LocationDTO.java               # Location data transfer
├── reportImage/
│   └── ReportImageDTO.java            # Report image data transfer
├── reports/                           # Report DTOs
│   ├── CreateReportDTO.java
│   ├── ReportResponseDTO.java
│   └── updateReportDTO.java
└── users/                             # User DTOs
    ├── UpdateUserDTO.java
    ├── UserSummaryDTO.java
    └── UserWithReportsDTO.java
```

### **5. 🚨 Exceptions Package (`exceptions/`)**
```
exceptions/
├── BadRequestException.java           # Bad request handling
├── GlobalExceptionHandler.java       # Global exception management
└── ResourceNotFoundException.java     # Resource not found handling
```

### **6. 🔄 Mappers Package (`mappers/`)**
```
mappers/
├── LocationMapper.java                # Location entity mapping
├── ReportImageMapper.java             # Report image mapping
├── ReportMapper.java                  # Report entity mapping
└── UserMapper.java                    # User entity mapping
```

### **7. 🗃️ Model Package (`model/`) - Entity Models**
```
model/
├── Category.java                      # Report category entity
├── Keyword.java                       # Report keyword entity
├── Location.java                      # Location entity
├── Report.java                        # Main report entity
├── ReportImage.java                   # Report image entity
├── Token.java                         # JWT token entity
├── User.java                          # User entity
└── enums/                            # Enumerations
    ├── EmailVerificationToken.java    # Email verification enum
    ├── Role.java                      # User roles (CITIZEN, ADMIN, AUTHORITY)
    └── Status.java                    # User status (PENDING, ACTIVE, etc.)
```

### **8. 🗄️ Repository Package (`repository/`) - Data Access Layer**
```
repository/
├── CategoryRepository.java            # Category data access
├── EmailVerificationTokenRepository.java # Email verification tokens
├── KeywordRepository.java             # Keyword data access
├── LocationRepository.java            # Location data access
├── ReportImageRepository.java         # Report image data access
├── ReportRepository.java              # Report data access
├── TokenRepository.java               # JWT token data access
└── UserRepository.java                # User data access
```

### **9. 🔧 Services Package (`services/`) - Business Logic Layer**
```
services/
├── AIProcessorService.java            # AI report processing
├── AuthService.java                   # Authentication service interface
├── EmailVerificationService.java      # Email verification interface
├── ReportService.java                 # Report business logic
├── UserService.java                   # User business logic
└── impl/                             # Service implementations
    ├── AuthServiceImpl.java           # Authentication implementation
    ├── CustomUserDetailsService.java  # User details service
    └── EmailVerificationServiceImpl.java # Email verification implementation
```

## 🧪 **Test Structure (`src/test/java/com/fixmystreet/fixmystreet/`)**

```
test/
├── FixmystreetApplicationTests.java   # Main application tests
├── mappers/                          # Mapper tests
│   ├── LocationMapperTest.java
│   ├── ReportImageMapperTest.java
│   ├── ReportMapperTest.java
│   └── UserMapperTest.java
└── services/
    └── UserServiceTest.java          # User service tests
```

## 📁 **Resources Structure (`src/main/resources/`)**

```
resources/
├── application.properties             # Main application properties
├── application-dev.yml               # Development configuration
├── static/                          # Static resources
└── templates/                       # Template files
```

## 🏗️ **Key Package Relationships**

### **Data Flow Architecture:**
```
Controllers → Services → Repositories → Database
     ↓           ↓           ↓
   DTOs ←→ Mappers ←→ Entities
```

### **Security Flow:**
```
JWT Filter → Auth Service → User Repository → Database
     ↓            ↓              ↓
  JWT Service → Password Encoder → User Entity
```

### **AI Processing Flow:**
```
Report Controller → AI Service → OpenRouter API
     ↓                ↓              ↓
  Report Service → AI Processor → Enhanced Report
```

## 📊 **Package Responsibilities**

| Package | Responsibility |
|---------|----------------|
| **Controllers** | HTTP request handling, API endpoints |
| **Services** | Business logic, transaction management |
| **Repositories** | Data access, database operations |
| **DTOs** | Data transfer, API contracts |
| **Models** | Entity mapping, database schema |
| **Mappers** | Object transformation |
| **Config** | Application configuration, security |
| **Exceptions** | Error handling, validation |

This structure follows **Spring Boot best practices** with clear separation of concerns, making the codebase maintainable and scalable! 🚀

🧑‍💻 Getting Started
🔧 Prerequisites

Java 17+

Maven 3.8+

PostgreSQL 15+

Docker (optional, for containerized setup)

⚙️ Setup Instructions
# Clone the repository
git clone https://github.com/atircio/fixamo.git

# Navigate into the project
cd fixmystreet

# Set up environment variables
cp .env.example .env

# Run the application
mvn spring-boot:run

🐳 Run with Docker
docker build -t fixmtstreet .
docker run -p 8080:8080 fixamo

🧩 Configuration
🔑 Environment Variables
Variable	Description
DB_URL	PostgreSQL database URL
DB_USERNAME	Database username
DB_PASSWORD	Database password
JWT_SECRET	Secret key for token signing
MAIL_USERNAME	Gmail address for SMTP
MAIL_PASSWORD	Gmail app password
🧪 Testing

Run all unit tests with:

mvn test

🧭 Development Guidelines

Follow clean code and SOLID principles

Use DTOs (DetailsDTO, ReportDTO, etc.) instead of direct entity exposure

Ensure proper exception handling and validation

Commit frequently with meaningful messages

🌍 Deployment

Fixamo can be deployed on any cloud or containerized environment:

Docker Compose for local testing

AWS / GCP / Azure for production

Environment variables are configured for each environment

🤝 Contributing

Contributions are always welcome!

To contribute:

Fork the repository

Create your feature branch

Commit changes with clear messages

Open a pull request

🧡 About the Project

Fixamo was developed to empower citizens and help governments respond faster by connecting technology, AI, and civic responsibility.

“Technology is powerful when it makes life better for everyone.”

