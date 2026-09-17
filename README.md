# QuestBoard

QuestBoard is a small close-to-production Java REST application used to compare container image optimization strategies for **Spring Boot** and **Quarkus**.

Both implementations provide comparable functionality and use:

- Java 25
- PostgreSQL
- Hibernate ORM / JPA
- Flyway database migrations
- Bean Validation
- health checks
- metrics
- externalized database configuration
- synchronous request processing

The repository accompanies the **“Can a Real Java App Fit in 50 MB?”** experiment.

## Docker image variants

Both applications can be built using several containerization strategies.

| Suffix | Strategy                                                                                       |
|---|------------------------------------------------------------------------------------------------|
| `-dirty-fat` | Naive single-stage build with full JDK, project sources and build artifacts in the image |
| `-fat` | Cleaned-up single-stage build using a full JDK                                                 |
| `-multistage` | Multi-stage build with a JDK builder and JRE runtime                                           |
| `-jlink` | Multi-stage build with an application-specific Java runtime created with `jlink`               |
| `-native` | GraalVM Native Image built with BellSoft Liberica Native Image Kit                             |

The Dockerfiles use [BellSoft Hardened Images](https://bell-sw.com/bellsoft-hardened-images/) and Alpaquita Linux-based hardened runtime images.

## Running with Docker Compose

Docker and Docker Compose are the only requirements for the regular container builds.

PostgreSQL is started automatically by Compose.

The application is available at:

```text
http://localhost:8080
```

PostgreSQL is exposed on the host at:

```text
localhost:5433
```

Inside the Compose network, the applications connect to PostgreSQL at `postgres:5432`.

### Spring Boot

Choose a Dockerfile using the `SUFFIX` environment variable.

For example, here's how to run the multistage image:

```bash
SUFFIX=-jre docker compose up backend-spring --build
```

### Quarkus

The same suffixes are used for the Quarkus implementation, for example:

```bash
SUFFIX=-multistage docker compose up backend-quarkus --build
```

### Stop the application

```bash
docker compose down
```

To also delete the PostgreSQL volume and start with a clean database:

```bash
docker compose down -v
```

## API

Ready-to-run requests are available in [`requests.http`](requests.http).

## Health endpoints

Spring Boot:

```text
/actuator/health
/actuator/health/db
```

Quarkus:

```text
/q/health
/q/health/live
/q/health/ready
```

## Image-size experiment

The repository contains Dockerfiles ranging from poor but common containerization practices to optimized alternatives.

Two image-size metrics can be used to compare the results:

- **Content size**: compressed image content used for registry storage and distribution.
- **Disk usage**: local storage required for the image content and its unpacked filesystem.

The experiment primarily uses **content size** when asking whether a Java application can fit into a 50 MB container image.

## Notes on `jlink`

The `jlink` variants derive the required JDK modules from the application and create an application-specific Java runtime.

A smaller custom runtime does not always mean a smaller compressed container image. Prebuilt optimized JRE images like Liberica JDK Lite may compress more efficiently, while `jlink` can reduce the unpacked disk footprint.