# Dockerizing your Tasks project (Spring Boot + PostgreSQL)

## Prereqs
- Project builds a fat JAR with Spring Boot (via `spring-boot-maven-plugin`).
- You have either Maven Wrapper (`mvnw`) + `.mvn` folder in repo, **or** switch the Dockerfile build step to use plain `mvn` or a Gradle image.

## Files
- `Dockerfile` — multi-stage build (JDK 21 for build, JRE 21 for runtime).
- `docker-compose.yml` — two services: `db` (Postgres 16) and `app` (your service).
- `.dockerignore` — keeps your image small.

## Quick start
```bash
# From your project root (where pom.xml lives)
docker compose -f docker-compose.yml up --build
# First run will compile the app inside the builder image
```

Open: http://localhost:8080

The app uses a `docker` Spring profile (set via env) and connects to Postgres at `jdbc:postgresql://db:5432/tasksdb` with user/password `tasks/tasks`.

## Useful commands
```bash
# See logs
docker compose logs -f app
docker compose logs -f db

# Exec into the DB container and use psql
docker exec -it tasks-db psql -U tasks -d tasksdb

# Stop and remove containers (volumes are kept)
docker compose down

# Stop and remove containers + volumes (wipe DB data)
docker compose down -v
```

## Notes
- If you use Gradle, replace the build stage with a Gradle image or copy your Gradle Wrapper and run `./gradlew bootJar`.
- If you prefer building locally: run `./mvnw -DskipTests package` and change the Dockerfile to only copy `target/*.jar` (skipping the build stage).
- Consider adding Spring Actuator and uncommenting the healthcheck in `docker-compose.yml`.
- For production: swap `SPRING_JPA_HIBERNATE_DDL_AUTO=update` for migrations (Flyway/Liquibase) and configure credentials via secrets.
