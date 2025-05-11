
# Inkvite [![CI pipeline 🚀](https://github.com/florianleca/inkvite-backend/actions/workflows/ci-pipeline.yml/badge.svg)](https://github.com/florianleca/inkvite-backend/actions/workflows/ci-pipeline.yml) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=florianleca_inkvite-backend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=florianleca_inkvite-backend) [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=florianleca_inkvite-backend&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=florianleca_inkvite-backend) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=florianleca_inkvite-backend&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=florianleca_inkvite-backend) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=florianleca_inkvite-backend&metric=coverage)](https://sonarcloud.io/summary/new_code?id=florianleca_inkvite-backend)

---

This is the backend repository for **Inkvite**, an appointment booking application for tattoo artists.  
The project provides a REST API that handles the core functionalities of the application, including user management (tattoo artists, clients) and appointment scheduling.

---

## 🚀 Tech Stack

- Java 17
- Spring Boot 3.4.5
- Gradle
- PostgreSQL
- Testcontainers (for integration testing)

---

## 🔧 Local Setup

### Prerequisites

- JDK 17
- Docker (for running integration tests with Testcontainers)

### Database Configuration

In order to run this project, you will need to define the following environment variables:
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

### Running the Application

```bash
./gradlew bootRun
```

---

## ✍️ Authors

- Florian Leca [@florianleca](https://github.com/florianleca)
- Maxime Molines [@max-mol](https://github.com/max-mol)

---

## 📄 Licence
  This project is licensed under the GPL License. See the [LICENSE](LICENSE) file for details.

