# 💳 Secure Payment Gateway (Spring Boot + PostgreSQL)

A 3-tier simulation of a secure payment gateway featuring:
- **Spring Boot REST API** (server)
- **PostgreSQL** (database)
- **Static HTML Client** (client tier)
- **Transaction authorization**, fraud rules, and audit logging

## ⚙️ Run locally
```bash
docker compose up -d       # starts postgres
mvn spring-boot:run        # runs app
