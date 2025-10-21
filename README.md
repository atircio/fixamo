# Fix My Street

A comprehensive Spring Boot application for citizens to report street issues and authorities to manage them efficiently. This platform provides AI-powered report processing, user authentication, and role-based access control.

## 🚀 Features

### Core Functionality
- **Report Management**: Create, read, update, and delete street issue reports
- **AI-Powered Processing**: Automatic report enhancement using OpenRouter AI
- **User Management**: Complete user registration, authentication, and profile management
- **Location Services**: GPS coordinates and address tracking for reports
- **Image Support**: Multiple image attachments per report
- **Category System**: Organized report categorization (Roads, Sanitation, Water, Electricity, Noise, Animal Control, Other)
- **Keyword Extraction**: AI-generated keywords for better searchability
- **Email Verification**: Secure email verification system for user accounts

### AI Features
- **Smart Report Processing**: Raw user reports are automatically enhanced using AI
- **Content Rewriting**: Converts informal reports into professional, formal format
- **Automatic Categorization**: AI determines appropriate categories for reports
- **Severity Assessment**: AI evaluates and assigns severity levels (Low, Medium, High)
- **Keyword Generation**: Automatic extraction of relevant keywords
- **Location Integration**: AI processes location data for better context

### User Roles & Permissions
- **Citizen (ROLE_CITIZEN)**: Can create and manage their own reports
- **Authority (ROLE_AUTHORITY)**: Can view all reports and user information
- **Admin (ROLE_ADMIN)**: Full system access including user management

### User Status Management
- **PENDING_VERIFICATION**: New users awaiting email verification
- **ACTIVE**: Verified and active users
- **SUSPENDED**: Temporarily suspended users
- **DEACTIVATED**: Deactivated user accounts
- **BANNED**: Permanently banned users

## 🔐 Authentication & Security

### JWT-Based Authentication
- **Token Generation**: Secure JWT tokens with configurable expiration
- **Role-Based Access Control**: Fine-grained permissions based on user roles
- **Token Refresh**: Automatic token refresh mechanism
- **Secure Headers**: Bearer token authentication for API endpoints

### Security Features
- **Password Encryption**: Secure password storage
- **Email Verification**: Required email verification for account activation
- **Session Management**: Secure session handling with JWT
- **CORS Configuration**: Cross-origin resource sharing setup
- **Input Validation**: Comprehensive request validation using Bean Validation

### Authentication Endpoints
- `POST /api/v1/auth/signup` - User registration
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/logout` - User logout
- `POST /api/v1/auth/refresh` - Token refresh
- `GET /api/vi/auth/verify-email` - Email verification

## 🛠️ Technology Stack

### Backend
- **Spring Boot 3.4.5** - Main framework
- **Java 17** - Programming language
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations
- **PostgreSQL** - Primary database
- **MapStruct** - Object mapping
- **Lombok** - Code generation
- **JWT (jjwt)** - Token management

### AI Integration
- **OpenRouter API** - AI processing service
- **DeepSeek Chat v3.1** - AI model for report processing

### Documentation & Testing
- **Swagger/OpenAPI 3** - API documentation
- **Spring Boot Test** - Testing framework
- **Spring Security Test** - Security testing

### Infrastructure
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Maven** - Build tool
- **Jenkins** - CI/CD pipeline

## 📊 Database Schema

### Core Entities
- **User**: User accounts with roles and status
- **Report**: Street issue reports with metadata
- **Category**: Report categorization system
- **Location**: GPS coordinates and addresses
- **ReportImage**: Image attachments for reports
- **Keyword**: AI-generated keywords for reports
- **Token**: Email verification tokens

### Relationships
- User → Reports (One-to-Many)
- Report → Category (Many-to-One)
- Report → Location (One-to-One)
- Report → Images (One-to-Many)
- Report → Keywords (One-to-Many)

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL 15+
- Docker & Docker Compose (optional)

### Environment Variables
Create a `.env` file with the following variables:
```env
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/fixmystreet
DB_USERNAME=your_username
DB_PASSWORD=your_password
DB_NAME=fixmystreet

