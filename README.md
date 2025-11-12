# legal-sight-technical-challenge

# Speech Management API

## Overview
A brief description of your Speech Management API:  
"This system allows management of legal speeches, including creation, retrieval, updating, and deletion. It supports filtering by author, subject, body, and date ranges"

**Main Features:**
- Create, update, and delete speeches
- Fetch speeches with pagination and filtering (author, subject, body, date range)
- Error handling with structured responses (`errorCode` + `errorMessage`)

---

## Technology Stack
- **Backend:** Java 17, Spring Boot
- **Database:** H2 (for testing) / MySQL/PostgreSQL (for production)
- **Other Libraries:** Lombok, MapStruct, Spring Data JPA, Springdoc OpenAPI

---

## Installation

1. **Clone the repository**
```bash
    git clone https://github.com/wrenflores/legal-sight-technical-challenge.git
    cd speech-management-api
```
2. **Setup Backend**
- Ensure Java 17 and Maven are installed.

3. **Setup Database**
- For testing, H2 in-memory database is used (no setup required).

## Running the Project
**Backend:**
```bash
    mvn clean install
    mvn spring-boot:run
```

**Swagger UI (API Documentation): http://localhost:8080/api/speech/swagger-ui.html**

## Features

**Fetch All Speeches**
```bash
    curl --location --request GET 'http://localhost:8080/api/speeches?page=0&size=10&sort=id%2Casc'
```

**Create Speech**
```bash
    curl --location --request POST 'http://localhost:8080/api/speech' \
    --header 'Content-Type: application/json' \
    --data '{
        "author":"jose",
        "subject":"enrichment of plant",
        "body":"you need to always water the plants",
        "speechDate":"2025-11-25"
    }'
```
**Update Speech**
```bash
    curl --location --request PUT 'http://localhost:8080/api/speech/6' \
    --header 'Content-Type: application/json' \
    --data '{
        "author":"joselito",
        "subject":"enrichment of plant",
        "body":"you need to always water the plants",
        "speechDate":"2025-11-25"
    }'
```

**Delete Speech**
```bash
    curl --location --request DELETE 'http://localhost:8080/api/speech/6'
```

**Filtering & Pagination**
```bash
    curl --location --request GET 'http://localhost:8080/v1/speeches?author=John&startDate=2025/01/01&endDate=2025/12/31&page=0&size=10'
```

## Error Handling
**All errors return structured responses in the format:**
```json
    {
      "errorCode": "SPH005",
      "errorMessage": "Invalid date format for 'date'. Please use yyyy/MM/dd."
    }
```
**Common error codes:**
- SPH001 – Speech not found
- SPH002 – Author is required
- SPH003 – Subject is required
- SPH004 – Speech body is required
- SPH005 – Invalid date format
- SPH999 – Unexpected error

## Testing
**Run tests using Maven:**
```bash
     mvn test
```








