# 🧠 Fixamo – Smart Street Issue Reporting System

Empowering communities to make their streets better with the help of AI. Built with Spring Boot, PostgreSQL, and AI integration, Fixamo helps citizens report, track, and resolve public issues efficiently.

---

## 🚀 Overview

Fixamo is an AI-powered platform (web + mobile) for reporting neighborhood issues—think potholes, broken streetlights, water leaks, etc.  
When a user submits a report, the system uses AI to **categorize**, **estimate severity**, **rewrite descriptions for clarity**, and **extract keywords**—so authorities can respond faster and more effectively.

The platform supports multiple **user roles** (Citizen, Authority, Admin) to enforce workflows, permissions, and accountability.

---

## ✨ Key Features

### 🧩 Core Functionality
- Report creation, viewing, editing with **images** and **geolocation**  
- User management: registration, profile, status  
- Filtering and searching by location, category, status  
- Report lifecycle: submitted → reviewed → resolved  

### 🤖 AI-Powered Features
- Automatic report rewriting to improve clarity and consistency  
- Auto-categorization of issues (e.g. “road”, “lighting”, “water”)  
- Severity assessment (low / medium / high)  
- Keyword extraction for indexing, search, analytics  
- Suggestions for authorities when handling reports  

### 👥 User Roles & Permissions
| Role     | Capabilities |
|----------|--------------|
| **Citizen**   | Submit reports, view their own reports, update profile |
| **Authority** | Review & assign reports, change status, view related data |
| **Admin**     | Manage users, categories, system configuration, reports oversight |

### ⚙️ User Status Lifecycle
| Status                | Meaning |
|------------------------|---------|
| **Pending Verification** | User has registered but not yet verified via email |
| **Active**              | Fully approved, all actions allowed |
| **Suspended**           | Temporarily blocked |
| **Deactivated**         | Turned off by user or admin |
| **Banned**              | Permanently blocked from the system |

---

## 🔐 Authentication & Security

### 🔑 JWT-Based Authentication
- Login & signup using JWT access + refresh tokens  
- Refresh mechanism to extend sessions  
- Secure password encryption using strong hashing  

### 🧰 Additional Security Measures
- Email verification using SMTP (e.g., Gmail)  
- CORS configuration, CSRF protection  
- Role-based access control (RBAC) for APIs  
- Global exception handling, session management  

### 🔒 Key Auth Endpoints
| HTTP Method | Path                    | Description                                 |
|-------------|--------------------------|---------------------------------------------|
| POST        | `/api/auth/register`     | Register a new user                          |
| POST        | `/api/auth/login`        | Authenticate and receive JWT                |
| POST        | `/api/auth/refresh`      | Refresh JWT token                            |
| GET/POST    | `/api/auth/verify`       | Verify user via email link / token          |

---

## 🧱 Technical Stack & Architecture

### 🧰 Tech Stack
- **Backend**: Java 17, Spring Boot  
- **Database**: PostgreSQL  
- **Security**: Spring Security + JWT  
- **AI Integration**: OpenAI (via OpenRouter API)  
- **Deployment**: Docker, containerization support  

### 🗂 Data Model & Schema
- Core Entities: `User`, `Report`, `Category`, `Subcategory`, `Location`, `Attachment`  
- Relations:
  - One-to-many: `User → Report`  
  - Many-to-one: `Report → Category`  
  - Report images attached to reports  
  - Token & verification entities for auth flows  

---

## 🏗 Project Structure

src/
└── main/
├── java/com/fixmystreet/fixmystreet/
│ ├── config/
│ ├── controllers/
│ ├── dtos/
│ ├── exceptions/
│ ├── mappers/
│ ├── model/
│ ├── repository/
│ └── services/
└── resources/
├── application.properties
├── application-dev.yml
├── static/
└── templates/

markdown
Copy code

### Major Packages & Responsibilities

- **config/**: Application & security configuration (JWT filters, CORS, Swagger)  
- **controllers/**: REST endpoints (AuthController, ReportController, UserController, etc.)  
- **dtos/**: Data Transfer Objects (requests, responses)  
- **exceptions/**: Custom exceptions & global exception handler  
- **mappers/**: Conversion between Entities and DTOs  
- **model/**: JPA Entities & enums (Role, Status, etc.)  
- **repository/**: Spring Data JPA repositories  
- **services/**: Business logic, AI processing, user & report workflows  

---

## 🧪 Testing

- Unit and integration tests via **JUnit / Mockito**  
- Structure mirrors `src/main/java`: tests for services, mappers, controllers  
- Run all tests with:

```bash
mvn test
🧑‍💻 Getting Started
🔧 Prerequisites
Java 17+

Maven 3.8+

PostgreSQL 15+

Docker (optional, for container setup)

⚙ Installation & Run
bash
Copy code
# Clone the repo
git clone https://github.com/your-username/fixamo.git

cd fixamo

# Copy & configure environment variables
cp .env.example .env
# Edit .env to include DB_URL, credentials, JWT_SECRET, MAIL settings, etc.

# Run locally
mvn spring-boot:run
🐳 Docker (Optional)
bash
Copy code
docker build -t fixamo .
docker run -p 8080:8080 fixamo
⚙ Configuration & Environment Variables
Variable	Description
DB_URL	JDBC URL to PostgreSQL
DB_USERNAME	DB user
DB_PASSWORD	DB password
JWT_SECRET	Secret key for signing tokens
JWT_EXPIRATION	Token expiration time
MAIL_USERNAME	Email / SMTP user
MAIL_PASSWORD	SMTP password or app password

Add any others relevant to email host, AI API keys, etc.

🧭 Development Guidelines
Follow SOLID & Clean Code principles

Always use DTOs — never expose entities directly

Validate inputs and handle exceptions centrally

Write unit tests for new features

Commit early, often, and clearly

🌍 Deployment & Hosting
Fixamo is deployable to any environment:

Locally: Docker Compose or direct Java run

Production: AWS, Azure, GCP, or other cloud

Use environment variables for configuration per environment

📦 API Examples (Optional but helpful)
Here’s a sampling of typical API endpoints:

Method	Path	Description
POST	/api/reports	Create a new report
GET	/api/reports	List all visible reports (with filters)
GET	/api/reports/{id}	Get one report’s details
PUT	/api/reports/{id}	Update report (status, comments, etc.)
DELETE	/api/reports/{id}	Delete a report (if allowed)
GET	/api/users/{userId}	Get user info (admin / own data)
PUT	/api/users/{userId}	Update user profile or status

You can expand this section with full request/response examples (JSON, headers, etc.).

🤝 Contributing
Contributions, bug reports, feature requests — all are welcome!

Steps:

Fork the repository

Create a feature branch (git checkout -b feat/your-feature)

Commit changes with clear messages

Push, then open a pull request

Ensure tests pass & code follows style

🧡 About
Fixamo is built to bridge communities and local governments using technology and AI.
We believe that better reporting, clarity, and categorization lead to faster issue resolution and cleaner, safer streets.

“Technology is powerful when it makes life better for everyone.”

🧾 License
This project is licensed under the MIT License. See the LICENSE file for full details.

vbnet
Copy code

If you like, I can format this for you with **GitHub markdown preview (with Table of Contents, badges, etc.)** or even generate a **starter API doc file** to go with it. Do you want me to do that next?