# JWT Configuration
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=1800000

# Email Configuration
SMP_EMAIL_ADDRESS=your_email@gmail.com
SMP_EMAIL_PASSWORD=your_app_password

# AI Configuration
OPENROUTER_API_KEY=your_openrouter_api_key
```

### Running the Application

#### Using Docker Compose (Recommended)
```bash
# Clone the repository
git clone <repository-url>
cd fixmystreet

# Create .env file with your configuration
cp .env.example .env
# Edit .env with your values

# Start the application
docker-compose up -d
```

#### Manual Setup
```bash
# Start PostgreSQL database
# Update application-dev.yml with your database credentials

# Build the application
./mvnw clean package

# Run the application
java -jar target/fixmystreet-0.0.1-SNAPSHOT.jar
```

### API Documentation
Once the application is running, access the Swagger UI at:
- **URL**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/v3/api-docs`

## 📡 API Endpoints

### Authentication
- `POST /api/v1/auth/signup` - Register new user
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/logout` - User logout
- `POST /api/v1/auth/refresh` - Refresh JWT token
- `GET /api/vi/auth/verify-email` - Verify email address

### Reports
- `POST /api/reports` - Create new report
- `GET /api/reports` - Get all reports
- `GET /api/reports/{id}` - Get report by ID
- `PUT /api/reports/{id}` - Update report
- `DELETE /api/reports/{id}` - Delete report
- `GET /api/reports/user/{userId}` - Get reports by user

### Users
- `GET /api/users/me` - Get current user profile
- `GET /api/users/{id}` - Get user by ID (Authority/Admin only)
- `GET /api/users/by-email` - Get user by email (Authority/Admin only)
- `GET /api/users/{id}/reports` - Get user with reports (Authority/Admin only)
- `GET /api/users` - Get all users (Admin only)
- `GET /api/users/with-reports` - Get all users with reports (Admin only)
- `PUT /api/users/{id}` - Update user profile
- `DELETE /api/users/{id}` - Delete user (Admin only)

## 🔧 Configuration

### Application Properties
- **Database**: PostgreSQL with JPA/Hibernate
- **Security**: JWT-based authentication
- **Email**: Gmail SMTP configuration
- **AI**: OpenRouter API integration
- **CORS**: Configured for cross-origin requests

### Security Configuration
- JWT token expiration: 30 minutes (configurable)
- Password encoding: BCrypt
- CORS enabled for frontend integration
- Role-based access control

## 🧪 Testing

### Running Tests
```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=UserServiceTest
```

### Test Coverage
- Unit tests for services
- Integration tests for controllers
- Security tests for authentication
- Mapper tests for object conversion

## 🚀 Deployment

### Docker Deployment
```bash
# Build Docker image
docker build -t fixmystreet .

# Run with Docker Compose
docker-compose up -d
```

### Production Considerations
- Configure production database
- Set up proper JWT secrets
- Configure email service
- Set up monitoring and logging
- Configure reverse proxy (nginx)

## 📝 Development

### Project Structure
```
src/
├── main/
│   ├── java/com/fixmystreet/fixmystreet/
│   │   ├── config/          # Configuration classes
│   │   ├── controllers/     # REST controllers
│   │   ├── dtos/           # Data Transfer Objects
│   │   ├── exceptions/     # Exception handling
│   │   ├── mappers/       # Object mappers
│   │   ├── model/         # Entity models
│   │   ├── repository/    # Data repositories
│   │   └── services/      # Business logic
│   └── resources/
│       ├── application.properties
│       └── application-dev.yml
└── test/                  # Test classes
```

### Code Quality
- **Lombok**: Reduces boilerplate code
- **MapStruct**: Type-safe object mapping
- **Validation**: Comprehensive input validation
- **Exception Handling**: Global exception handling
- **Logging**: Structured logging throughout

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the API documentation at `/swagger-ui.html`

---

**Fix My Street** - Empowering citizens to improve their communities through technology and AI.

